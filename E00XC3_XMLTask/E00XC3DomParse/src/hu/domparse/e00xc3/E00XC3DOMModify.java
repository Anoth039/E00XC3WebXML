package hu.domparse.e00xc3;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class E00XC3DOMModify {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		
		// XML beolvasása
        File inputFile = new File("E00XC3XML.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        
        // Normalizálás
        doc.getDocumentElement().normalize();
        
        // 1. módosítás: Első kölcsönző címének módosítása
        NodeList kolcsonzok = doc.getElementsByTagName("kolcsonzo");
        Element firstKolcsonzo = (Element) kolcsonzok.item(0);

        Element cimElem = (Element) firstKolcsonzo.getElementsByTagName("cim").item(0);

        Element varosElem = (Element) cimElem.getElementsByTagName("varos").item(0);
        Element utcaElem = (Element) cimElem.getElementsByTagName("utca").item(0);
        Element hazszamElem = (Element) cimElem.getElementsByTagName("hazszam").item(0);

        System.out.println("1. módosítás: Első kölcsönző címének módosítása");
        System.out.println("Eredeti cím: " 
            + varosElem.getTextContent() + ", " 
            + utcaElem.getTextContent() + " " 
            + hazszamElem.getTextContent()
        );

        varosElem.setTextContent("Budapest");
        utcaElem.setTextContent("Fő utca");
        hazszamElem.setTextContent("99");

        System.out.println("Módosított cím: " 
            + varosElem.getTextContent() + ", " 
            + utcaElem.getTextContent() + " " 
            + hazszamElem.getTextContent()
        );
        
        // 2. módosítás: C kategóriás jogosítványok módosítása D-re
        NodeList ugyfelek = doc.getElementsByTagName("ugyfel");
        System.out.println("\n2. módosítás: A C kategóriás jogosítványok módosítása D-re");
        for (int i = 0; i < ugyfelek.getLength(); i++) {
            Element ugyfel = (Element) ugyfelek.item(i);
            NodeList jogositvanyok = ugyfel.getElementsByTagName("jogositvany");
            for (int j = 0; j < jogositvanyok.getLength(); j++) {
                Element jogositvany = (Element) jogositvanyok.item(j);
                Element kategoria = (Element) jogositvany.getElementsByTagName("kategoria").item(0);
                if (kategoria.getTextContent().equals("C")) {
                    System.out.println("Ügyfél: " + ugyfel.getElementsByTagName("nev").item(0).getTextContent()
                                       + " - Eredeti kategória: " + kategoria.getTextContent());
                    kategoria.setTextContent("D");
                    System.out.println("Új kategória: D");
                }
            }
        }
        
        // 3. módosítás: Alkalmazottak kölcsönzőjének cseréje (k1 ↔ k2)
        NodeList alkalmazottak = doc.getElementsByTagName("alkalmazott");
        System.out.println("\n3. módosítás: Alkalmazottak kölcsönzőjének cseréje (k1 ↔ k2)");
        for (int i = 0; i < alkalmazottak.getLength(); i++) {
            Element alkalmazott = (Element) alkalmazottak.item(i);
            String eredetiKod = alkalmazott.getAttribute("k_a");
            String ujKod = eredetiKod.equals("k1") ? "k2" : (eredetiKod.equals("k2") ? "k1" : eredetiKod);
            
            System.out.println("Alkalmazott: " + alkalmazott.getElementsByTagName("nev").item(0).getTextContent()
                               + " - Eredeti kölcsönző: " + eredetiKod
                               + ", Új kölcsönző: " + ujKod);
            
            alkalmazott.setAttribute("k_a", ujKod);
        }
        
	   // 4. módosítás: Új szolgáltatás hozzáadása kommenttel együtt
	   Comment comment = doc.createComment(" 5. Szolgáltatás ");
	
	   Element ujSzolgaltatas = doc.createElement("szolgaltatas");
	   ujSzolgaltatas.setAttribute("szk", "sz5");
	   ujSzolgaltatas.setAttribute("k_sz", "k1");
	
	   Element nev = doc.createElement("nev");
	   nev.setTextContent("Biciklitartó bérlés");
	   ujSzolgaltatas.appendChild(nev);
	
       Element leiras = doc.createElement("leiras");
       leiras.setTextContent("Kiegészítő biciklitartó biztosítása a járműhöz hosszabb utazásokhoz.");
	   ujSzolgaltatas.appendChild(leiras);
	
	   Element koltseg = doc.createElement("koltseg");
	   koltseg.setTextContent("3000");
	   ujSzolgaltatas.appendChild(koltseg);
	
	   Node lastSzolgaltatas = doc.getElementsByTagName("szolgaltatas").item(doc.getElementsByTagName("szolgaltatas").getLength() - 1);
	   Node parent = lastSzolgaltatas.getParentNode();
	
	   parent.insertBefore(comment, lastSzolgaltatas.getNextSibling());
	   parent.insertBefore(ujSzolgaltatas, comment.getNextSibling());
	
	   System.out.println("\n4. módosítás: Új szolgáltatás hozzáadása");
	   System.out.println("Szolgáltatás neve: " + nev.getTextContent());
	   System.out.println("Leírás: " + leiras.getTextContent());
       System.out.println("Költség: " + koltseg.getTextContent() + " Ft\n");
        
       // XML kiírása konzolra
       TransformerFactory transformerFactory = TransformerFactory.newInstance();
       Transformer transformer = transformerFactory.newTransformer();
       
	   transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
       transformer.setOutputProperty(OutputKeys.INDENT, "yes");
       transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
       
	   DOMSource source = new DOMSource(doc);
	   
	   StreamResult console = new StreamResult(System.out);

       removeEmptyTextNodes(doc.getDocumentElement());
       
       transformer.transform(source, console);
    }
	
	private static void removeEmptyTextNodes(Element element) {
	    NodeList children = element.getChildNodes();
	    for (int i = children.getLength() - 1; i >= 0; i--) {
	        Node child = children.item(i);
	        if (child.getNodeType() == Node.TEXT_NODE) {
	            if (child.getTextContent().trim().isEmpty()) {
	                element.removeChild(child);
	            }
	        } else if (child.getNodeType() == Node.ELEMENT_NODE) {
	            removeEmptyTextNodes((Element) child);
	        }
	    }
	}
}
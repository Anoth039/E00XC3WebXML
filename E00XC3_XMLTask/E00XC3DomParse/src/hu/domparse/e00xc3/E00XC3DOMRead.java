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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class E00XC3DOMRead {

	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException, TransformerException {
        
		// XML fájl beolvasása
	    File inputFile = new File("E00XC3XML.xml");
	    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    Document doc = dBuilder.parse(inputFile);
	    
	    // Normalizálás
	    doc.getDocumentElement().normalize();
	    
	    // Gyökérelem kiírása
	    Element root = doc.getDocumentElement();
	    System.out.println("Gyökérelem: " + root.getTagName());
	    System.out.println("===============================================");

	    // Kölcsönzők beolvasása
	    NodeList kolcsonzok = doc.getElementsByTagName("kolcsonzo");
        for (int i = 0; i < kolcsonzok.getLength(); i++) {
            Element k = (Element) kolcsonzok.item(i);
            System.out.println("Aktuális elem: " + k.getTagName());

            String kod = k.getAttribute("kkod");
            String nev = k.getElementsByTagName("nev").item(0).getTextContent();

            Element cim = (Element) k.getElementsByTagName("cim").item(0);
            String cimStr =
                cim.getElementsByTagName("varos").item(0).getTextContent() + ", " +
                cim.getElementsByTagName("utca").item(0).getTextContent() + " " +
                cim.getElementsByTagName("hazszam").item(0).getTextContent();

            System.out.println("Kölcsönző kód: " + kod);
            System.out.println("Név: " + nev);
            System.out.println("Cím: " + cimStr);

            System.out.println("Elérhetőségek:");
            NodeList el = k.getElementsByTagName("elerhetoseg");
            for (int j = 0; j < el.getLength(); j++)
                System.out.println("  " + el.item(j).getTextContent().trim());

            System.out.println("Nyitvatartás:");
            NodeList ny = k.getElementsByTagName("nyitvatartas");
            for (int j = 0; j < ny.getLength(); j++) {
                Element e = (Element) ny.item(j);
                System.out.println("  " +
                    e.getElementsByTagName("nap").item(0).getTextContent() + ": " +
                    e.getElementsByTagName("intervallum").item(0).getTextContent());
            }

            System.out.println("-----------------------------------------------");
        }
        
        // Tulajdonosok beolvasása
        NodeList tulajdonosok = doc.getElementsByTagName("tulajdonos");
        for (int i = 0; i < tulajdonosok.getLength(); i++) {
            Element t = (Element) tulajdonosok.item(i);
            System.out.println("Aktuális elem: " + t.getTagName());

            System.out.println("Tulajdonos kód: " + t.getAttribute("tkod"));
            System.out.println("Kölcsönző kódja: " + t.getAttribute("k_t"));
            System.out.println("Tulajdonos neve: " + t.getElementsByTagName("nev").item(0).getTextContent());
            System.out.println("Tulajdonos életkora: " + t.getElementsByTagName("eletkor").item(0).getTextContent());
            System.out.println("Tulajdonos adószáma: " + t.getElementsByTagName("adoszam").item(0).getTextContent());
            System.out.println("-----------------------------------------------");
        }
        
        // Alkalmazottak beolvasása
        NodeList alkalmazottak = doc.getElementsByTagName("alkalmazott");
        for (int i = 0; i < alkalmazottak.getLength(); i++) {
            Element a = (Element) alkalmazottak.item(i);
            System.out.println("Aktuális elem: " + a.getTagName());

            System.out.println("Alkalmazott kód: " + a.getAttribute("akod"));
            System.out.println("Kölcsönző kódja: " + a.getAttribute("k_a"));
            System.out.println("Alkalmazott neve: " + a.getElementsByTagName("nev").item(0).getTextContent());
            System.out.println("Beosztás: " + a.getElementsByTagName("beosztas").item(0).getTextContent());

            System.out.println("Műszakok:");
            NodeList mu = a.getElementsByTagName("muszak");
            for (int j = 0; j < mu.getLength(); j++)
                System.out.println("  " + mu.item(j).getTextContent().trim());

            System.out.println("Végzettségek:");
            NodeList vg = a.getElementsByTagName("vegzettseg");
            for (int j = 0; j < vg.getLength(); j++)
                System.out.println("  " + vg.item(j).getTextContent().trim());

            System.out.println("-----------------------------------------------");
        }
        
        // Szolgáltatások beolvasása
        NodeList szolgaltatasok = doc.getElementsByTagName("szolgaltatas");
        for (int i = 0; i < szolgaltatasok.getLength(); i++) {
            Element sz = (Element) szolgaltatasok.item(i);
            System.out.println("Aktuális elem: " + sz.getTagName());

            System.out.println("Szolgáltatás kód: " + sz.getAttribute("szk"));
            System.out.println("Kölcsönző kódja: " + sz.getAttribute("k_sz"));
            System.out.println("Szolgáltatás neve: " + sz.getElementsByTagName("nev").item(0).getTextContent());
            System.out.println("Leírás: " + sz.getElementsByTagName("leiras").item(0).getTextContent());
            System.out.println("Költség: " + sz.getElementsByTagName("koltseg").item(0).getTextContent() + " Ft");
            System.out.println("-----------------------------------------------");
        }
        
        // Ügyfelek beolvasása
        NodeList ugyfelek = doc.getElementsByTagName("ugyfel");
        for (int i = 0; i < ugyfelek.getLength(); i++) {
            Element u = (Element) ugyfelek.item(i);
            System.out.println("Aktuális elem: " + u.getTagName());

            System.out.println("Ügyfél kód: " + u.getAttribute("ukod"));
            System.out.println("Ügyfél név: " + u.getElementsByTagName("nev").item(0).getTextContent());

            Element cim = (Element) u.getElementsByTagName("cim").item(0);
            String c =
                cim.getElementsByTagName("varos").item(0).getTextContent() + ", " +
                cim.getElementsByTagName("utca").item(0).getTextContent() + " " +
                cim.getElementsByTagName("hazszam").item(0).getTextContent();
            System.out.println("Ügyfél címe: " + c);

            System.out.println("Elérhetőségek:");
            NodeList el = u.getElementsByTagName("elerhetoseg");
            for (int j = 0; j < el.getLength(); j++)
                System.out.println("  " + el.item(j).getTextContent().trim());

            NodeList jog = u.getElementsByTagName("jogositvany");
            for (int j = 0; j < jog.getLength(); j++) {
                Element e = (Element) jog.item(j);
                System.out.println("Jogosítvány:");
                System.out.println("  Kategória: " + e.getElementsByTagName("kategoria").item(0).getTextContent());
                System.out.println("  Érvényesség: " + e.getElementsByTagName("ervenyesseg").item(0).getTextContent());
            }

            System.out.println("-----------------------------------------------");
        }
        
        // Bérlések beolvasása
        NodeList berlesek = doc.getElementsByTagName("berles");
        for (int i = 0; i < berlesek.getLength(); i++) {
            Element b = (Element) berlesek.item(i);
            System.out.println("Aktuális elem: " + b.getTagName());

            System.out.println("Kölcsönző kódja: " + b.getAttribute("k_u_k"));
            System.out.println("Ügyfél kódja: " + b.getAttribute("k_u_u"));

            Element j = (Element) b.getElementsByTagName("jarmu").item(0);
            System.out.println("Jármű:");
            System.out.println("  Márka: " + j.getElementsByTagName("marka").item(0).getTextContent());
            System.out.println("  Típus: " + j.getElementsByTagName("tipus").item(0).getTextContent());
            System.out.println("  Rendszám: " + j.getElementsByTagName("rendszam").item(0).getTextContent());

            System.out.println("Bérlés kezdete: " + b.getElementsByTagName("kezdete").item(0).getTextContent());
            System.out.println("Bérlés vége: " + b.getElementsByTagName("vege").item(0).getTextContent());

            Element d = (Element) b.getElementsByTagName("dij").item(0);
            System.out.println("Díjak:");
            System.out.println("  Kaució: " + d.getElementsByTagName("kaucio").item(0).getTextContent() + " Ft");
            System.out.println("  Napidíj: " + d.getElementsByTagName("napidij").item(0).getTextContent() + " Ft");
            System.out.println("  Kilométerdíj: " + d.getElementsByTagName("kilometerdij").item(0).getTextContent() + " Ft");

            System.out.println("-----------------------------------------------");
        }
        
        // XML fájl kiírása
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		
		DOMSource source = new DOMSource(doc);
		
		File myFile = new File("E00XC3XML_output.xml");
		
		StreamResult file = new StreamResult(myFile);
		
        removeEmptyTextNodes(doc.getDocumentElement());

        transformer.transform(source, file);

        System.out.println("XML fájl sikeresen elmentve: " + myFile);
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
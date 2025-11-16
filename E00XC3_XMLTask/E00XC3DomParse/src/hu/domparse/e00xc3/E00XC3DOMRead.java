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
	        Element kolcsonzoElem = (Element) kolcsonzok.item(i);

	        System.out.println("Aktuális elem: " + kolcsonzoElem.getTagName());

	        String kod = kolcsonzoElem.getAttribute("kkod");
	        String nev = getTagValue(kolcsonzoElem, "nev");

	        Element cimElem = (Element) kolcsonzoElem.getElementsByTagName("cim").item(0);
	        String cim = getTagValue(cimElem, "varos") + ", " +
	                     getTagValue(cimElem, "utca") + " " +
	                     getTagValue(cimElem, "hazszam");

	        System.out.println("Kölcsönző kód: " + kod);
	        System.out.println("Név: " + nev);
	        System.out.println("Cím: " + cim);

	        NodeList elerhetosegek = kolcsonzoElem.getElementsByTagName("elerhetoseg");
	        System.out.println("Elérhetőségek:");
	        for (int j = 0; j < elerhetosegek.getLength(); j++) {
	            String ertek = elerhetosegek.item(j).getTextContent().trim();
	            System.out.println("  " + ertek);
	        }

	        NodeList nyitvatartasok = kolcsonzoElem.getElementsByTagName("nyitvatartas");
	        System.out.println("Nyitvatartás:");
	        for (int j = 0; j < nyitvatartasok.getLength(); j++) {
	            Element nyitElem = (Element) nyitvatartasok.item(j);
	            String nap = getTagValue(nyitElem, "nap");
	            String intervallum = getTagValue(nyitElem, "intervallum");
	            System.out.println("  " + nap + ": " + intervallum);
	        }

	        System.out.println("-----------------------------------------------");
	    }
	    
	    // Tulajdoosok beolvasása
	    NodeList tulajdonosok = doc.getElementsByTagName("tulajdonos");

	    for (int i = 0; i < tulajdonosok.getLength(); i++) {
	        Element tulajElem = (Element) tulajdonosok.item(i);

	        System.out.println("Aktuális elem: " + tulajElem.getTagName());

	        String tkod = tulajElem.getAttribute("tkod");
	        String kt = tulajElem.getAttribute("k_t");

	        String nev = getTagValue(tulajElem, "nev");
	        String eletkor = getTagValue(tulajElem, "eletkor");
	        String adoszam = getTagValue(tulajElem, "adoszam");

	        System.out.println("Tulajdonos kód: " + tkod);
	        System.out.println("Kölcsönző kódja: " + kt);
	        System.out.println("Tulajdonos neve: " + nev);
	        System.out.println("Tulajdonos életkora: " + eletkor);
	        System.out.println("Tulajdonos adószáma: " + adoszam);
	        System.out.println("-----------------------------------------------");
	    }
		
	    // Alkalmazottak beolvasása
	    NodeList alkalmazottak = doc.getElementsByTagName("alkalmazott");

	    for (int i = 0; i < alkalmazottak.getLength(); i++) {
	        Element alkElem = (Element) alkalmazottak.item(i);

	        System.out.println("Aktuális elem: " + alkElem.getTagName());

	        String akod = alkElem.getAttribute("akod");
	        String ka = alkElem.getAttribute("k_a");

	        String nev = getTagValue(alkElem, "nev");
	        String beosztas = getTagValue(alkElem, "beosztas");

	        System.out.println("Alkalmazott kód: " + akod);
	        System.out.println("Kölcsönző kódja: " + ka);
	        System.out.println("Alkalmazott neve: " + nev);
	        System.out.println("Beosztás: " + beosztas);

	        NodeList muszakok = alkElem.getElementsByTagName("muszak");
	        System.out.println("Műszakok:");
	        for (int j = 0; j < muszakok.getLength(); j++) {
	            String muszak = muszakok.item(j).getTextContent().trim();
	            System.out.println("  " + muszak);
	        }

	        NodeList vegzettsegek = alkElem.getElementsByTagName("vegzettseg");
	        System.out.println("Végzettségek:");
	        for (int j = 0; j < vegzettsegek.getLength(); j++) {
	            String vegzettseg = vegzettsegek.item(j).getTextContent().trim();
	            System.out.println("  " + vegzettseg);
	        }

	        System.out.println("-----------------------------------------------");
	    }
	    
	    // Szolgáltatások beolvasása
	    NodeList szolgaltatasok = doc.getElementsByTagName("szolgaltatas");

	    for (int i = 0; i < szolgaltatasok.getLength(); i++) {
	        Element szElem = (Element) szolgaltatasok.item(i);

	        System.out.println("Aktuális elem: " + szElem.getTagName());

	        String szk = szElem.getAttribute("szk");
	        String ksz = szElem.getAttribute("k_sz");

	        String nev = getTagValue(szElem, "nev");
	        String leiras = getTagValue(szElem, "leiras");
	        String koltseg = getTagValue(szElem, "koltseg");

	        System.out.println("Szolgáltatás kód: " + szk);
	        System.out.println("Kölcsönző kódja: " + ksz);
	        System.out.println("Szolgáltatás neve: " + nev);
	        System.out.println("Leírás: " + leiras);
	        System.out.println("Költség: " + koltseg + " Ft");
	        System.out.println("-----------------------------------------------");
	    }
	    
	    // Ügyfelek beolvasása
	    NodeList ugyfelek = doc.getElementsByTagName("ugyfel");

	    for (int i = 0; i < ugyfelek.getLength(); i++) {
	        Element ugyfelElem = (Element) ugyfelek.item(i);

	        System.out.println("Aktuális elem: " + ugyfelElem.getTagName());

	        String ukod = ugyfelElem.getAttribute("ukod");
	        String nev = getTagValue(ugyfelElem, "nev");

	        Element cimElem = (Element) ugyfelElem.getElementsByTagName("cim").item(0);
	        String cim = getTagValue(cimElem, "varos") + ", " +
	                     getTagValue(cimElem, "utca") + " " +
	                     getTagValue(cimElem, "hazszam");

	        System.out.println("Ügyfél kód: " + ukod);
	        System.out.println("Ügyfél név: " + nev);
	        System.out.println("Ügyfél címe: " + cim);

	        NodeList elerhetosegek = ugyfelElem.getElementsByTagName("elerhetoseg");
	        System.out.println("Elérhetőségek:");
	        for (int j = 0; j < elerhetosegek.getLength(); j++) {
	            String ertek = elerhetosegek.item(j).getTextContent().trim();
	            System.out.println("  " + ertek);
	        }

	        NodeList jogositvanyok = ugyfelElem.getElementsByTagName("jogositvany");
	        for (int j = 0; j < jogositvanyok.getLength(); j++) {
	            Element jogElem = (Element) jogositvanyok.item(j);
	            String kategoria = getTagValue(jogElem, "kategoria");
	            String ervenyesseg = getTagValue(jogElem, "ervenyesseg");

	            System.out.println("Jogosítvány:");
	            System.out.println("  Kategória: " + kategoria);
	            System.out.println("  Érvényesség: " + ervenyesseg);
	        }

	        System.out.println("-----------------------------------------------");
	    }
	    
	    // Bérlések beolvasása
	    NodeList berlesek = doc.getElementsByTagName("berles");

	    for (int i = 0; i < berlesek.getLength(); i++) {
	        Element berlesElem = (Element) berlesek.item(i);

	        System.out.println("Aktuális elem: " + berlesElem.getTagName());

	        String kuk = berlesElem.getAttribute("k_u_k");
	        String kuu = berlesElem.getAttribute("k_u_u");

	        System.out.println("Kölcsönző kódja: " + kuk);
	        System.out.println("Ügyfél kódja: " + kuu);

	        Element jarmuElem = (Element) berlesElem.getElementsByTagName("jarmu").item(0);
	        String marka = getTagValue(jarmuElem, "marka");
	        String tipus = getTagValue(jarmuElem, "tipus");
	        String rendszam = getTagValue(jarmuElem, "rendszam");

	        System.out.println("Jármű:");
	        System.out.println("  Márka: " + marka);
	        System.out.println("  Típus: " + tipus);
	        System.out.println("  Rendszám: " + rendszam);

	        String kezdete = getTagValue(berlesElem, "kezdete");
	        String vege = getTagValue(berlesElem, "vege");

	        System.out.println("Bérlés kezdete: " + kezdete);
	        System.out.println("Bérlés vége: " + vege);

	        Element dijElem = (Element) berlesElem.getElementsByTagName("dij").item(0);
	        String kaucio = getTagValue(dijElem, "kaucio");
	        String napidij = getTagValue(dijElem, "napidij");
	        String kilometerdij = getTagValue(dijElem, "kilometerdij");

	        System.out.println("Díjak:");
	        System.out.println("  Kaució: " + kaucio + " Ft");
	        System.out.println("  Napidíj: " + napidij + " Ft");
	        System.out.println("  Kilométerdíj: " + kilometerdij + " Ft");

	        System.out.println("-----------------------------------------------");
	    }
	    
	    // Üres sorok eltávolítása
	    removeEmptyTextNodes(doc.getDocumentElement());
	    
	    // XML fájl kiírása
	    TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    Transformer transformer = transformerFactory.newTransformer();
	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

	    DOMSource source = new DOMSource(doc);
	    StreamResult result = new StreamResult(new File("E00XC3XML_output.xml"));

	    transformer.transform(source, result);

	    System.out.println("XML fájl sikeresen elmentve: E00XC3XML_output.xml");
	}
	
	// Segédfüggvény tag értékének lekérésére
	private static String getTagValue(Element parent, String tagName) {
	    NodeList list = parent.getElementsByTagName(tagName);
	    return list.getLength() > 0 ? list.item(0).getTextContent() : "";
	}
	
	// Segédfüggvény üres szöveges csomópontok eltávolítására
	private static void removeEmptyTextNodes(Element elem) {
	    NodeList children = elem.getChildNodes();
	    for (int i = children.getLength() - 1; i >= 0; i--) {
	        Node child = children.item(i);
	        if (child.getNodeType() == Node.TEXT_NODE && child.getTextContent().trim().isEmpty()) {
	            elem.removeChild(child);
	        } else if (child.getNodeType() == Node.ELEMENT_NODE) {
	            removeEmptyTextNodes((Element) child);
	        }
	    }
	}
}
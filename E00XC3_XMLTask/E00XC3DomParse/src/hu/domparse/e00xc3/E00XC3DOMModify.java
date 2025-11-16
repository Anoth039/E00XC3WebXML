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

public class E00XC3DOMModify {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		
		// XML beolvasása
        File inputFile = new File("E00XC3XML.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();

        // 1. Kölcsönző nevének módosítása
        NodeList kolcsonzok = doc.getElementsByTagName("kolcsonzo");
        Element firstKolcsonzo = (Element) kolcsonzok.item(0);
        System.out.println("1. Első kölcsönző nevének módosítása");
        System.out.println("Eredeti név: " + getTagValue(firstKolcsonzo, "nev"));
        firstKolcsonzo.getElementsByTagName("nev").item(0).setTextContent("CityCar Rent Plus");
        System.out.println("Módosított név: " + getTagValue(firstKolcsonzo, "nev"));
        System.out.println("-----------------------------------------------");

        // 2. Alkalmazott végzettségének bővítése
        NodeList alkalmazottak = doc.getElementsByTagName("alkalmazott");
        Element firstAlk = (Element) alkalmazottak.item(0);
        System.out.println("2. Első alkalmazott végzettségeinek bővítése");
        Element ujVegzettseg = doc.createElement("vegzettseg");
        ujVegzettseg.setTextContent("Projektmenedzser");
        firstAlk.appendChild(ujVegzettseg);
        System.out.println("Új végzettség hozzáadva: " + ujVegzettseg.getTextContent());
        System.out.println("-----------------------------------------------");

        // 3. Bérlések díjainak növelése (kaucio, napidij, kilometerdij)
        NodeList berlesek = doc.getElementsByTagName("berles");
        System.out.println("3. Bérlések díjainak növelése (+1000 Ft a különböző díjakra)");

        String[] dijTipusok = {"kaucio", "napidij", "kilometerdij"};

        for (int i = 0; i < berlesek.getLength(); i++) {
            Element berles = (Element) berlesek.item(i);
            Element dijElem = (Element) berles.getElementsByTagName("dij").item(0);

            System.out.println("Bérlés #" + (i + 1));

            for (String tipus : dijTipusok) {
                int eredeti = Integer.parseInt(
                    dijElem.getElementsByTagName(tipus).item(0).getTextContent()
                );

                dijElem.getElementsByTagName(tipus).item(0)
                        .setTextContent(String.valueOf(eredeti + 1000));

                System.out.println("  " + tipus + " új érték: " + (eredeti + 1000) + " Ft");
            }
        }
        System.out.println("-----------------------------------------------");

        // 4. Új szolgáltatás hozzáadása
        Element root = doc.getDocumentElement();
        Element ujSzolg = doc.createElement("szolgaltatas");
        ujSzolg.setAttribute("szk", "s3");
        ujSzolg.setAttribute("k_sz", "k1");

        Element nev = doc.createElement("nev");
        nev.setTextContent("Gyerekülés bérlés");
        ujSzolg.appendChild(nev);

        Element leiras = doc.createElement("leiras");
        leiras.setTextContent("Gyermekülés bérlése a jármű mellé, külön díjért.");
        ujSzolg.appendChild(leiras);

        Element koltseg = doc.createElement("koltseg");
        koltseg.setTextContent("5000");
        ujSzolg.appendChild(koltseg);

        NodeList szolgList = doc.getElementsByTagName("szolgaltatas");

        if (szolgList.getLength() > 0) {
            Node lastSzolg = szolgList.item(szolgList.getLength() - 1);
            root.insertBefore(ujSzolg, lastSzolg.getNextSibling());
        } else {
            root.appendChild(ujSzolg);
        }
        
        System.out.println("4. Új szolgáltatás hozzáadva: " + getTagValue(ujSzolg, "nev"));
        System.out.println("-----------------------------------------------");
        
        removeEmptyTextNodes(root);
        System.out.println("\n===== MÓDOSÍTOTT XML TARTALOM =====\n");
        printDocumentToConsole(doc);
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

    // XML dokumentum formázott kiírása konzolra
    private static void printDocumentToConsole(Document doc) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        DOMSource source = new DOMSource(doc);
        StreamResult consoleResult = new StreamResult(System.out);
        transformer.transform(source, consoleResult);
    }
}

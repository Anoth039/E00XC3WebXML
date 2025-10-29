package dome00xc31029;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomReadE00XC31 {
	
public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
        
        File xmlFile = new File("orarendE00XC3.xml");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);

        doc.getDocumentElement().normalize();

        System.out.println("Gyökér elem: " + doc.getDocumentElement().getNodeName());

        NodeList nList = doc.getElementsByTagName("ora");

        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);

            System.out.println("\nAktuális elem: " + nNode.getNodeName());

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
            	
                Element elem = (Element) nNode;

                String id = elem.getAttribute("id");
                String tipus = elem.getAttribute("tipus");

                String targy = elem.getElementsByTagName("targy").item(0).getTextContent();

                Element idopontElem = (Element) elem.getElementsByTagName("idopont").item(0);
                String nap = idopontElem.getElementsByTagName("nap").item(0).getTextContent();
                String tol = idopontElem.getElementsByTagName("tol").item(0).getTextContent();
                String ig = idopontElem.getElementsByTagName("ig").item(0).getTextContent();

                String helyszin = elem.getElementsByTagName("helyszin").item(0).getTextContent();
                String oktato = elem.getElementsByTagName("oktato").item(0).getTextContent();
                String szak = elem.getElementsByTagName("szak").item(0).getTextContent();

                System.out.println("Óra id: " + id);
                System.out.println("Típus: " + tipus);
                System.out.println("Tárgy: " + targy);
                System.out.println("Időpont:");
                System.out.println("   Nap: " + nap);
                System.out.println("   Tól: " + tol);
                System.out.println("   Ig: " + ig);
                System.out.println("Helyszín: " + helyszin);
                System.out.println("Oktató: " + oktato);
                System.out.println("Szak: " + szak);
                System.out.println("--------------------------------------");
            }
        }
    }
}

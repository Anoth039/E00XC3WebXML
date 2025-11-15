package xpathe00xc3;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class xPathQuery1E00XC3 {

	public static void main(String[] args) {
		try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // XML beolvasása
            Document document = builder.parse("orarendE00XC3.xml");
            document.getDocumentElement().normalize();

            XPath xPath = XPathFactory.newInstance().newXPath();

            // 1. lekérdezés: Összes óra
            String q1 = "//ora";
            printNodes(document, xPath, q1, "1) Összes óra eleme:\n");

            // 2. lekérdezés: Hétfői órák
            String q2 = "//ora[idopont/nap='Hétfő']";
            printNodes(document, xPath, q2, "2) Hétfői órák:\n");

            // 3. lekérdezés: Gyakorlat típusú órák
            String q3 = "//ora[@tipus='gyakorlat']";
            printNodes(document, xPath, q3, "3) Gyakorlat típusú órák:\n");


        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException e) {
            e.printStackTrace();
        }
    }

    private static void printNodes(Document doc, XPath xPath, String expr, String title)
            throws XPathExpressionException {

        System.out.println("\n" + title);
        NodeList list = (NodeList) xPath.compile(expr).evaluate(doc, XPathConstants.NODESET);

        for (int i = 0; i < list.getLength(); i++) {
            Element e = (Element) list.item(i);

            System.out.println("ID: " + e.getAttribute("id"));
            System.out.println("Típus: " + e.getAttribute("tipus"));
            System.out.println("Tárgy: " + e.getElementsByTagName("targy").item(0).getTextContent());

            Element ido = (Element) e.getElementsByTagName("idopont").item(0);
            System.out.println("Időpont:");
            System.out.println("   Nap: " + ido.getElementsByTagName("nap").item(0).getTextContent());
            System.out.println("   Tól: " + ido.getElementsByTagName("tol").item(0).getTextContent());
            System.out.println("   Ig:  " + ido.getElementsByTagName("ig").item(0).getTextContent());

            System.out.println("Helyszín: " + e.getElementsByTagName("helyszin").item(0).getTextContent());
            System.out.println("Oktató: " + e.getElementsByTagName("oktato").item(0).getTextContent());
            System.out.println("Szak: " + e.getElementsByTagName("szak").item(0).getTextContent());
            System.out.println("------------------------------------");
        }
    }
}

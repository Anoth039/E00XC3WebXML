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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class xPathQueryE00XC3 {

	public static void main(String[] args) {
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse("studentE00XC3.xml");
			
			document.getDocumentElement().normalize();
			
			XPath xPath = XPathFactory.newInstance().newXPath();
			
			// 1) Az összes student elem, amely a class gyermeke
            // String e00xc3 = "/class/student";
            // printStudents(document, xPath, e00xc3, "1. Összes student, amely a class gyermeke:");
			
			// 2) A student, amelynek id attribútuma 02
            // String e00xc3 = "//student[@id='02']";
            // printStudents(document, xPath, e00xc3, "2. Student, ahol id='02':");
			
			// 3) Az összes student elem (függetlenül attól, hol van)
            // String e00xc3 = "//student";
            // printStudents(document, xPath, e00xc3, "3. Összes student elem:");
			
			// 4) A második student, amely a class root gyermeke
            // String e00xc3 = "/class/student[2]";
            // printStudents(document, xPath, e00xc3, "4. Második student:");
			
			// 5) Az utolsó student, amely a class root gyermeke
            // String e00xc3 = "/class/student[last()]";
            // printStudents(document, xPath, e00xc3, "5. Utolsó student:");
			
			// 6) Az utolsó előtti student
            // String e00xc3 = "/class/student[last()-1]";
            // printStudents(document, xPath, e00xc3, "6. Utolsó előtti student:");
			
		    // 7) Az első két student a root elem gyermekei közül
            // String e00xc3 = "/class/student[position()<=2]";
            // printStudents(document, xPath, e00xc3, "7. Első két student:");
			
			// 8) A class root összes gyermeke
            // String e00xc3 = "/class/*";
            // printTextNodes(document, xPath, e00xc3, "8. Class root összes gyermeke:");
			
			// 9) Az összes student, amely rendelkezik legalább egy attribútummal
            // String e00xc3 = "//student[@*]";
            // printStudents(document, xPath, e00xc3, "9. Studentek attribútumokkal:");
			
			// 10) A dokumentum összes eleme
            // String e00xc3 = "//*";
            // printTextNodes(document, xPath, e00xc3, "10. Dokumentum összes eleme:");

			// 11) Az összes student, ahol a kor > 20
            // String e00xc3 = "//student[kor>20]";
            // printStudents(document, xPath, e00xc3, "11. Hallgatók, akiknek a kor > 20:");
			
			// 12) Az összes student keresztnev vagy vezeteknev csomópontja
            String e00xc3 = "//student/keresztnev | //student/vezeteknev";
            printTextNodes(document, xPath, e00xc3, "12. Összes kereszt- és vezetéknév:");
            
		} catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException e) {
            e.printStackTrace();
        }
	}
	
	@SuppressWarnings("unused")
	private static void printStudents(Document document, XPath xPath, String expression, String title)
            throws XPathExpressionException {
        System.out.println("\n" + title);
        NodeList E00XC3 = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);
        for (int i = 0; i < E00XC3.getLength(); i++) {
            Node node = E00XC3.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                System.out.println("Hallgató ID: " + element.getAttribute("id"));
                System.out.println("Vezetéknév: " + element.getElementsByTagName("vezeteknev").item(0).getTextContent());
                System.out.println("Keresztnév: " + element.getElementsByTagName("keresztnev").item(0).getTextContent());
                System.out.println("Becenév: " + element.getElementsByTagName("becenev").item(0).getTextContent());
                System.out.println("Kor: " + element.getElementsByTagName("kor").item(0).getTextContent());
                System.out.println();
            }
        }
    }
	
	@SuppressWarnings("unused")
	private static void printTextNodes(Document document, XPath xPath, String expression, String title)
            throws XPathExpressionException {
        System.out.println("\n" + title);
        NodeList E00XC3 = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);
        for (int i = 0; i < E00XC3.getLength(); i++) {
            System.out.println(" - " + E00XC3.item(i).getNodeName());
        }
    }
}

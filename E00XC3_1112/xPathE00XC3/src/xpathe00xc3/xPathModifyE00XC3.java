package xpathe00xc3;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class xPathModifyE00XC3 {

	public static void main(String[] args) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder;
		Document doc = null;
		
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse("studentE00XC3.xml");
			
			doc.getDocumentElement().normalize();
			
			XPathFactory xpathfactory = XPathFactory.newInstance();
			
			XPath xpath = xpathfactory.newXPath();
			
			XPathExpression e00xc3 = xpath.compile("//student[@id='01']");
			
			NodeList E00XC3 = (NodeList) e00xc3.evaluate(doc, XPathConstants.NODESET);
			
			for (int i = 0; i < E00XC3.getLength(); i++) {
				Node nNode = E00XC3.item(i);
				System.out.println("Aktuális elem: " + nNode.getNodeName());
				
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					
					Element nElement = (Element) nNode;
					
					nElement.getElementsByTagName("keresztnev").item(0).setTextContent("Benedek");
					nElement.getElementsByTagName("becenev").item(0).setTextContent("Beni");

					System.out.println("Hallgató ID: " + nElement.getAttribute("id"));
					System.out.println("Keresztnév: " + nElement.getElementsByTagName("keresztnev").item(0).getTextContent());
					System.out.println("Vezetéknév: " + nElement.getElementsByTagName("vezeteknev").item(0).getTextContent());		
					System.out.println("Becenév: " + nElement.getElementsByTagName("becenev").item(0).getTextContent());
					System.out.println("Kor: " + nElement.getElementsByTagName("kor").item(0).getTextContent());
				}
			}
		} catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException e) {
            e.printStackTrace();
        }
	}

}

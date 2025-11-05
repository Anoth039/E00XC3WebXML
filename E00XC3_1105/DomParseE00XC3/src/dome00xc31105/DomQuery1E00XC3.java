package dome00xc31105;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomQuery1E00XC3 {

	public static void main(String[] args) {
		try {
            File inputFile = new File("orarendE00XC3.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            removeWhitespaceNodes(doc.getDocumentElement());

            System.out.println("Gyökérelem: " + doc.getDocumentElement().getNodeName());

            NodeList oraLista = doc.getElementsByTagName("ora");

            List<String> kurzusNevek = new ArrayList<>();

            for (int i = 0; i < oraLista.getLength(); i++) {
                Node node = oraLista.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element oraElem = (Element) node;
                    String targyNev = oraElem.getElementsByTagName("targy").item(0).getTextContent();
                    kurzusNevek.add(targyNev);
                }
            }

            System.out.println("\n1.) Kurzusnév lista: " + kurzusNevek);

            Node elsoOra = oraLista.item(0);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource oraSource = new DOMSource(elsoOra);

            System.out.println("\n2.) Első óra XML formátumban:");
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(oraSource, consoleResult);

            StreamResult fileResult = new StreamResult(new File("orarendElsoOraE00XC3.xml"));
            transformer.transform(oraSource, fileResult);

            List<String> oktatok = new ArrayList<>();

            for (int i = 0; i < oraLista.getLength(); i++) {
                Node node = oraLista.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element oraElem = (Element) node;
                    String oktatoNev = oraElem.getElementsByTagName("oktato").item(0).getTextContent();
                    oktatok.add(oktatoNev);
                }
            }

            System.out.println("\n3.) Oktatók nevei: " + oktatok);

            System.out.println("\n4.) Összetett lekérdezés – hétfői gyakorlatok:");
            for (int i = 0; i < oraLista.getLength(); i++) {
                Node node = oraLista.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element oraElem = (Element) node;
                    String tipus = oraElem.getAttribute("tipus");
                    String nap = oraElem.getElementsByTagName("nap").item(0).getTextContent();

                    if (tipus.equals("gyakorlat") && nap.equals("Hétfő")) {
                        String targy = oraElem.getElementsByTagName("targy").item(0).getTextContent();
                        String helyszin = oraElem.getElementsByTagName("helyszin").item(0).getTextContent();
                        System.out.println(" - " + targy + " (" + helyszin + ")");
                    }
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

	}
	
	private static void removeWhitespaceNodes(Node node) {
	    Node child = node.getFirstChild();
	    while (child != null) {
	        Node next = child.getNextSibling();
	        if (child.getNodeType() == Node.TEXT_NODE) {
	            String text = child.getTextContent();
	            if (text.trim().isEmpty()) {
	                node.removeChild(child);
	            }
	        } else if (child.hasChildNodes()) {
	            removeWhitespaceNodes(child);
	        }
	        child = next;
	    }
	}

}

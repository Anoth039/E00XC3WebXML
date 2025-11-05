package dome00xc31105;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomQueryE00XC3 {

	public static void main(String[] args) {
		try {
            File inputFile = new File("hallgatoE00XC3.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            System.out.println("Gyökérelem: " + doc.getDocumentElement().getNodeName());

            NodeList hallgatoLista = doc.getElementsByTagName("hallgato");

            System.out.println("\n--- Hallgatók vezetéknevei ---");

            for (int i = 0; i < hallgatoLista.getLength(); i++) {
                Node node = hallgatoLista.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element hallgatoElem = (Element) node;
                    String vezeteknev = hallgatoElem
                            .getElementsByTagName("vezeteknev")
                            .item(0)
                            .getTextContent();

                    System.out.println("Hallgató " + (i + 1) + " vezetéknév: " + vezeteknev);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

	}

}

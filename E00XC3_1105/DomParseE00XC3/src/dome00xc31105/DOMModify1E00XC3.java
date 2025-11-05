package dome00xc31105;

import java.io.File;

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

public class DOMModify1E00XC3 {

	public static void main(String[] args) {
		try {
            File inputFile = new File("orarendE00XC3.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            removeWhitespaceNodes(doc.getDocumentElement());

            System.out.println("Eredeti gyökérelem: " + doc.getDocumentElement().getNodeName());

            Element root = doc.getDocumentElement();

            Element ujOra = doc.createElement("ora");
            ujOra.setAttribute("id", "99");
            ujOra.setAttribute("tipus", "eloadas");

            Element targy = doc.createElement("targy");
            targy.appendChild(doc.createTextNode("Adatbázis rendszerek"));

            Element idopont = doc.createElement("idopont");
            Element nap = doc.createElement("nap");
            nap.appendChild(doc.createTextNode("Péntek"));
            Element tol = doc.createElement("tol");
            tol.appendChild(doc.createTextNode("08:00"));
            Element ig = doc.createElement("ig");
            ig.appendChild(doc.createTextNode("10:00"));
            idopont.appendChild(nap);
            idopont.appendChild(tol);
            idopont.appendChild(ig);

            Element helyszin = doc.createElement("helyszin");
            helyszin.appendChild(doc.createTextNode("A2/105"));

            Element oktato = doc.createElement("oktato");
            oktato.appendChild(doc.createTextNode("Dr. Orosz Tamás"));

            Element szak = doc.createElement("szak");
            szak.appendChild(doc.createTextNode("Mérnökinformatikus BSc"));

            Element oraado = doc.createElement("oraado");
            oraado.appendChild(doc.createTextNode("Tóth László"));

            ujOra.appendChild(targy);
            ujOra.appendChild(idopont);
            ujOra.appendChild(helyszin);
            ujOra.appendChild(oktato);
            ujOra.appendChild(szak);
            ujOra.appendChild(oraado);

            root.appendChild(ujOra);

            NodeList oraLista = doc.getElementsByTagName("ora");
            for (int i = 0; i < oraLista.getLength(); i++) {
                Node oraNode = oraLista.item(i);
                if (oraNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element oraElem = (Element) oraNode;
                    String tipus = oraElem.getAttribute("tipus");
                    if ("gyakorlat".equals(tipus)) {
                        oraElem.setAttribute("tipus", "eloadas");
                    }
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(doc);

            System.out.println("\n--- Módosított dokumentum ---");
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);

            StreamResult fileResult = new StreamResult(new File("orarendModify1E00XC3.xml"));
            transformer.transform(source, fileResult);

            System.out.println("\nA módosított XML elmentve: orarendModify1E00XC3.xml");

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

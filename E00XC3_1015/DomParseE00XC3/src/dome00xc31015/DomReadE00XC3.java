package dome00xc31015;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;

public class DomReadE00XC3 {
    
    public static void main(String[] args) {
        try {
            File xmlFile = new File("orarendE00XC3.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);

            doc.getDocumentElement().normalize();

            System.out.println("Gyökérelem: " + doc.getDocumentElement().getNodeName());
            System.out.println("============================================");

            NodeList oraList = doc.getElementsByTagName("ora");

            for (int i = 0; i < oraList.getLength(); i++) {
                Node node = oraList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element oraElem = (Element) node;

                    String id = oraElem.getAttribute("id");
                    String tipus = oraElem.getAttribute("tipus");

                    String targy = oraElem.getElementsByTagName("targy").item(0).getTextContent();
                    Element idopontElem = (Element) oraElem.getElementsByTagName("idopont").item(0);
                    String nap = idopontElem.getElementsByTagName("nap").item(0).getTextContent();
                    String tol = idopontElem.getElementsByTagName("tol").item(0).getTextContent();
                    String ig = idopontElem.getElementsByTagName("ig").item(0).getTextContent();
                    String helyszin = oraElem.getElementsByTagName("helyszin").item(0).getTextContent();
                    String oktato = oraElem.getElementsByTagName("oktato").item(0).getTextContent();
                    String szak = oraElem.getElementsByTagName("szak").item(0).getTextContent();

                    System.out.println("Óra #" + id + " (" + tipus + ")");
                    System.out.println("Tantárgy: " + targy);
                    System.out.println("Nap: " + nap);
                    System.out.println("Időpont: " + tol + " - " + ig);
                    System.out.println("Helyszín: " + helyszin);
                    System.out.println("Oktató: " + oktato);
                    System.out.println("Szak: " + szak);
                    System.out.println("--------------------------------------------");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


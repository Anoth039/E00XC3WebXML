package xpathe00xc3;

import java.io.IOException;
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class xPathModify1E00XC3 {

	public static void main(String[] args) {
		try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // XML beolvasása
            Document document = builder.parse("orarendE00XC3.xml");
            document.getDocumentElement().normalize();

            XPath xPath = XPathFactory.newInstance().newXPath();

            // 1) Az id="05" órán a helyszín legyen "L401"
            Node ora5 = (Node) xPath.compile("//ora[@id='05']/helyszin")
                    .evaluate(document, XPathConstants.NODE);
            ora5.setTextContent("L401");
            
            // 2) Szerdai órák napjának módosítása csütörtökre
            NodeList szerdaiOrak = (NodeList) xPath.compile("//ora/idopont/nap[text()='Szerda']")
                    .evaluate(document, XPathConstants.NODESET);

            for (int i = 0; i < szerdaiOrak.getLength(); i++) {
                Node nap = szerdaiOrak.item(i);
                nap.setTextContent("Csütörtök");
            }
            
            // 3) Minden óra típus attribútumának felcserélése
            NodeList osszesOra = (NodeList) xPath.compile("//ora")
                    .evaluate(document, XPathConstants.NODESET);

            for (int i = 0; i < osszesOra.getLength(); i++) {

                Element o = (Element) osszesOra.item(i);
                String tipus = o.getAttribute("tipus");

                if (tipus.equals("gyakorlat")) {
                    o.setAttribute("tipus", "eloadas");
                } else if (tipus.equals("eloadas")) {
                    o.setAttribute("tipus", "gyakorlat");
                }
            }
            
            // 3)

            System.out.println("\nMódosítások sikeresen elvégezve.\n");


            // Konzolra írás
            System.out.println("===== MÓDOSÍTOTT ORARÉND TELJES TARTALMA =====\n");

            NodeList oraLista = document.getElementsByTagName("ora");

            for (int i = 0; i < oraLista.getLength(); i++) {

                Element ora = (Element) oraLista.item(i);

                System.out.println("Óra ID: " + ora.getAttribute("id"));
                System.out.println("Típus: " + ora.getAttribute("tipus"));
                System.out.println("Tárgy: " + ora.getElementsByTagName("targy").item(0).getTextContent());

                Element ido = (Element) ora.getElementsByTagName("idopont").item(0);
                System.out.println(" - Nap: " + ido.getElementsByTagName("nap").item(0).getTextContent());
                System.out.println(" - Tól: " + ido.getElementsByTagName("tol").item(0).getTextContent());
                System.out.println(" - Ig: " + ido.getElementsByTagName("ig").item(0).getTextContent());

                System.out.println("Helyszín: " + ora.getElementsByTagName("helyszin").item(0).getTextContent());
                System.out.println("Oktató: " + ora.getElementsByTagName("oktato").item(0).getTextContent());
                System.out.println("Szak: " + ora.getElementsByTagName("szak").item(0).getTextContent());

                System.out.println("------------------------------------\n");
            }


            // Fájlba mentés
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File("orarendE00XC31.xml"));

            transformer.transform(source, result);

            System.out.println("Fájl elmentve: orarendE00XC31.xml");

        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException | TransformerException e) {
            e.printStackTrace();
        }

	}

}

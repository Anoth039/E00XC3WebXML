package dome00xc31029;

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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class DomWriteE00XC31 {

	public static void main(String[] args) throws ParserConfigurationException, TransformerException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        Element root = doc.createElement("E00XC3_orarend");
        doc.appendChild(root);

        root.appendChild(createOra(doc, "01", "eloadas",
                "Elektrotecnika-elektronika", "Hétfő", "08:00", "09:50",
                "E3", "Szabó Norbert István", "Mérnökinformatikus BSc"));

        root.appendChild(createOra(doc, "02", "eloadas",
                "Web technológiák 1.", "Hétfő", "10:00", "12:00",
                "E5", "Dr. Agárdi Anita", "Mérnökinformatikus BSc"));

        root.appendChild(createOra(doc, "03", "gyakorlat",
                "Angol műszaki szaknyelv 1.", "Hétfő", "12:00", "14:00",
                "A1/101", "Dobronyi Eszter", "Mérnökinformatikus BSc"));

        root.appendChild(createOra(doc, "04", "gyakorlat",
                "Web technológiák 1.", "Hétfő", "14:00", "16:00",
                "L101", "Dr. Agárdi Anita", "Mérnökinformatikus BSc"));

        root.appendChild(createOra(doc, "05", "eloadas",
                "Mobil programozási alapok", "Hétfő", "16:00", "18:00",
                "L101", "Dr. Agárdi Anita", "Mérnökinformatikus BSc"));

        root.appendChild(createOra(doc, "06", "gyakorlat",
                "Mobil programozási alapok", "Hétfő", "18:00", "20:00",
                "L101", "Dr. Agárdi Anita", "Mérnökinformatikus BSc"));

        root.appendChild(createOra(doc, "07", "gyakorlat",
                "Windows Rendszergazda", "Kedd", "08:00", "10:00",
                "L101", "Dr. Wagner György", "Mérnökinformatikus BSc"));

        root.appendChild(createOra(doc, "08", "eloadas",
                "Mesterséges Intelligencia alapok", "Kedd", "10:00", "12:00",
                "E32", "Kunné Dr. Tamás Judit, Fazekas Levente Áron", "Mérnökinformatikus BSc"));

        root.appendChild(createOra(doc, "09", "gyakorlat",
                "Mesterséges Intelligencia alapok", "Kedd", "12:00", "14:00",
                "E32", "Kunné Dr. Tamás Judit, Fazekas Levente Áron", "Mérnökinformatikus BSc"));

        root.appendChild(createOra(doc, "10", "gyakorlat",
                "Elektrotecnika-elektronika", "Kedd", "14:00", "15:50",
                "A1/317", "Dr. Kozsely Gábor", "Mérnökinformatikus BSc"));

        root.appendChild(createOra(doc, "11", "gyakorlat",
                "Windows rendszergazda", "Kedd", "16:00", "18:00",
                "L103", "Dr. Wagner György", "Mérnökinformatikus BSc"));

        root.appendChild(createOra(doc, "12", "eloadas",
                "Webes adatkezelő környezetek", "Szerda", "8:00", "10:00",
                "A1/310", "Dr. Kovács László József", "Mérnökinformatikus BSc"));

        root.appendChild(createOra(doc, "13", "gyakorlat",
                "Webes adatkezelő környezetek", "Szerda", "10:00", "12:00",
                "L103", "Dr. Bednarik László", "Mérnökinformatikus BSc"));

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transf = transformerFactory.newTransformer();

        transf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transf.setOutputProperty(OutputKeys.INDENT, "yes");
        transf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        DOMSource source = new DOMSource(doc);
        File outFile = new File("orarend1E00XC3.xml");

        StreamResult console = new StreamResult(System.out);
        StreamResult file = new StreamResult(outFile);

        transf.transform(source, console);
        transf.transform(source, file);

    }

    private static Node createOra(Document doc, String id, String tipus, String targy,
    String nap, String tol, String ig, String helyszin, String oktato, String szak) {

        Element ora = doc.createElement("ora");
        ora.setAttribute("id", id);
        ora.setAttribute("tipus", tipus);

        ora.appendChild(createElement(doc, "targy", targy));

        Element idopont = doc.createElement("idopont");
        idopont.appendChild(createElement(doc, "nap", nap));
        idopont.appendChild(createElement(doc, "tol", tol));
        idopont.appendChild(createElement(doc, "ig", ig));
        ora.appendChild(idopont);

        ora.appendChild(createElement(doc, "helyszin", helyszin));
        ora.appendChild(createElement(doc, "oktato", oktato));
        ora.appendChild(createElement(doc, "szak", szak));

        return ora;
    }

    private static Element createElement(Document doc, String name, String value) {
        Element elem = doc.createElement(name);
        elem.appendChild(doc.createTextNode(value));
        return elem;
    }
}

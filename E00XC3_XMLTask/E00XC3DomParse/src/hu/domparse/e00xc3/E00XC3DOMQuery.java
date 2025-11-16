package hu.domparse.e00xc3;

import java.io.File;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class E00XC3DOMQuery {

	public static void main(String[] args) {
		try {
			// XML fájl beolvasása
			File inputFile = new File("E00XC3XML.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            
            // 1. Lekérdezés: Kölcsönzők, akik szombaton nyitva vannak
            System.out.println("1. Kölcsönzők nyitva szombaton:");
            NodeList kolcsonzok = doc.getElementsByTagName("kolcsonzo");
            for (int i = 0; i < kolcsonzok.getLength(); i++) {
                Element kolcsonzoElem = (Element) kolcsonzok.item(i);
                NodeList nyitvatartasok = kolcsonzoElem.getElementsByTagName("nyitvatartas");
                for (int j = 0; j < nyitvatartasok.getLength(); j++) {
                    Element nyitElem = (Element) nyitvatartasok.item(j);
                    String nap = getTagValue(nyitElem, "nap");
                    if ("Szombat".equals(nap)) { 
                        System.out.println("  - " + getTagValue(kolcsonzoElem, "nev") + " nyitva: " + getTagValue(nyitElem, "intervallum"));
                    }
                }
            }
            System.out.println("-----------------------------------------------");
            
            // 2. Lekérdezés: Ügyfelek B kategóriás jogosítvánnyal és érvényesség
            System.out.println("2. Ügyfelek B kategóriás jogosítvánnyal:");
            NodeList ugyfelek = doc.getElementsByTagName("ugyfel");
            for (int i = 0; i < ugyfelek.getLength(); i++) {
                Element uElem = (Element) ugyfelek.item(i);

                NodeList jogositvanyok = uElem.getElementsByTagName("jogositvany");
                for (int j = 0; j < jogositvanyok.getLength(); j++) {
                    Element jog = (Element) jogositvanyok.item(j);
                    if ("B".equals(getTagValue(jog, "kategoria"))) {
                        String ervenyesseg = getTagValue(jog, "ervenyesseg");
                        System.out.println("  - " + getTagValue(uElem, "nev") + " (Érvényes: " + ervenyesseg + ")");
                    }
                }
            }
            System.out.println("-----------------------------------------------");
            
            // 3. Lekérdezés: Szolgáltatások, melyeknek ára 5000 Ft felett, de 10000 Ft alatt van
            System.out.println("3. Szolgáltatások 5000-10000 Ft között:");
            NodeList szolgaltatasok = doc.getElementsByTagName("szolgaltatas");

            for (int i = 0; i < szolgaltatasok.getLength(); i++) {
                Element szElem = (Element) szolgaltatasok.item(i);
                int koltseg = Integer.parseInt(getTagValue(szElem, "koltseg"));

                if (koltseg > 5000 && koltseg < 10000) {
                    String nev = getTagValue(szElem, "nev");
                    String leiras = getTagValue(szElem, "leiras");
                    System.out.println("  - " + nev + " (" + koltseg + " Ft): " + leiras);
                }
            }
            System.out.println("-----------------------------------------------");
                
            // 4. Lekérdezés: Bérlésenkénti összköltség ügyfél nevével együtt
            System.out.println("4. Bérlésenkénti összköltség (napidíj a napok alapján, kilométerdíj 100 km-rel számolva):");
            NodeList berlesek = doc.getElementsByTagName("berles");

            for (int i = 0; i < berlesek.getLength(); i++) {
                Element bElem = (Element) berlesek.item(i);
                Element dijElem = (Element) bElem.getElementsByTagName("dij").item(0);

                int kaucio = Integer.parseInt(getTagValue(dijElem, "kaucio"));
                int napidijPerNap = Integer.parseInt(getTagValue(dijElem, "napidij"));
                int kilometerdij = Integer.parseInt(getTagValue(dijElem, "kilometerdij")) * 100; // 100 km

                // Bérlés kezdete és vége
                String kezdeteStr = getTagValue(bElem, "kezdete");
                String vegeStr = getTagValue(bElem, "vege");

                LocalDate kezdete = LocalDate.parse(kezdeteStr);
                LocalDate vege = LocalDate.parse(vegeStr);
                long napokSzama = ChronoUnit.DAYS.between(kezdete, vege) + 1;

                int berlesKoltseg = kaucio + (int)(napidijPerNap * napokSzama) + kilometerdij;

                // Jármű adatai
                Element jarmuElem = (Element) bElem.getElementsByTagName("jarmu").item(0);
                String marka = getTagValue(jarmuElem, "marka");
                String tipus = getTagValue(jarmuElem, "tipus");

                // Ügyfél keresése kód alapján
                String kuu = bElem.getAttribute("k_u_u");
                String ugyfelNev = "";
                for (int j = 0; j < ugyfelek.getLength(); j++) {
                    Element uElem = (Element) ugyfelek.item(j);
                    if (kuu.equals(uElem.getAttribute("ukod"))) {
                        ugyfelNev = getTagValue(uElem, "nev");
                        break;
                    }
                }

                System.out.println("  - " + marka + " " + tipus + " bérlés (ügyfél: " + ugyfelNev + ") költsége: " + berlesKoltseg + " Ft");
            }
            System.out.println("-----------------------------------------------");
            
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Segédfüggvény tag értékének lekérésére
	private static String getTagValue(Element parent, String tagName) {
	    NodeList list = parent.getElementsByTagName(tagName);
	    return list.getLength() > 0 ? list.item(0).getTextContent() : "";
	}
}

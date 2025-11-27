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
            
            // Normalizálás
            doc.getDocumentElement().normalize();
            
            // 1. lekérdezés: Kölcsönzők listázása amelyek szombaton nyitva vannak
            System.out.println("1. lekérdezés: Kölcsönzők nyitva szombaton");

            NodeList kolcsonzok = doc.getElementsByTagName("kolcsonzo");

            for (int i = 0; i < kolcsonzok.getLength(); i++) {
                Element kolcsonzoElem = (Element) kolcsonzok.item(i);
                String kkod = kolcsonzoElem.getAttribute("kkod");

                NodeList nyitvatartasok = kolcsonzoElem.getElementsByTagName("nyitvatartas");
                for (int j = 0; j < nyitvatartasok.getLength(); j++) {
                    Element nyitElem = (Element) nyitvatartasok.item(j);
                    String nap = nyitElem.getElementsByTagName("nap").item(0).getTextContent();
                    if ("Szombat".equals(nap)) {
                        String intervallum = nyitElem.getElementsByTagName("intervallum").item(0).getTextContent();
                        
                        Element cim = (Element) kolcsonzoElem.getElementsByTagName("cim").item(0);
                        String cimStr = cim.getElementsByTagName("varos").item(0).getTextContent() + ", " +
                                        cim.getElementsByTagName("utca").item(0).getTextContent() + " " +
                                        cim.getElementsByTagName("hazszam").item(0).getTextContent();
                        
                        NodeList elerhetosegek = kolcsonzoElem.getElementsByTagName("elerhetoseg");
                        StringBuilder elStr = new StringBuilder();
                        for (int k = 0; k < elerhetosegek.getLength(); k++) {
                            elStr.append(elerhetosegek.item(k).getTextContent());
                            if (k < elerhetosegek.getLength() - 1) elStr.append(", ");
                        }

                        System.out.println("\nKölcsönző kód: " + kkod);
                        System.out.println("Név: " + kolcsonzoElem.getElementsByTagName("nev").item(0).getTextContent());
                        System.out.println("Cím: " + cimStr);
                        System.out.println("Elérhetőségek: " + elStr);
                        System.out.println("Nyitva: " + intervallum);
                    }
                }
            }


         // 2. lekérdezés: Legdrágább szolgáltatás megkeresése kölcsönzőként
            System.out.println("\n2. lekérdezés: Minden kölcsönző legdrágább szolgáltatása");

            NodeList szList = doc.getElementsByTagName("szolgaltatas");

            for (int i = 0; i < kolcsonzok.getLength(); i++) {
                Element kolcsonzoElem = (Element) kolcsonzok.item(i);
                String kkod = kolcsonzoElem.getAttribute("kkod");

                Element maxSz = null;
                int maxAr = -1;

                for (int j = 0; j < szList.getLength(); j++) {
                    Element sz = (Element) szList.item(j);
                    if (kkod.equals(sz.getAttribute("k_sz"))) {
                        int ar = Integer.parseInt(sz.getElementsByTagName("koltseg").item(0).getTextContent());
                        if (ar > maxAr) {
                            maxAr = ar;
                            maxSz = sz;
                        }
                    }
                }

                if (maxSz != null) {
                    System.out.println("\nKölcsönző kód: " + kkod);
                    System.out.println("Szolgáltatás kód: " + maxSz.getAttribute("szk"));
                    System.out.println("Szolgáltatás név: " + maxSz.getElementsByTagName("nev").item(0).getTextContent());
                    System.out.println("Leírás: " + maxSz.getElementsByTagName("leiras").item(0).getTextContent());
                    System.out.println("Ár: " + maxAr + " Ft");
                }
            }

            
            // 3. lekérdezés: Azon ügyfelek listázása akik rendelkeznek egyszerre B és A kategóriás jogosítvánnyal
            System.out.println("\n3. lekérdezés: Ügyfelek, akiknek van B és A kategóriás jogosítványuk");

            NodeList ugyfelek = doc.getElementsByTagName("ugyfel");

            for (int i = 0; i < ugyfelek.getLength(); i++) {
                Element uElem = (Element) ugyfelek.item(i);
                NodeList jogositvanyok = uElem.getElementsByTagName("jogositvany");

                boolean hasB = false;
                boolean hasA = false;

                for (int j = 0; j < jogositvanyok.getLength(); j++) {
                    Element jog = (Element) jogositvanyok.item(j);
                    String kategoria = jog.getElementsByTagName("kategoria").item(0).getTextContent();

                    if ("B".equals(kategoria)) hasB = true;
                    if ("A".equals(kategoria)) hasA = true;
                }

                if (hasB && hasA) {
                    String ukod = uElem.getAttribute("ukod");
                    String nev = uElem.getElementsByTagName("nev").item(0).getTextContent();

                    Element cimElem = (Element) uElem.getElementsByTagName("cim").item(0);
                    String cimStr = cimElem.getElementsByTagName("varos").item(0).getTextContent() + ", " +
                                    cimElem.getElementsByTagName("utca").item(0).getTextContent() + " " +
                                    cimElem.getElementsByTagName("hazszam").item(0).getTextContent();

                    NodeList elerhetosegek = uElem.getElementsByTagName("elerhetoseg");
                    StringBuilder elStr = new StringBuilder();
                    for (int k = 0; k < elerhetosegek.getLength(); k++) {
                        elStr.append(elerhetosegek.item(k).getTextContent());
                        if (k < elerhetosegek.getLength() - 1) elStr.append(", ");
                    }

                    System.out.println("\nÜgyfél kód: " + ukod);
                    System.out.println("Név: " + nev);
                    System.out.println("Cím: " + cimStr);
                    System.out.println("Elérhetőségek: " + elStr);
                    
                    System.out.println("Jogosítványok:");
                    for (int j = 0; j < jogositvanyok.getLength(); j++) {
                        Element jog = (Element) jogositvanyok.item(j);
                        String kategoria = jog.getElementsByTagName("kategoria").item(0).getTextContent();
                        String ervenyesseg = jog.getElementsByTagName("ervenyesseg").item(0).getTextContent();
                        System.out.println("  - Kategória: " + kategoria + ", Érvényesség: " + ervenyesseg);
                    }
                }
            }

            // 4. lekérdezés: Bérlések napidíjának kiírása az intervallum alapján
            System.out.println("\n4. lekérdezés: Bérlések összesített napidíja");

            NodeList berlesek = doc.getElementsByTagName("berles");

            for (int i = 0; i < berlesek.getLength(); i++) {
                Element bElem = (Element) berlesek.item(i);
                
                // Bérlés kezdete és vége
                LocalDate kezdete = LocalDate.parse(bElem.getElementsByTagName("kezdete").item(0).getTextContent());
                LocalDate vege = LocalDate.parse(bElem.getElementsByTagName("vege").item(0).getTextContent());
                
                long napokSzama = ChronoUnit.DAYS.between(kezdete, vege) + 1; // mindkét nap számít

                Element dijElem = (Element) bElem.getElementsByTagName("dij").item(0);
                int napidijPerNap = Integer.parseInt(dijElem.getElementsByTagName("napidij").item(0).getTextContent());
                
                int napidijOsszesen = (int) (napidijPerNap * napokSzama);

                // Ügyfél nevének keresése kód alapján
                String ugyfelKod = bElem.getAttribute("k_u_u");
                String ugyfelNev = "";
                for (int j = 0; j < ugyfelek.getLength(); j++) {
                    Element uElem = (Element) ugyfelek.item(j);
                    if (ugyfelKod.equals(uElem.getAttribute("ukod"))) {
                        ugyfelNev = uElem.getElementsByTagName("nev").item(0).getTextContent();
                        break;
                    }
                }

                // Jármű adatai
                Element jarmuElem = (Element) bElem.getElementsByTagName("jarmu").item(0);
                String marka = jarmuElem.getElementsByTagName("marka").item(0).getTextContent();
                String tipus = jarmuElem.getElementsByTagName("tipus").item(0).getTextContent();

                System.out.println("\nBérlés: " + marka + " " + tipus + " (ügyfél: " + ugyfelNev + ")");
                System.out.println("Intervallum: " + kezdete + " - " + vege + " (" + napokSzama + " nap)");
                System.out.println("Napidíj: " + napidijPerNap + " Ft/nap, Összesen: " + napidijOsszesen + " Ft");
            }
            
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

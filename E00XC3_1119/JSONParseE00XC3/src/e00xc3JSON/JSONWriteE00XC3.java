package e00xc3JSON;

import java.io.FileReader;
import java.io.FileWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JSONWriteE00XC3 {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		try (FileReader reader = new FileReader("orarendE00XC3.json")) {
			
			JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            JSONObject root = (JSONObject) jsonObject.get("E00XC3_orarend");
            JSONArray lessons = (JSONArray) root.get("ora");

            System.out.println("Órarend blokk formában:\n");

            JSONArray newLessons = new JSONArray();

            for (int i = 0; i < lessons.size(); i++) {

                JSONObject lesson = (JSONObject) lessons.get(i);
                JSONObject time = (JSONObject) lesson.get("idopont");

                System.out.println("===============================");
                System.out.println("Tárgy: " + lesson.get("targy"));
                System.out.println("Időpont: " + time.get("nap") + " " + time.get("tol") + "-" + time.get("ig"));
                System.out.println("Helyszín: " + lesson.get("helyszin"));
                System.out.println("Oktató: " + lesson.get("oktato"));
                System.out.println("Szak: " + lesson.get("szak"));

                JSONObject newLesson = new JSONObject();
                newLesson.put("targy", lesson.get("targy"));
                newLesson.put("helyszin", lesson.get("helyszin"));
                newLesson.put("oktato", lesson.get("oktato"));
                newLesson.put("szak", lesson.get("szak"));
                newLesson.put("idopont", time);

                newLessons.add(newLesson);
            }

            try (FileWriter writer = new FileWriter("orarendE00XC31.json")) {

                writer.write("{\n");
                writer.write("  \"E00XC3_orarend\": {\n");
                writer.write("    \"ora\": [\n");

                for (int i = 0; i < newLessons.size(); i++) {
                    JSONObject lesson = (JSONObject) newLessons.get(i);
                    JSONObject idopont = (JSONObject) lesson.get("idopont");

                    writer.write("      {\n");
                    writer.write("        \"targy\": \"" + lesson.get("targy") + "\",\n");

                    writer.write("        \"idopont\": {\n");
                    writer.write("          \"nap\": \"" + idopont.get("nap") + "\",\n");
                    writer.write("          \"tol\": \"" + idopont.get("tol") + "\",\n");
                    writer.write("          \"ig\": \"" + idopont.get("ig") + "\"\n");
                    writer.write("        },\n");

                    writer.write("        \"helyszin\": \"" + lesson.get("helyszin") + "\",\n");
                    writer.write("        \"oktato\": \"" + lesson.get("oktato") + "\",\n");
                    writer.write("        \"szak\": \"" + lesson.get("szak") + "\"\n");

                    writer.write("      }");

                    if (i < newLessons.size() - 1) {
                        writer.write(",");
                    }
                    writer.write("\n");
                }

                writer.write("    ]\n");
                writer.write("  }\n");
                writer.write("}\n");

                System.out.println("\nJSON fájl sikeresen létrehozva: orarendE00XC31.json");
            }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

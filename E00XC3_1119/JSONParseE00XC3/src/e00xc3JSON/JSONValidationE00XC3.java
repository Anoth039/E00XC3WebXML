package e00xc3JSON;

import java.io.FileReader;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JSONValidationE00XC3 {

	public static void main(String[] args) {
		try (FileReader schemaReader = new FileReader("orarendE00XC3Schema.json");
	             FileReader jsonReader = new FileReader("orarendE00XC3.json")) {

	            JSONObject rawSchema = new JSONObject(new JSONTokener(schemaReader));
	            Schema schema = SchemaLoader.load(rawSchema);

	            JSONObject jsonData = new JSONObject(new JSONTokener(jsonReader));

	            schema.validate(jsonData);
	            System.out.println("Validation SUCCESS: JSON megfelel a sémának!");

	        } catch (ValidationException ve) {
	            System.out.println("Validation ERROR:");
	            ve.getAllMessages().forEach(System.out::println);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}
}

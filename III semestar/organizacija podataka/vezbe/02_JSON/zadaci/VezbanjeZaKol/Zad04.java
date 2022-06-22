package VezbanjeZaKol;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class Zad04 {
	public static void main(String[] args) {
		try (
				Reader reader = new FileReader("resources/01_polasci.csv");
				FileWriter writer = new FileWriter("resources/04_ispis.yaml");
		) {
			CsvToBean<Polazak> csv = new CsvToBeanBuilder<Polazak>(reader)
					.withSeparator(',')
					.withType(Polazak.class)
					.withIgnoreQuotations(true)
					.build();
			List<Polazak> li = csv.parse();
			
			List<PolazakSpec04> li2 = new ArrayList<PolazakSpec04>();
			for (Polazak p : li) {
				li2.add(new PolazakSpec04(p));
			}
			
			Yaml yaml = new Yaml(new Constructor(PolazakSpec04.class));
			yaml.dump(li2, writer);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		try (
			Reader reader = new FileReader("resources/04_ispis.yaml");
			FileWriter writer = new FileWriter("resources/04_ispis.json");
				) {
			Yaml yaml = new Yaml();
			
			// Kompajler ne zna da ce resneje biti ArrayList<PolazakSpec04> ali mi znamo
			@SuppressWarnings("unchecked")
			ArrayList<PolazakSpec04> li = (ArrayList<PolazakSpec04>) yaml.load(reader);
			
			ObjectMapper obj = new ObjectMapper();
			obj.writeValue(writer, li);

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

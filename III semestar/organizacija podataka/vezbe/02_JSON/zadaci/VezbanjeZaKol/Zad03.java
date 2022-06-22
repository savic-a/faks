package VezbanjeZaKol;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class Zad03 {
	public static void main(String[] args) {
		try (Reader r = new FileReader("resources/01_polasci.csv");) {
			CsvToBean<Polazak> csv = new CsvToBeanBuilder<Polazak>(r)
					.withType(Polazak.class)
					.withSeparator(',')
					.build();
			List<Polazak> li = csv.parse();
			
			List<Polazak> has = new ArrayList<Polazak>();
			List<Polazak> hasnt = new ArrayList<Polazak>();
			
			for (Polazak p : li) {
				boolean found = false;
				for (Polazak q : li) {
					if (p == q) continue;
					if (p.getPolazak().equals(q.getDolazak()) && p.getDolazak().equals(q.getPolazak())) {
						found = true;
						break;
					}
				}
				
				if (found) {
					has.add(p);
				} else {
					hasnt.add(p);
				}
			}
			
			FileWriter w1 = new FileWriter("resources/03_has.yaml");
			FileWriter w2 = new FileWriter("resources/03_hasnt.json");
			
			Yaml yaml = new Yaml(new Constructor(Polazak.class));
			yaml.dump(has, w1);
			
			ObjectMapper mp = new ObjectMapper();
			mp.enable(SerializationFeature.INDENT_OUTPUT);		
			mp.writeValue(w2, hasnt);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

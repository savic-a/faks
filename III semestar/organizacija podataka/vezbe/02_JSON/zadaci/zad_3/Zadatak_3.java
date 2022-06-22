package zad_3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import zad_1.Putanja;

public class Zadatak_3 {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		List<Putanja> putanja = citanjeFajla();
		Map<String, List<Putanja>> mapa = new HashMap<>();
		kreirajMapu(putanja, mapa);
		kreirajFajlove(putanja, mapa);

	}


	private static void kreirajFajlove(List<Putanja> putanja, Map<String, List<Putanja>> mapa) throws JsonGenerationException, JsonMappingException, IOException {
		List<Putanja> nemaju = new ArrayList<Putanja>();
		List<Putanja> imaju = new ArrayList<Putanja>();
		
		for(String p : mapa.keySet()) {
			List<Putanja> relacija = mapa.get(p);
			if(!mapa.containsKey(relacija.get(0).getDolazak()+"-"+relacija.get(0).getPolazak())) {
				nemaju.addAll(relacija);
			}else {
				imaju.addAll(relacija);
			}
		}
		//JSON
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.writeValue(new File("02_JSON/zadaci/zad_3.json"), nemaju);
		
		//YAML
		Yaml yaml = new Yaml();
		try(Writer writer = new FileWriter("02_JSON/zadaci/zad_3.yaml")){
			yaml.dump(imaju, writer);
		}
		
		
	}


	private static void kreirajMapu(List<Putanja> putanja, Map<String, List<Putanja>> mapa) {
		for(Putanja p : putanja) {
			if(!mapa.containsKey(p.getPolazak()+"-"+p.getDolazak())) {
				List<Putanja> lista = new ArrayList<Putanja>();
				lista.add(p);
				mapa.put(p.getPolazak()+"-"+p.getDolazak(), lista);
			}else {
				mapa.get(p.getPolazak()+"-"+p.getDolazak()).add(p);
			}
		}
		
	}


	private static List<Putanja> citanjeFajla() throws FileNotFoundException, IOException {
		List<Putanja> putanja = new ArrayList<Putanja>();
		try(Reader reader = new FileReader("02_JSON/zadaci/putanje.csv")){
			CsvToBean<Putanja> csv = new CsvToBeanBuilder<Putanja>(reader).withSeparator(',').withType(Putanja.class).build();
			putanja = csv.parse();
		}
		return putanja;
	}

	

}

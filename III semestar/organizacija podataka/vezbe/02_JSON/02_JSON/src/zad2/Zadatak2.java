package zad2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class Zadatak2 {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		List<Contry> contries = procitajFajl();
		System.out.println(contries);
		
		Map<String, List<Contry>> contriesMap = new HashMap<>();
		kreiranjeMape(contries, contriesMap);
		
		kreiranjeFajlova(contries,contriesMap);

	}

	private static void kreiranjeFajlova(List<Contry> contries, Map<String, List<Contry>> contriesMap) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		
		for(String continent : contriesMap.keySet()) {
			mapper.writeValue(new File("02_JSON/resources/zad2/"+continent+".json"), contriesMap.get(continent));
		}
		
	}

	private static void kreiranjeMape(List<Contry> contries, Map<String, List<Contry>> contriesMap) {
		for(Contry contry : contries) {
			if(!contriesMap.containsKey(contry.getContinentName())) {
				List<Contry> oneContry = new ArrayList<Contry>();
				oneContry.add(contry);
				contriesMap.put(contry.getContinentName(), oneContry);
			}
			else {
				contriesMap.get(contry.getContinentName()).add(contry);
			}
		}
	}

	private static List<Contry> procitajFajl() throws FileNotFoundException, IOException {
		String FILE_NAME = "02_JSON/resources/countries_cities.csv";
		
		List<Contry> contries = new ArrayList<Contry>();
		
		try(Reader reader = new FileReader(FILE_NAME)) {
			CsvToBean<Contry> csv = new CsvToBeanBuilder<Contry>(reader).withType(Contry.class).withSeparator(',').build();
			contries = csv.parse();
		}
		
		return contries;
	}

}

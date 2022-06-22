package zad_4;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.yaml.snakeyaml.Yaml;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import zad_1.Putanja;

public class Zadatak_4 {
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		List<Putanja> putanje = citanjeCsv();
		Map<String, List<Putanja>> mapa = kreiranjeMape(putanje);
		kreiranjeYaml(putanje, mapa);
		List<Putanja> putanjeYaml = citanjeYaml(); 
		kreiranjeJson(putanjeYaml);

	}

	private static void kreiranjeJson(List<Putanja> putanjeYaml) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.writeValue(new File("02_JSON/zadaci/zad_4.json"), putanjeYaml);
	}

	
	@SuppressWarnings("unchecked")
	private static List<Putanja> citanjeYaml() throws FileNotFoundException, IOException {
		List<Putanja> putanjeYaml = new ArrayList<Putanja>();
		
		Yaml yaml = new Yaml();
		try(InputStream is = new FileInputStream(new File("02_JSON/zadaci/zad_4.yaml"))){
			putanjeYaml = (List<Putanja>) yaml.load(is);
		}
		return putanjeYaml;
	}

	private static void kreiranjeYaml(List<Putanja> putanje, Map<String, List<Putanja>> mapa) throws IOException {
		List<Putanja> izabrani = new ArrayList<Putanja>();
		
		System.out.println("Unesite q za kraj, enter za nastavak");
		try(Scanner sc = new Scanner(System.in)){
			while(!sc.nextLine().equals("q") || !sc.nextLine().equals("Q")) {
				System.out.println("Mesto polaska: ");
				String polazak = sc.nextLine();
				System.out.println("Mesto dolaska: ");
				String dolazak = sc.nextLine();
				System.out.println("Vreme polaska: ");
				String vreme = sc.nextLine();
				System.out.println("Unesite q za kraj, enter za nastavak");
				
				if(!mapa.containsKey(polazak+"-"+dolazak)) { 
					System.out.println("Ne postoji nijedna relacija medju unesenim gradovima :(");
					break;
				}
				
				for(Putanja p : mapa.get(polazak+"-"+dolazak)) {
					if(vreme.equals(p.getVremePolazak())) {
						izabrani.add(p);
					}
					else 
						continue;
				}
				if(izabrani.isEmpty()) {
					System.out.println("Ne postoji ni jedna relacija koja zadovoljava trazene uslove.");
					break;
				}
			}
			
			//yaml pisanje
			Yaml yaml = new Yaml();
			try(Writer writer = new FileWriter("02_JSON/zadaci/zad_4.yaml")){
				yaml.dump(izabrani, writer);
			}
			izabrani = new ArrayList<Putanja>();
		}
		
	}

	private static Map<String, List<Putanja>> kreiranjeMape(List<Putanja> putanje) {
		Map<String, List<Putanja>> mapa = new HashMap<>();
		for(Putanja p : putanje) {
			if(!mapa.containsKey(p.getPolazak()+"-"+p.getDolazak())) {
				List<Putanja> lista = new ArrayList<Putanja>();
				lista.add(p);
				mapa.put(p.getPolazak()+"-"+p.getDolazak(), lista);
			}else {
				mapa.get(p.getPolazak()+"-"+p.getDolazak()).add(p);
			}
		}
		return mapa;
	}

	private static List<Putanja> citanjeCsv() throws FileNotFoundException, IOException {
		List<Putanja> putanje = new ArrayList<Putanja>();
		try(Reader reader = new FileReader("02_JSON/zadaci/putanje.csv")){
			CsvToBean<Putanja> csv = new CsvToBeanBuilder<Putanja>(reader).withSeparator(',').withType(Putanja.class).build();
			putanje = csv.parse();
		}
		
		return putanje;
	}

}

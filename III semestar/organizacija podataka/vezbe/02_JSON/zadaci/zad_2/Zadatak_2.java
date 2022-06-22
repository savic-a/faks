package zad_2;

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

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import zad_1.Putanja;

public class Zadatak_2 {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		List<Putanja> putanje = citanjeCSV();
		Map<String, List<Putanja>> mapa = new HashMap<>();
		kreiranjeMape(putanje, mapa);
		rezultati(mapa);
	}

	private static void rezultati(Map<String, List<Putanja>> mapa) throws FileNotFoundException, IOException {
		//1. najveci br polazaka
		String najBrPolaska = "";
		int br = 0;
		for(String putanja : mapa.keySet()) {
			if(mapa.get(putanja).size() > br) {
				br = mapa.get(putanja).size();
				najBrPolaska = putanja;
			}
		}System.out.println(najBrPolaska);
		
		//2. najduze vreme
		int vreme = 0;
		int najVreme = 0;
		String relacija = "";
		for(String putanja : mapa.keySet()) {
			vreme = 0;
			for(Putanja p : mapa.get(putanja)) {
				int polazak = Integer.parseInt(p.getVremePolazak().split(":")[0]);
				int dolazak = Integer.parseInt(p.getVremeDolaska().split(":")[0]);
				vreme += dolazak - polazak;
			}
			if(vreme > najVreme) {
				najVreme = vreme;
				relacija = putanja;
			}
		}System.out.println(relacija);
		
		//3.najisplativije
		double naj = 0;
		double zarada = 0;
		String najRelacija = "";
		for(String putanja : mapa.keySet()) {
			zarada = 0;
			for(Putanja p : mapa.get(putanja)) {
				zarada += Double.parseDouble(p.getCena()) * Integer.parseInt(p.getBrKarata()); 
			}
			if(zarada > naj) {
				naj = zarada;
				najRelacija = putanja;
			}
		}System.out.println(najRelacija);
		
		//pisanje u YAML
		Yaml yaml = new Yaml();
		try(Writer writer = new FileWriter("02_JSON/zadaci/zad_2.yaml")){
			yaml.dump(mapa.get(najBrPolaska), writer);
			yaml.dump(mapa.get(relacija), writer);
			yaml.dump(mapa.get(najRelacija), writer);
		}
		
		
	}

	private static void kreiranjeMape(List<Putanja> putanje, Map<String, List<Putanja>> mapa) {
		
		for(Putanja p : putanje) {
			if(!mapa.containsKey(p.getPolazak()+"-"+p.getDolazak())) {
				List<Putanja> lista = new ArrayList<>();
				lista.add(p);
				mapa.put(p.getPolazak()+"-"+p.getDolazak(), lista);
			}else {
				mapa.get(p.getPolazak()+"-"+p.getDolazak()).add(p);
			}
		}
		
	}

	private static List<Putanja> citanjeCSV() throws FileNotFoundException, IOException {
		List<Putanja> putanje = new ArrayList<Putanja>();
		
		try(Reader reader = new FileReader("02_JSON/zadaci/putanje.csv")){
			CsvToBean<Putanja> csv = new CsvToBeanBuilder<Putanja>(reader).withSeparator(',').withType(Putanja.class).build();
			putanje = csv.parse();
		}return putanje;
	}

}

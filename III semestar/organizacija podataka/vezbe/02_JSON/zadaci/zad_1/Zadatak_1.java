package zad_1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.opencsv.CSVWriter;

public class Zadatak_1 {

	public static void main(String[] args) throws IOException {
		List<Putanja> polasci = new ArrayList<Putanja>();
		Map<String, List<Putanja>> mapa = new HashMap<>();
		kreiranjeFajla(polasci, mapa);
		pisanjeCSV(polasci);
		kreiranjeJSON(mapa);
		System.out.println(mapa);

	}

	private static void kreiranjeJSON(Map<String, List<Putanja>> mapa) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		for(String p : mapa.keySet()) {
			mapper.writeValue(new File("02_JSON/zadaci/relacije/"+p+".json"), mapa.get(p));
		}
		
	}

	private static void pisanjeCSV(List<Putanja> polasci) throws IOException {
		try(CSVWriter writer = new CSVWriter(new FileWriter("02_JSON/zadaci/putanje.csv"))){
			for(Putanja p : polasci) {
				String[] linija = new String[9];
				linija[0] = p.getId();
				linija[1] = p.getPolazak();
				linija[2] = p.getDolazak();
				linija[3] = p.getDatum();
				linija[4] = p.getVremePolazak();
				linija[5] = p.getVremeDolaska();
				linija[6] = p.getPeron();
				linija[7] = p.getCena();
				linija[8] = p.getBrKarata();
				
				writer.writeNext(linija);
			}
		}
		
	}

	private static void kreiranjeFajla(List<Putanja> polasci, Map<String, List<Putanja>> mapa) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
		DateTimeFormatter tf = DateTimeFormatter.ofPattern("HH:mm");
		Random random = new Random();
		int id = 1;
		String[] gradovi = {"Beograd", "Novi Sad", "Nis", "Zrenjanin", "Subotica"};
		LocalDate datum = LocalDate.now();
		LocalTime pocetak = LocalTime.of(6, 0);
		
		List<Putanja> lista = new ArrayList<Putanja>();
		
		for(String polazak : gradovi) {
			for(String dolazak : gradovi) {
				lista = new ArrayList<Putanja>();
				if(!polazak.equals(dolazak)) {
					int brPolaska = random.nextInt(3) + 1;
					for(int i=0; i<brPolaska; i++) {
						int up = random.nextInt(3);
						LocalDate generisanD = datum.plusDays(i); 
						
						for(int j = 0; j<up; j++) {
							LocalTime vremeP = pocetak.plusHours(j);
							int peron = random.nextInt(20);
							double cena = random.nextDouble() * 1000;
							int brKarata = random.nextInt(50);
							
							Putanja p = new Putanja();
							p.setId(Integer.toString(id));
							p.setPolazak(polazak);
							p.setDolazak(dolazak);
							p.setDatum(dtf.format(generisanD));
							p.setVremePolazak(tf.format(vremeP));
							p.setVremeDolaska(tf.format(vremeP.plusHours(random.nextInt(5) + 1)));
							p.setPeron(Integer.toString(peron));
							p.setCena(Double.toString(cena));
							p.setBrKarata(Integer.toString(brKarata));
							
							polasci.add(p);
							lista.add(p);
							mapa.put(polazak+"-"+dolazak, lista);
							id++;
						}
					}
				}
			}
		}
		
	}

}

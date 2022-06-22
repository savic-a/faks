package zad_5;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.yaml.snakeyaml.Yaml;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.opencsv.CSVWriter;

public class Zadatak_5 {

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		List<Auto> automobili = kreiranjeJson();
		kreiranjeYaml(automobili);
		kreiranjeCsv(automobili);
		podC(automobili);
	}

	private static void podC(List<Auto> automobili) throws JsonGenerationException, JsonMappingException, IOException {
		List<Double> kilometri = new ArrayList<>();
		List<Auto> ispisani = new ArrayList<>();
		
		for(Auto a : automobili){
			if(!a.getKm().equals("-1"))
			kilometri.add(Double.valueOf(a.getKm()));
		}
		Collections.sort(kilometri);
		
		for(Double km : kilometri) {
			for(Auto a : automobili) {
				if(!ispisani.contains(a) && Double.parseDouble(a.getKm()) == km) {
					ispisani.add(a);
				}
			}
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.writeValue(new File("02_JSON/zadaci/zad_5.json"), ispisani);
	}

	private static void kreiranjeCsv(List<Auto> automobili) {
		List<Osoba> osobe = new ArrayList<Osoba>();
		
		try(CSVWriter writer = new CSVWriter(new FileWriter("02_JSON/zadaci/zad_5.csv"))){
			for(Auto a : automobili) {
				if(!osobe.contains(a.getOsoba())){
					osobe.add(a.getOsoba());
				}
			}
			for(Osoba o : osobe) {
				double potrosnaSuma = 0;
				int ukupnoDana = 0;
				int brPuta = 0;
				for(Auto a : automobili) {
					if(!a.getVracanjeDatum().equals("") && a.getOsoba() == o) {
						brPuta++;
						ukupnoDana += Integer.parseInt(a.getVracanjeDatum().split("\\.")[0]) - Integer.parseInt(a.getDatum().split("\\.")[0]); 
						potrosnaSuma += Integer.parseInt(a.getBrDana()) * Double.parseDouble(a.getCena());
					}
				}
				String[] linija = new String[5];
				linija[0] = o.getIme();
				linija[1] = o.getPrezime();
				linija[2] = o.getJmbg();
				linija[3] = Double.toString(potrosnaSuma);
				linija[4] = Double.toString(ukupnoDana/brPuta);
				
				writer.writeNext(linija);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static void kreiranjeYaml(List<Auto> automobili) throws IOException {
		List<Auto> izabrani = new ArrayList<Auto>();
		
		try(Scanner sc = new Scanner(System.in)){
			System.out.println("Mesec: ");
			String mesec = sc.nextLine();
			System.out.print("Godina: ");
			String godina = sc.nextLine();
			
			for(Auto a : automobili) {
				String[] datum = a.getDatum().split("\\.");
				if(mesec.equals(datum[1]) && godina.equals(datum[2]))
					izabrani.add(a);
			}
		}
		Yaml yaml = new Yaml();
		try(Writer writer = new FileWriter("02_JSON/zadaci/iznajmljeni.yaml")){
			yaml.dump(izabrani, writer);
		}
		
	}

//	@SuppressWarnings("unchecked")
//	private static List<Auto> citanjeJson() throws JsonParseException, JsonMappingException, IOException {
//		List<Auto> automobili = new ArrayList<Auto>();
//		ObjectMapper mapper = new ObjectMapper();
//		mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
//		automobili = mapper.readValue(new File("02_JSON/zadaci/iznajmljeni.json"), List.class);
//		return automobili;
//	}

	private static List<Auto> kreiranjeJson() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
		
		List<Auto> automobili = new ArrayList<Auto>();
		Random random = new Random();
		int id = 1;
		double km = -1;
		String[] mesta = {"Zrenjanin", "Novi Sad", "Beograd", "Subotica", "Nis", "Kragujevac"};
		LocalDate danas = LocalDate.now();
		String vracanje = "";
		
		
		Osoba pera = new Osoba("Pera", "Peric", "1234567891234");
		Osoba zika = new Osoba("Zika", "Zikic", "0103975123556");
		Osoba sima = new Osoba("Sima", "Simic", "0505221546221");
		Osoba[] osobe = {pera, zika, sima};

		for(int i=0; i<5; i++) {
			for(String grad : mesta) {
				for(Osoba o : osobe) {
					LocalDate datumDate = danas.plusDays(random.nextInt(8));
					String datum = dtf.format(datumDate);
					if(random.nextBoolean()) {
						LocalDate vracanjeDate = datumDate.plusDays(5);
						vracanje = dtf.format(vracanjeDate);
						km = random.nextDouble() * 1000;
					}
					double cena = random.nextDouble() * 1000;
					int brDana = random.nextInt(21);
					Auto auto = new Auto(Integer.toString(id), grad, datum, vracanje, Double.toString(km), Double.toString(cena), Integer.toString(brDana), o);
					automobili.add(auto);
					id++;
				}
			}
		}
		try {
			mapper.writeValue(new File("02_JSON/zadaci/iznajmljeni.json"), automobili);
		} catch (JsonGenerationException e) {
		} catch (JsonMappingException e) {
		} catch (IOException e) {
		}
		
		return automobili;
	}
}


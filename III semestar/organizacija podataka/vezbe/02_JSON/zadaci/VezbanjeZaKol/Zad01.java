package VezbanjeZaKol;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

public class Zad01 {
	public static void main(String[] args) {
		
		// Prvi deo: podaci iz memorije u CSV	
		{		
			try (FileWriter writer = new FileWriter("02_JSON/zadaci/kolokvijum/01_polasci.csv");) {
				StatefulBeanToCsv<Polazak> csv =
						new StatefulBeanToCsvBuilder<Polazak>(writer)
						.withApplyQuotesToAll(false)
//						.withSeparator(';')
						.build();
				
				ArrayList<Polazak> li = new ArrayList<Polazak>();
				li.add(new Polazak("111", "Mesto1", "Mesto2", "4.11.2021.", "17:00", "18:00", "1", 400, 10));
				li.add(new Polazak("112", "Mesto1", "Mesto2", "4.11.2021.", "18:00", "19:00", "1", 400, 20));
				li.add(new Polazak("222", "Mesto42", "Mesto27", "5.11.2021.", "13:00", "14:00", "2", 400, 10));
				li.add(new Polazak("333", "Mesto41", "Mesto52", "5.11.2021.", "14:00", "15:00", "1", 400, 30));
				li.add(new Polazak("444", "Mesto41", "Mesto22", "5.11.2021.", "15:00", "16:00", "1", 500,  5));
				li.add(new Polazak("555", "Mesto41", "Mesto21", "6.11.2021.", "16:00", "17:00", "2", 500,  8));
				li.add(new Polazak("666", "Mesto41", "Mesto22", "7.11.2021.", "17:00", "18:00", "2", 400,  9));
				li.add(new Polazak("777", "Mesto41", "Mesto32", "8.11.2021.", "18:00", "19:00", "1", 500,  9));
				li.add(new Polazak("888", "Mesto41", "Mesto24", "8.11.2021.", "19:00", "20:00", "3", 400, 10));
				li.add(new Polazak("999", "Mesto41", "Mesto52", "9.11.2021.", "20:00", "21:00", "1", 500,  8));
				
				csv.write(li);
			} catch (Exception e) {
				e.printStackTrace();
			}		
		}
		
		// Drugi deo: podaci iz CSV u JSON
		
		{
			try (Reader reader = new FileReader("02_JSON/zadaci/kolokvijum/01_polasci.csv");) {
				CsvToBean<Polazak> csv =
						new CsvToBeanBuilder<Polazak>(reader)
						.withSeparator(',')
						.withType(Polazak.class)
						.build();
				List<Polazak> li = new ArrayList<Polazak>();
				li = csv.parse();
					
				ObjectMapper mapper = new ObjectMapper();
				mapper.enable(SerializationFeature.INDENT_OUTPUT);
				mapper.writeValue(new File("02_JSON/zadaci/kolokvijum/01_polasci.json"), li);		
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

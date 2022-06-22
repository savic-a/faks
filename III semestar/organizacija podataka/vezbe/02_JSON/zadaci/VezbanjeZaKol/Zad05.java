package VezbanjeZaKol;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.representer.Representer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

/* ovo je za read, ne za write
class Zad05Strategy extends ColumnPositionMappingStrategy<OsobaPodaci> {
	public Zad05Strategy() {
		this.setType(OsobaPodaci.class);
	}
	
	@Override
	public OsobaPodaci populateNewBean(String [] line) {
		OsobaPodaci o = new OsobaPodaci(
			new Osoba(
				line[0],
				line[1],
				line[2]
			),
			Double.parseDouble(line[3]),
			Double.parseDouble(line[4])
		);
		return o;	
	}
}
*/




public class Zad05 {
	public static void main(String[] args) {
		List<Iznajmljivanje> li = new ArrayList<Iznajmljivanje>();
		
		generisi_elemente_za_listu(li);
		System.out.println(li);
		
		try (FileWriter writer = new FileWriter("02_JSON/resources/zad05_svipodaci.json");) {
			ObjectMapper om = new ObjectMapper();
			om.enable(SerializationFeature.INDENT_OUTPUT);
			om.writeValue(writer, li);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		podA(li);
		podB(li);
		podC(li);
	}
	
	private static void podA(List<Iznajmljivanje> li) {
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Unesite mesec:");
		String month = scan.next();
		System.out.println("Unesite godinu:");
		String year = scan.next();
		
		List<Iznajmljivanje> select = new ArrayList<Iznajmljivanje>();
		for (Iznajmljivanje i : li) {
			String input_full_date = month + "." + year + ".";
			String i_datum = i.getDatum_iznajmljivanja();
			
			if (i_datum.endsWith(input_full_date))
				select.add(i);
		}
		
		try (FileWriter writer = new FileWriter("02_JSON/resources/zad05_a.yaml");) {
			Yaml yaml = new Yaml(new Constructor(Iznajmljivanje.class));
			DumperOptions options = new DumperOptions();
			options.setWidth(50);
			options.setIndent(4);
			options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
			
			yaml = new Yaml(new Constructor(Iznajmljivanje.class), new Representer(), options);
			
			yaml.dump(select, writer);
		} catch (Exception e) { 
			e.printStackTrace();
		}
		
		scan.close();
	}
	
	private static void podB(List<Iznajmljivanje> li) {
		Map<Osoba, Double> potroseno = new HashMap<Osoba, Double>();
		Map<Osoba, Double> brojdana = new HashMap<Osoba, Double>();
		Map<Osoba, Integer> brojiznajmljivanja = new HashMap<Osoba, Integer>();
		
		for (Iznajmljivanje i : li) {
			if (i.getBroj_dana() == -1) // Ako jos uvek traje iznajmljivanje, ne racunamo
				continue;
			
			Osoba o = i.getOsoba();
			boolean present = potroseno.get(o) != null;
			if (present) {
				potroseno.put(o, potroseno.get(o) + i.getCena() * i.getBroj_dana());
				brojdana.put(o, brojdana.get(o) + i.getBroj_dana());
				brojiznajmljivanja.put(o, brojiznajmljivanja.get(o) + 1);
			} else {
				potroseno.put(o, i.getCena() * i.getBroj_dana());
				brojdana.put(o, (double)i.getBroj_dana());
				brojiznajmljivanja.put(o, 1);
			}
		}
		
		
		List<OsobaPodaci> podaci = new ArrayList<OsobaPodaci>();
		for (Entry<Osoba, Double> e : brojdana.entrySet()) {
			podaci.add(new OsobaPodaci(
					e.getKey(), 
					potroseno.get(e.getKey()),
					e.getValue() / brojiznajmljivanja.get(e.getKey())
			));
		}
		
		
		// Ovo ne radi kako treba jer OsobaPodaci u fajl upisuje polje `osoba`
		// preko toString() koji ne postuje pravila CSV-a...
		try (FileWriter writer = new FileWriter("02_JSON/resources/zad05_b.csv")) {
			StatefulBeanToCsv<OsobaPodaci> csv = new StatefulBeanToCsvBuilder<OsobaPodaci>(writer)
					.withApplyQuotesToAll(false)
//					.withMappingStrategy(new Zad05Strategy())  ovo je za read, ne za write
					.build();
			csv.write(podaci);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void podC(List<Iznajmljivanje> li) {
		Map<Auto, Double> istrosenost = new HashMap<Auto, Double>();
		
		for (Iznajmljivanje i : li) {
			if (i.getBroj_dana() == -1) //Ako jos uvek traje iznajmljivanje, ne racunamo
				continue;
			
			Auto a = i.getAuto();
			boolean present = istrosenost.get(a) != null;
			if (present) {
				istrosenost.put(a, istrosenost.get(a) + i.getKilometri());
			} else {
				istrosenost.put(a,  + i.getKilometri());
			}
		}
		
		List<Entry<Auto, Double>> list = new ArrayList<Entry<Auto, Double>>();
		for (Entry<Auto, Double> e : istrosenost.entrySet())
			list.add(e);
		
		list.sort(new Comparator<Entry<Auto, Double>>() {
			@Override
			public int compare(Entry<Auto, Double> o1, Entry<Auto, Double> o2) {
				return (o1.getValue().compareTo(o2.getValue()));
			}
		});
		
		try (FileWriter writer = new FileWriter("02_JSON/resources/zad05_c.json");) {
			ObjectMapper om = new ObjectMapper();
			om.enable(SerializationFeature.INDENT_OUTPUT);
			om.writeValue(writer, list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private static void generisi_elemente_za_listu(List<Iznajmljivanje> li) {
		Osoba marko = new Osoba("Marko", "Markovic", "111");
		Osoba janko = new Osoba("Janko", "Jankovic", "222");
		Osoba branko = new Osoba("Branko", "Brankovic", "333");
		Osoba stanko = new Osoba("Stanko", "Stankovic", "444");
		
		Auto auto1 = new Auto("BMW", "2019");
		Auto auto2 = new Auto("Jugo", "2000");
		Auto auto3 = new Auto("Mercedes", "2010");
		
		li.add(new Iznajmljivanje("00", auto1, "Novi Sad", "10.10.2021.", "15.10.2021.", 
				30, 1000.0, 5, marko));
		li.add(new Iznajmljivanje("01", auto1, "Novi Sad", "20.10.2021.", "24.10.2021.", 
				20, 1500.0, 4, janko));
		li.add(new Iznajmljivanje("02", auto2, "Novi Sad", "19.10.2021.", "23.10.2021.", 
				10, 1800.0, 4, branko));
		li.add(new Iznajmljivanje("03", auto3,  "Novi Sad", "20.10.2021.", "21.10.2021.", 
				5, 3000.0, 1, stanko));
		li.add(new Iznajmljivanje("03", auto3,  "Novi Sad", "10.11.2021.", "", 
				-1, 6000.0, -1, marko));
		li.add(new Iznajmljivanje("03", auto1, "Novi Sad", "1.11.2021.", "10.11.2021.", 
				30, 1000.0, 10, janko));
	}
}

package VezbanjeZaKol;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class Zad02 {
	public static void main(String[] args) {
		// Ucitaj CSV
		
		try (Reader r = new FileReader("resources/01_polasci.csv");) {
			CsvToBean<Polazak> csv = new CsvToBeanBuilder<Polazak>(r)
					.withSeparator(',')
					.withType(Polazak.class)
					.build();
			
			List<Polazak> li = csv.parse();
			
		Map<String, Integer> m1 = new HashMap<String, Integer>();
		Map<String, Integer> m2 = new HashMap<String, Integer>();
		Map<String, Double> m3 = new HashMap<String, Double>();
		
		for (Polazak p : li) {
			String s;
			if (p.getPolazak().compareTo(p.getDolazak()) < 0)
				s = p.getPolazak() + " - " + p.getDolazak();
			else
				s = p.getDolazak() + " - " + p.getPolazak();
			
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			
			Integer i = m1.get(s);
			Integer j = m2.get(s);
			Double k = m3.get(s);
			
			// Ako je ijedan null, svi ce biti null.
			if (i == null) {
				m1.put(s, 1);	
				int diff = (int) (sdf.parse(p.getVreme_dolaska()).getTime() - sdf.parse(p.getVreme_polaska()).getTime());
				m2.put(s, diff);
				double pay = p.getBroj_prodanih() * p.getCena();
				m3.put(s, pay);
			} else {
				m1.put(s, i + 1);	
				int diff = (int) (sdf.parse(p.getVreme_dolaska()).getTime() - sdf.parse(p.getVreme_polaska()).getTime());
				m2.put(s, j + diff);
				double pay = p.getBroj_prodanih() * p.getCena();
				m3.put(s, k + pay);
			}
		}
		
		// Napravi liste iz entrysetova
		
		List<Entry<String, Integer>> list1 = new ArrayList<Entry<String, Integer>>();
		List<Entry<String, Integer>> list2 = new ArrayList<Entry<String, Integer>>();
		List<Entry<String, Double >> list3 = new ArrayList<Entry<String, Double >>();
		
		for (Entry<String, Integer> entry : m1.entrySet()) list1.add(entry);
		for (Entry<String, Integer> entry : m2.entrySet()) list2.add(entry);
		for (Entry<String, Double > entry : m3.entrySet()) list3.add(entry);
		
		// Sortiraj ih (minus ide jer treba obrnut poredak)
		
		list1.sort(new Comparator<Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				return -o1.getValue().compareTo(o2.getValue());
			}
		});
		list2.sort(new Comparator<Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				return -o1.getValue().compareTo(o2.getValue());
			}
		});
		list3.sort(new Comparator<Entry<String, Double>>() {
			@Override
			public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
				return -o1.getValue().compareTo(o2.getValue());
			}
		});
		
		// Ispisi najbolje
		
		//List<PolazakResult> lll = new ArrayList<PoazakResult>();
		
		
		Yaml y = new Yaml(new Constructor(PolazakResult.class));
		
		try (FileWriter wr = new FileWriter("resources/02_ispis.yaml");) {
			y.dump(new PolazakResult<Integer>(list1.get(0).getKey(), list1.get(0).getValue()), wr);
			y.dump(new PolazakResult<Integer>(list2.get(0).getKey(), list2.get(0).getValue()), wr);
			y.dump(new PolazakResult<Double>(list3.get(0).getKey(), list3.get(0).getValue()), wr);
		}
		
		
		System.out.println(list1.get(0) + " " +  list2.get(0) + " " + list3.get(0));		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

package modeli;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import citanjeIzFajla.Menadzer;
import entiteti.Kurs;
import entiteti.Ucenici;
import entiteti.Zahtev;

public class UceniciZahtevModel extends AbstractTableModel{
	
	private static final long serialVersionUID = 1L;
	
	private List<Kurs> kursevi = new ArrayList<Kurs>();
	private Menadzer menadzer;
	private Ucenici ucenik;
	
	private String[] kolone = {"ID", "Naziv", "Jezik", "Cena", "Predavac", "Testovi", "Broj ucenika"};
	
	public UceniciZahtevModel(Menadzer menadzer, Ucenici ucenik) {
		this.menadzer = menadzer;
		this.ucenik = ucenik;
		this.kursevi = dostupniKursevi();
	}
	
	private List<Kurs> dostupniKursevi() {
		kursevi.clear();
		for(Kurs k : menadzer.getKursMen().getKursevi()) {
			if(!ucenik.getKursevi().contains(k) && !k.getPredavac().getUsername().equals(""))
				kursevi.add(k);
		for(Zahtev z : menadzer.getZahteviKursMen().getZahteviKurs()) {
			for(Kurs kurs : z.getKursevi()) {
				System.out.println(z.getUcenik());
				if(kursevi.contains(kurs) && ucenik == z.getUcenik()) {
					kursevi.remove(kurs);
				}
			}
		}
		}return kursevi;
	}

	@Override
	public int getRowCount() {
		return kursevi.size();
	}

	@Override
	public int getColumnCount() {
		return kolone.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Kurs k = kursevi.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return k.getId();
		case 1:
			return k.getNaziv();
		case 2:
			return k.getJezik().getNaziv();
		case 3:
			return Double.toString(k.getCenaKursa());
		case 4:
			return k.getPredavac().getUsername();
		case 5:
			String testovi = "";
			if (!k.getTestovi().contains(null)) {
				for(int i=0;i<k.getTestovi().size();i++) {
					if (i == 0)
						testovi = k.getTestovi().get(i).getNaziv();
					else
						testovi += ", " + k.getTestovi().get(i).getNaziv(); 
				}
			}
			return testovi;
		case 6:
			return Integer.toString(k.getBrUcenika());
		default:
			return null;
		}
	}
	
	@Override
	public String getColumnName(int column) {
		return this.kolone[column];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return this.getValueAt(0, columnIndex).getClass();
	}
}

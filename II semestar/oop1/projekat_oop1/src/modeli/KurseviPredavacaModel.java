package modeli;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import citanjeIzFajla.KursMen;
import entiteti.Kurs;
import entiteti.Predavac;

public class KurseviPredavacaModel extends AbstractTableModel{
	
	private static final long serialVersionUID = -606496872162215998L;
	
	private KursMen kursMen;
	private List<Kurs> kursevi;
	private Predavac predavac;
	private String[] kolone = {"ID", "Naziv", "Jezik", "Cena", "Broj učenika"};
	
	public KurseviPredavacaModel(KursMen mng, Predavac predavac) {
		this.kursMen = mng;
		this.predavac = predavac;
		this.kursevi = new ArrayList<Kurs>();
	}
	
	private List<Kurs> listaKurseva(){
		kursevi.clear();
		for(Kurs k : kursMen.getKursevi()) {
			if(k.getPredavac() == predavac) {
				kursevi.add(k);
			}
		}
		return kursevi;
	}

	@Override
	public int getRowCount() {
		return listaKurseva().size();
	}

	@Override
	public int getColumnCount() {
		return kolone.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Kurs k = listaKurseva().get(rowIndex);
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

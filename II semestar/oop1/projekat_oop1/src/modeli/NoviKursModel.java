package modeli;

import javax.swing.table.AbstractTableModel;

import citanjeIzFajla.KursNoviMen;
import entiteti.Kurs;

public class NoviKursModel extends AbstractTableModel{
	
	private static final long serialVersionUID = -3869006602139932737L;
	
	private KursNoviMen kursNoviMen;
	private String[] kolone = {"ID", "Naziv", "Jezik", "Cena", "Predavac", "Testovi", "Broj ucenika"};
	
	public NoviKursModel(KursNoviMen mng) {
		this.kursNoviMen = mng;
	}

	@Override
	public int getRowCount() {
		return kursNoviMen.getKursevi().size();
	}

	@Override
	public int getColumnCount() {
		return kolone.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Kurs k = kursNoviMen.getKursevi().get(rowIndex);
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

package modeli;

import javax.swing.table.AbstractTableModel;

import citanjeIzFajla.SekMenadzer;
import entiteti.Sekretar;

public class SekretarModel extends AbstractTableModel{
	
	private static final long serialVersionUID = 2528060867399989365L;

	private SekMenadzer sekMen;
	private String[] kolone = {"Ime", "Prezime", "Pol", "Datum", "Tel", "Adresa", "Korisnicko ime", "Lozinka", "Plata"};
	
	public SekretarModel(SekMenadzer sekMen) {
		this.sekMen = sekMen;
	}

	@Override 
	public int getRowCount() {
		return sekMen.getSekretari().size();
	}

	@Override
	public int getColumnCount() {
		return kolone.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Sekretar s = sekMen.getSekretari().get(rowIndex);
		switch (columnIndex) {
		case 0:
			return s.getIme();
		case 1:
			return s.getPrezime();
		case 2:
			return s.getPol();
		case 3:
			return s.getDatum();
		case 4:
			return s.getTel();
		case 5:
			return s.getAdresa();
		case 6:
			return s.getUsername();
		case 7:
			return s.getLozinka();
		case 8:
			return s.getPlata();
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

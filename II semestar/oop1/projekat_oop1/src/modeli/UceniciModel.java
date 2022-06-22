package modeli;

import javax.swing.table.AbstractTableModel;

import citanjeIzFajla.UcenikMen;
import entiteti.Ucenici;

public class UceniciModel extends AbstractTableModel{
	
	private static final long serialVersionUID = -5123455553488893008L;
	
	private UcenikMen ucenikMen;
	private String[] kolone = {"Ime", "Prezime", "Pol", "Datum", "Tel", "Adresa", "Korisnicko ime", "Lozinka"};

	public UceniciModel(UcenikMen mng) {
		this.ucenikMen = mng;
	}

	@Override
	public int getRowCount() {
		return ucenikMen.getUcenici().size();
	}

	@Override
	public int getColumnCount() {
		return kolone.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Ucenici u = ucenikMen.getUcenici().get(rowIndex);
		switch (columnIndex) {
		case 0:
			return u.getIme();
		case 1:
			return u.getPrezime();
		case 2:
			return u.getPol();
		case 3:
			return u.getDatum();
		case 4:
			return u.getTel();
		case 5:
			return u.getAdresa();
		case 6:
			return u.getUsername();
		case 7:
			return u.getLozinka();
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

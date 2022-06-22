package modeli;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import entiteti.Test;

public class PrijavaTestovaModel extends AbstractTableModel{
	
	private static final long serialVersionUID = 1L;
	
	private List<Test> testovi;
	private String[] kolone = {"ID", "Naziv", "Kurs", "Predavač", "Broj učionice", "Datum"};
	
	public PrijavaTestovaModel(List<Test> testovi) {
		this.testovi = testovi;
	}

	@Override
	public int getRowCount() {
		return testovi.size();
	}

	@Override
	public int getColumnCount() {
		return kolone.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Test t = testovi.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return t.getId();
		case 1:
			return t.getNaziv();
		case 2:
			return t.getKurs(); 
		case 3:
			return t.getPredavac().getUsername();
		case 4:
			return t.getBrUcionice();
		case 5:
			return t.getDatum();
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

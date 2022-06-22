package modeli;

import javax.swing.table.AbstractTableModel;

import entiteti.Test;
import entiteti.Ucenici;

public class TestoviUcenikaModel extends AbstractTableModel{
	
	private static final long serialVersionUID = 1L;
	
	private Ucenici ucenik;
	private String[] kolone = {"ID", "Naziv", "Kurs", "Predavač", "Broj učionice", "Datum"};
	
	public TestoviUcenikaModel(Ucenici ucenik) {
		this.ucenik = ucenik;
	}

	@Override
	public int getRowCount() {
		return ucenik.getTestovi().size();
	}

	@Override
	public int getColumnCount() {
		return kolone.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Test t = ucenik.getTestovi().get(rowIndex);
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

package modeli;

import javax.swing.table.AbstractTableModel;

import citanjeIzFajla.JeziciMenadzer;
import entiteti.Jezik;

public class JezikModel extends AbstractTableModel{
	
	private static final long serialVersionUID = -1063195563273678147L;
	
	private JeziciMenadzer jeziciMen;
	private String[] kolone = {"ID", "Naziv"};

	public JezikModel(JeziciMenadzer mng) {
		this.jeziciMen = mng;
	}

	@Override
	public int getRowCount() {
		return jeziciMen.getJezici().size();
	}

	@Override
	public int getColumnCount() {
		return kolone.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Jezik j = jeziciMen.getJezici().get(rowIndex);
		switch (columnIndex) {
		case 0:
			return j.getId();
		case 1:
			return j.getNaziv();
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

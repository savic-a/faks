package modeli;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import citanjeIzFajla.Menadzer;
import entiteti.Predavac;
import entiteti.Test;

public class TestModel extends AbstractTableModel{
	
	private static final long serialVersionUID = -6092801953771542446L;
	
	private Menadzer menadzer;
	private Predavac p;
	private List<Test> testovi;
	
	private String[] kolone = {"ID", "Naziv", "Kurs", "Predavac", "Broj ucionice", "Datum"};

	public TestModel(Menadzer menadzer, Predavac p) {
		this.menadzer = menadzer;
		this.p = p;
		this.testovi = new ArrayList<Test>();
	}
	
	private List<Test> listaTestova(){
		testovi.clear();
		for(Test t : menadzer.getTestMen().getTestovi()) {
			if(t.getPredavac() == p) {
				testovi.add(t);
			}
		}
		return testovi;
	}

	@Override
	public int getRowCount() {
		return listaTestova().size();
	}

	@Override
	public int getColumnCount() {
		return kolone.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Test t = listaTestova().get(rowIndex);
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

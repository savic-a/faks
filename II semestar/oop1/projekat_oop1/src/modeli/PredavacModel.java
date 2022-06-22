package modeli;

import javax.swing.table.AbstractTableModel;

import citanjeIzFajla.PredMenadzer;
import entiteti.Predavac;

public class PredavacModel extends AbstractTableModel{
	
	private static final long serialVersionUID = 173122351138550735L;
	
	private PredMenadzer predMen;
	private String[] kolone = {"Ime", "Prezime", "Pol", "Datum", "Tel", "Adresa", "Korisnicko ime", "Lozinka", "Plata", "Jezik"};

	public PredavacModel(PredMenadzer mng) {
		this.predMen = mng;
	}

	@Override
	public int getRowCount() {
		return predMen.getPredavaci().size();
	}

	@Override
	public int getColumnCount() {
		return kolone.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Predavac p = predMen.getPredavaci().get(rowIndex);
		switch (columnIndex) {
		case 0:
			return p.getIme();
		case 1:
			return p.getPrezime();
		case 2:
			return p.getPol();
		case 3:
			return p.getDatum();
		case 4:
			return p.getTel();
		case 5:
			return p.getAdresa();
		case 6:
			return p.getUsername();
		case 7:
			return p.getLozinka();
		case 8:
			return Double.toString(p.getPlata());
		case 9:
			String jezici = "";
			if (!p.getJezici().contains(null)) {
				for(int i=0;i<p.getJezici().size();i++) {
					if (i == 0)
						jezici = p.getJezici().get(i).getNaziv();
					else
						jezici += ", " + p.getJezici().get(i).getNaziv(); 
				}
			}
			return jezici;
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

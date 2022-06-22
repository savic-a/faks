package modeli;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import citanjeIzFajla.ZahteviKursMen;
import entiteti.Sekretar;
import entiteti.Zahtev;

public class PreuzetiZahteviModel extends AbstractTableModel {
	
	private static final long serialVersionUID = 6238645851147232380L;
	
	private ZahteviKursMen zahteviKursMen;
	private List<Zahtev> zahtevi;
	private Sekretar sekretar;
	
	private String[] kolone = {"ID", "Ucenik", "Sekretar", "Kursevi", "Status"};
	
	public PreuzetiZahteviModel(ZahteviKursMen mng, Sekretar s) {
		this.zahteviKursMen = mng;
		this.zahtevi = new ArrayList<Zahtev>();
		this.sekretar = s;
	}
	
	private List<Zahtev> listaZahteva(){
		zahtevi.clear();
		for(Zahtev z : zahteviKursMen.getZahteviKurs()) {
			if(!z.getStatus().equals("odbijen") && !zahtevi.contains(z) && z.getSekretar() == sekretar) {
				zahtevi.add(z);
			}
		}
		return zahtevi;
	}

	@Override
	public int getRowCount() {
		return listaZahteva().size();
	}

	@Override
	public int getColumnCount() {
		return kolone.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Zahtev z = listaZahteva().get(rowIndex);
		switch (columnIndex) {
		case 0:
			return z.getId();
		case 1:
			return z.getUcenik().getUsername();
		case 2:
			return z.getSekretar().getUsername();
		case 3:
			String kursevi = "";
			if (!z.getKursevi().contains(null)) {
				for(int i=0;i<z.getKursevi().size();i++) {
					if (i == 0)
						kursevi = z.getKursevi().get(i).getNaziv();
					else
						kursevi += ", " + z.getKursevi().get(i).getNaziv(); 
				}
			}
			return kursevi;
		case 4:
			return z.getStatus();
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

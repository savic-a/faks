package modeli;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import citanjeIzFajla.Menadzer;
import entiteti.Sekretar;
import entiteti.Zahtev;

public class ZahteviRegModel extends AbstractTableModel{
	
	private static final long serialVersionUID = 6553002793707709973L;
	
	private JFrame parent;
	private List<Zahtev> zahtevi;
	private Menadzer menadzer;
	private Sekretar s;
	
	private String[] kolone = {"ID", "Ime", "Prezime", "Pol", "Datum", "Tel", "Adresa", "Korisnicko ime", "Lozinka", "Status"};

	public ZahteviRegModel(JFrame parent, Menadzer menadzer, Sekretar s) {
		this.parent = parent;
		this.zahtevi = new ArrayList<Zahtev>();
		this.menadzer = menadzer;
		this.s = s;
	}
	
	private List<Zahtev> listaZahteva(){
		zahtevi.clear();
		for(Zahtev z : menadzer.getZahteviRegMen().getZahteviReg()) {
			if(z.getSekretar() == s && (z.getStatus().equals("u obradi") || z.getStatus().equals("prihvacen")) && !zahtevi.contains(z)) {
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
		try {
			Zahtev z = listaZahteva().get(rowIndex);
			switch (columnIndex) {
			case 0:
				return z.getId();
			case 1:
				return z.getIme();
			case 2:
				return z.getPrezime();
			case 3:
				return z.getPol();
			case 4:
				return z.getDatum();
			case 5:
				return z.getTel();
			case 6:
				return z.getAdresa();
			case 7:
				return z.getUsername();
			case 8:
				return z.getLozinka();
			case 9:
				return z.getStatus();
			default:
				return null;
			}
		}catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ne postoji više nijedan zahtev za registraciju!", "Info", JOptionPane.INFORMATION_MESSAGE);
			parent.dispose();
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

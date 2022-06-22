package modeli;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.table.AbstractTableModel;

import citanjeIzFajla.Menadzer;
import citanjeIzFajla.ZahteviKursMen;
import entiteti.Zahtev;
import gui.sekretar.MainSekretar;

public class KreiranZahtevModel extends AbstractTableModel{
	
	private static final long serialVersionUID = -3005700231102864992L;
	
	private JFrame parent;
	private ZahteviKursMen zahteviKursMen;
	private List<Zahtev> zahtevi;
	private Menadzer menadzer;
	private String username;
	
	private String[] kolone = {"ID", "Ucenik", "Kursevi", "Status"};
	
	public KreiranZahtevModel(JFrame parent, ZahteviKursMen mng, Menadzer menadzer, String username) {
		this.parent = parent;
		this.zahteviKursMen = mng;
		this.menadzer = menadzer;
		this.username = username;
		this.zahtevi = new ArrayList<Zahtev>();
	}
	
	private List<Zahtev> listaZahteva(){
		zahtevi.clear();
		for(Zahtev z : zahteviKursMen.getZahteviKurs()) {
			if(z.getStatus().equals("kreiran") && !zahtevi.contains(z)) {
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
				return z.getUcenik().getUsername();
			case 2:
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
			case 3:
				return z.getStatus();
			default:
				return null;
			}
			}catch (Exception e) {
				MainSekretar ms = new MainSekretar(menadzer, username);
				ms.setVisible(true);
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
		try {
			return this.getValueAt(0, columnIndex).getClass();
		}catch (Exception e){
			return null;
		}
	}

}

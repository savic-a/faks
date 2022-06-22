package gui.administrator;

import java.awt.BorderLayout;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import citanjeIzFajla.Menadzer;
import entiteti.Jezik;
import entiteti.Kurs;
import entiteti.Zahtev;

public class Izvestaj2 extends JDialog{

	private static final long serialVersionUID = 6867020169125841190L;
	
	private LocalDate pocetak;
	private LocalDate kraj;
	private Menadzer menadzer;
	private List<Jezik> listaJez;
	
	private List<Zahtev> zahtevi;
	
	private DefaultTableModel tModel1;
	private JTable tabela1;
	
	public Izvestaj2(LocalDate pocetak, LocalDate kraj, Menadzer menadzer, List<Jezik> listaJez) {
		super();
		this.pocetak = pocetak;
		this.kraj = kraj;
		this.menadzer = menadzer;
		this.listaJez = listaJez;
		this.zahtevi = new ArrayList<Zahtev>();
		
		this.setTitle("Izveštaj 2");
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setIconImage(new ImageIcon("./src/slike/icon.png").getImage());
		initMenu();
		pack();
	}


	private void initMenu() {
		if(listaJez.isEmpty()) {
//			int n = 0;
//			for(Jezik j : menadzer.getJezMen().getJezici()) {
//				for(Zahtev z : menadzer.getZahteviKursMen().getZahteviKurs()) {
//					for(Kurs k : z.getKursevi()) {
//						if(j == k.getJezik() && z.getStatus() != "kreiran" && z.getDatumZahteva().isAfter(pocetak) && z.getDatumZahteva().isBefore(kraj)) {
//							n++;
//							vrednosti.add(n);
//							vrednostiSort.add(n);
//						}
//					}
//				}n=0;
//			}
//			System.out.println(vrednosti);
//			Collections.sort(vrednostiSort);
//		    Collections.reverse(vrednostiSort);
//		    
//			String[] zaglavlje1 = new String[] {"Jezik", "Popularnost"};
//			Object[][] sadrzaj1 = new Object[menadzer.getJezMen().getJezici().size()][zaglavlje1.length];
//			
//			for(int i=0; i<vrednostiSort.size(); i++) {
//				int br = vrednostiSort.get(i);
//				int indeks = vrednosti.indexOf(br);
//				sadrzaj1[i][0] = menadzer.getJezMen().getJezici().get(indeks);
//				sadrzaj1[i][1] = vrednosti.get(indeks);
//			}
			
			for(Zahtev z : menadzer.getZahteviKursMen().getZahteviKurs()) {
				if(z.getDatumZahteva().isAfter(pocetak) && z.getDatumZahteva().isBefore(kraj))
					zahtevi.add(z);
			}
			
			String[] zaglavlje1 = new String[] {"Sekretar", "Kursevi", "Status"};
			Object[][] sadrzaj1 = new Object[zahtevi.size()][zaglavlje1.length];
			
			for(int i=0; i<zahtevi.size(); i++) {
				Zahtev zahtev = zahtevi.get(i);
				sadrzaj1[i][0] = zahtev.getSekretar().getIme() + " " + zahtev.getSekretar().getPrezime();
				String kursevi = "";
				if (!zahtev.getKursevi().contains(null)) {
					for(int j=0;j<zahtev.getKursevi().size();j++) {
						if (j == 0)
							kursevi = zahtev.getKursevi().get(j).getNaziv();
						else
							kursevi += ", " + zahtev.getKursevi().get(j).getNaziv(); 
					}
				}
				sadrzaj1[i][1] = kursevi;
				sadrzaj1[i][2] = zahtev.getStatus();
			}
			
			tModel1 = new DefaultTableModel(sadrzaj1, zaglavlje1);
			tabela1 = new JTable(tModel1);
			
			tabela1.setRowSelectionAllowed(true);
			tabela1.setColumnSelectionAllowed(false);
			tabela1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tabela1.setDefaultEditor(Object.class, null);
			
			JScrollPane sp1 = new JScrollPane(tabela1);
			add(sp1, BorderLayout.CENTER);
		}
		else {
			for(Zahtev z : menadzer.getZahteviKursMen().getZahteviKurs()) {
				for(Kurs kurs : z.getKursevi()) {
					if(z.getDatumZahteva().isAfter(pocetak) && z.getDatumZahteva().isBefore(kraj) && listaJez.contains(kurs.getJezik())) {
						System.out.println("proslo");
						zahtevi.add(z);
					}
				}
			}
			
			String[] zaglavlje1 = new String[] {"Sekretar", "Kursevi", "Status"};
			Object[][] sadrzaj1 = new Object[zahtevi.size()][zaglavlje1.length];
			
			for(int i=0; i<zahtevi.size(); i++) {
				Zahtev zahtev = zahtevi.get(i);
				sadrzaj1[i][0] = zahtev.getSekretar().getIme() + " " + zahtev.getSekretar().getPrezime();
				String kursevi = "";
				if (!zahtev.getKursevi().contains(null)) {
					for(int j=0;j<zahtev.getKursevi().size();j++) {
						if (j == 0)
							kursevi = zahtev.getKursevi().get(j).getNaziv();
						else
							kursevi += ", " + zahtev.getKursevi().get(j).getNaziv(); 
					}
				}
				sadrzaj1[i][1] = kursevi;
				sadrzaj1[i][2] = zahtev.getStatus();
			}
			
			tModel1 = new DefaultTableModel(sadrzaj1, zaglavlje1);
			tabela1 = new JTable(tModel1);
			
			tabela1.setRowSelectionAllowed(true);
			tabela1.setColumnSelectionAllowed(false);
			tabela1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tabela1.setDefaultEditor(Object.class, null);
			
			JScrollPane sp1 = new JScrollPane(tabela1);
			add(sp1, BorderLayout.CENTER);
			
			
		}
		
	}
	
	
	
	
}

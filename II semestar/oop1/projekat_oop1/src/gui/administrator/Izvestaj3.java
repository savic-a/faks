package gui.administrator;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import citanjeIzFajla.Menadzer;
import entiteti.Kurs;
import entiteti.Rezultati;
import entiteti.Ucenici;

public class Izvestaj3 extends JDialog{

	private static final long serialVersionUID = -2019784482521913801L;
	
	private Menadzer menadzer;
	
	private List<Integer> brKurseva;
	private List<Double> cena;
	
	private DefaultTableModel tModel1;
	private JTable tabela1;

	public Izvestaj3(Menadzer menadzer) {
		super();
		this.menadzer = menadzer;
		this.brKurseva = new ArrayList<Integer>();
		this.cena = new ArrayList<Double>();
		
		this.setTitle("Izveštaj 3");
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setIconImage(new ImageIcon("./src/slike/icon.png").getImage());
		initMenu();
		pack();
	}

	private void initMenu() {
		int i = 0;
		double j = 0;
		for (Ucenici u : menadzer.getUcenikMen().getUcenici()) {
			for (Rezultati rez : menadzer.getRezMen().getRezultati()) {
				if(rez.getUcenik() == u && rez.getPolozio() == "polozio") {
					i += 1;
					String naziv = rez.getTest().getKurs();
					Kurs kurs = menadzer.getKursMen().pronadjiKursPoNazivu(naziv);
					j = kurs.getCenaKursa();
					brKurseva.add(i);
					cena.add(j);
				}
			}i=0;
			j=0;	
		}
		
		String[] zaglavlje1 = new String[] {"Ucenik", "Br kurseva", "Iznos"};
		Object[][] sadrzaj1 = new Object[cena.size()][zaglavlje1.length];
		
		for(int k=0; k<cena.size(); k++) {
			Ucenici u = menadzer.getUcenikMen().getUcenici().get(k);
			sadrzaj1[i][0] = u.getIme() + " " + u.getPrezime();
			sadrzaj1[i][1] = brKurseva.get(i);
			sadrzaj1[i][2] = cena.get(i);
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

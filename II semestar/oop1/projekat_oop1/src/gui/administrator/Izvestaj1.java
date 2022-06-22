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
import entiteti.Predavac;
import entiteti.Sekretar;
import entiteti.Test;

public class Izvestaj1 extends JDialog{

	private static final long serialVersionUID = 1078252327813867646L;
	
	private LocalDate pocetak;
	private LocalDate kraj;
	private Menadzer menadzer;
	private List<Integer> brTestova;
	
	private DefaultTableModel tModel1;
	private JTable tabela1;
	
	private DefaultTableModel tModel2;
	private JTable tabela2;
	
	public Izvestaj1(LocalDate pocetak, LocalDate kraj, Menadzer menadzer){
		super();
		this.pocetak = pocetak;
		this.kraj = kraj;
		this.menadzer = menadzer;
		this.brTestova = new ArrayList<Integer>();
		this.setTitle("Izveštaj 1");
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setIconImage(new ImageIcon("./src/slike/icon.png").getImage());
		initMenu();
		pack();
	}
	
	private void initMenu() {
		String[] zaglavlje1 = new String[] {"Ime i prezime", "Broj testova"};
		Object[][] sadrzaj1 = new Object[menadzer.getPredMen().getPredavaci().size()][zaglavlje1.length];
		
		for(Predavac p : menadzer.getPredMen().getPredavaci()) {
			int i = 0;
			for(Test t : menadzer.getTestMen().getTestovi()) {
				if(t.getDatum().isAfter(pocetak) && t.getDatum().isBefore(kraj) && p == t.getPredavac())
					i++;
			}brTestova.add(i);
		}
		
		for(int i = 0; i<menadzer.getPredMen().getPredavaci().size(); i++) {
			Predavac predavac = menadzer.getPredMen().getPredavaci().get(i);
			sadrzaj1[i][0] = predavac.getIme() + " " + predavac.getPrezime();
			sadrzaj1[i][1] = Integer.toString(brTestova.get(i));
		}
		
		tModel1 = new DefaultTableModel(sadrzaj1, zaglavlje1);
		tabela1 = new JTable(tModel1);
		
		tabela1.setRowSelectionAllowed(true);
		tabela1.setColumnSelectionAllowed(false);
		tabela1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabela1.setDefaultEditor(Object.class, null);
		
		JScrollPane sp1 = new JScrollPane(tabela1);
		add(sp1, BorderLayout.CENTER);
		
		String[] zaglavlje2 = new String[] {"Ime i prezime", "Broj zahteva"};
		Object[][] sadrzaj2 = new Object[menadzer.getSekMen().getSekretari().size()][zaglavlje2.length];
		
		for(int i = 0; i<menadzer.getSekMen().getSekretari().size(); i++) {
			Sekretar sekretar = menadzer.getSekMen().getSekretari().get(i);
			sadrzaj2[i][0] = sekretar.getIme() + " " + sekretar.getPrezime();
			sadrzaj2[i][1] = Integer.toString(sekretar.getBonus());
		}
		
		tModel2 = new DefaultTableModel(sadrzaj2, zaglavlje2);
		tabela2 = new JTable(tModel2);
		
		tabela2.setRowSelectionAllowed(true);
		tabela2.setColumnSelectionAllowed(false);
		tabela2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabela2.setDefaultEditor(Object.class, null);
		
		JScrollPane sp2 = new JScrollPane(tabela2);
		add(sp2, BorderLayout.NORTH);
	}
	
}

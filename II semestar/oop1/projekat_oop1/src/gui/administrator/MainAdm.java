package gui.administrator;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import citanjeIzFajla.Menadzer;
import entiteti.Kurs;
import entiteti.Predavac;
import entiteti.Sekretar;
import entiteti.Ucenici;
import gui.LoginProzor;

public class MainAdm extends JFrame{

	private static final long serialVersionUID = 7605961449669699839L;
	
	private Menadzer menadzer;
	private double prihodi;
	private double rashodi;
	private DefaultTableModel tModel;
	private JTable tabela;
	
	private JMenuBar mainMenu = new JMenuBar();
	
	private JMenu odjavaMenu = new JMenu("Odjavite se");
	private JMenuItem odjavaItem = new JMenuItem("Odjava");
	
	private JMenu zaposleniMenu = new JMenu("Zaposleni");
	private JMenuItem predavaciItem = new JMenuItem("Predavači");
	private JMenuItem sekretariItem = new JMenuItem("Sekretari");
	
	private JMenu ostaloMenu = new JMenu("Ostalo");
	private JMenuItem uceniciItem = new JMenuItem("Učenici");
	private JMenuItem kurseviItem = new JMenuItem("Kursevi");
	private JMenuItem jeziciItem = new JMenuItem("Jezici");
	
	private JMenu izvestajiMenu = new JMenu("Izveštaji");
	private JMenuItem izvestaj1Item = new JMenuItem("Izveštaj 1");
	private JMenuItem izvestaj2Item = new JMenuItem("Izveštaj 2");
	private JMenuItem izvestaj3Item = new JMenuItem("Izveštaj 3");

	
	public MainAdm(Menadzer menadzer, String username) {
		this.menadzer = menadzer;
		this.prihodi = 0.0;
		this.rashodi = 0.0;
		this.setTitle("Administrator: " + username);
		this.setSize(500, 500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setIconImage(new ImageIcon("./src/slike/icon.png").getImage());
		initMenu();
		initActions();
	}


	private void initMenu() {
		setJMenuBar(mainMenu);

		mainMenu.add(zaposleniMenu);
		zaposleniMenu.add(sekretariItem);
		zaposleniMenu.add(predavaciItem);
		
		mainMenu.add(ostaloMenu);
		ostaloMenu.add(uceniciItem);
		ostaloMenu.add(kurseviItem);
		ostaloMenu.add(jeziciItem);
		
		mainMenu.add(izvestajiMenu);
		izvestajiMenu.add(izvestaj1Item);
		izvestajiMenu.add(izvestaj2Item);
		izvestajiMenu.add(izvestaj3Item);
		
		mainMenu.add(odjavaMenu);
		odjavaMenu.add(odjavaItem);
		
		add(new JLabel("  Ovde na centralnom mestu glavnog prozora staviti ono sto je najbitnije za korisnika!"), BorderLayout.CENTER);
		// Npr. Za studenta su bitni ispiti, ispitni rokovi, ili ocene i prosek itd.
		for(Ucenici u : menadzer.getUcenikMen().getUcenici()) {
			for(Kurs k : u.getKursevi()) {
				prihodi += k.getCenaKursa();
			}
		}
		
		for(Predavac p : menadzer.getPredMen().getPredavaci()) {
			rashodi += p.getPlata();
		}
		for(Sekretar s : menadzer.getSekMen().getSekretari()) {
			rashodi += s.getPlata();
		}
		
		String[] zaglavlje = new String[] {"Prihodi", "Rashodi"};
		Object[][] sadrzaj = new Object[1][zaglavlje.length];
		
		sadrzaj[0][0] = prihodi;
		sadrzaj[0][1] = rashodi;
		
		tModel = new DefaultTableModel(sadrzaj, zaglavlje);
		tabela = new JTable(tModel);
		
		tabela.setRowSelectionAllowed(true);
		tabela.setColumnSelectionAllowed(false);
		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabela.setDefaultEditor(Object.class, null);
		
		JScrollPane sp = new JScrollPane(tabela);
		add(sp, BorderLayout.CENTER);
		
	}
		

	private void initActions() {
		odjavaItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainAdm.this.setVisible(false);
				LoginProzor lg = new LoginProzor(menadzer);
				lg.toString();
			}
			
		});
		
		sekretariItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SekretariTabela st = new SekretariTabela(MainAdm.this, menadzer.getSekMen());
				st.setVisible(true);
			}
		});
		
		predavaciItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PredavaciTabela pt = new PredavaciTabela(MainAdm.this, menadzer);
				pt.setVisible(true);
			}
		});
		
		uceniciItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				UceniciTabela ut = new UceniciTabela(MainAdm.this, menadzer.getUcenikMen());
				ut.setVisible(true);
			}
		});
		
		kurseviItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				KursTabela kt = new KursTabela(MainAdm.this, menadzer.getKursMen(), menadzer.getJezMen());
				kt.setVisible(true);
			}
		});
		
		jeziciItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JezikTabela jt = new JezikTabela(MainAdm.this, menadzer.getJezMen(), menadzer.getKursMen(), menadzer.getPredMen(), menadzer.getTestMen());
				jt.setVisible(true);
			}
		});
		
		izvestaj1Item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Datumi d = new Datumi(menadzer);
				d.setVisible(true);
				
			}
		});
		
		izvestaj2Item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				DatumiIJezici dij = new DatumiIJezici(menadzer);
				dij.setVisible(true);	
			}
		});
		
		izvestaj3Item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Izvestaj3 izv = new Izvestaj3(menadzer);
				izv.setVisible(true);	
			}
		});
	}
}

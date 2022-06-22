package gui.sekretar;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import citanjeIzFajla.Menadzer;
import entiteti.Sekretar;
import entiteti.Zahtev;
import gui.LoginProzor;
import modeli.KreiranZahtevModel;

public class MainSekretar extends JFrame{

	private static final long serialVersionUID = 4110098703530235517L;
	
	private Menadzer menadzer;
	private String username;
	private List<Zahtev> zahtevi = new ArrayList<Zahtev>();
	
	private JMenuBar mainMenu = new JMenuBar(); 
	
	private JMenu predavaciMenu = new JMenu("Predavaci");
	private JMenuItem predavaciItem = new JMenuItem("Postavi predavača");
	
	private JMenu zahteviMenu = new JMenu("Zahtevi");
	private JMenuItem registrujItem = new JMenuItem("Registruj ucenika");
	private JMenuItem regItem = new JMenuItem("Registrovani");
	private JMenuItem kursItem = new JMenuItem("Preuzeti kursevi");
	
	private JMenu odjavaMenu = new JMenu("Odjavite se");
	private JMenuItem odjavaItem = new JMenuItem("Odjava");
	
	private JPanel panelBtns = new JPanel();
	private JButton preuzimanjeBtn = new JButton("Preuzmite zahtev");
	private JButton dodajBtn = new JButton("Kreiraj nov zahtev");
	
	protected JTable table;
	protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();
	
	public MainSekretar(Menadzer menadzer, String username) {
		this.menadzer = menadzer;
		this.username = username;
		this.setTitle("Sekretar: " + username); 
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
		mainMenu.add(predavaciMenu);
		predavaciMenu.add(predavaciItem);
		
		mainMenu.add(zahteviMenu);
		zahteviMenu.add(registrujItem);
		zahteviMenu.add(regItem);
		zahteviMenu.add(kursItem);
		
		mainMenu.add(odjavaMenu);
		odjavaMenu.add(odjavaItem);
		
		for(Zahtev z : menadzer.getZahteviKursMen().getZahteviKurs()) {
			if(z.getStatus().equals("kreiran") && !zahtevi.contains(z)) {
				zahtevi.add(z);
			}
		}
		if(!zahtevi.isEmpty()) {
			JLabel poruka = new JLabel("KREIRANI ZAHTEVI KURSEVA");
			poruka.setHorizontalAlignment(JLabel.CENTER);
			add(poruka, BorderLayout.NORTH);
			
			table = new JTable(new KreiranZahtevModel(MainSekretar.this, menadzer.getZahteviKursMen(), menadzer, username));		
			table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.getTableHeader().setReorderingAllowed(false);
			tableSorter.setModel((AbstractTableModel) table.getModel());
			table.setRowSorter(tableSorter);
			JScrollPane sc = new JScrollPane(table);
			
			add(sc, BorderLayout.CENTER);
			
			panelBtns.add(dodajBtn);
			panelBtns.add(preuzimanjeBtn);
			getContentPane().add(panelBtns, BorderLayout.SOUTH);
		}
		else {
			JLabel poruka = new JLabel("Još uvek ne postoji nijedan kreiran zahtev kursa!");
			poruka.setHorizontalAlignment(JLabel.CENTER);
			add(poruka, BorderLayout.CENTER);
		}
	}
	
	private void initActions() {
		preuzimanjeBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = table.getSelectedRow();
				if(red == -1) 
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				else {
					String id = table.getValueAt(red, 0).toString();
					Zahtev z = menadzer.getZahteviKursMen().pronadjiZahtevPoId(id);
					if(z != null) {
						int izbor = JOptionPane.showConfirmDialog(null,"Da li ste sigurni da zelite da preuzmete zahtev?", 
								"Potvrda preuzimanja", JOptionPane.YES_NO_OPTION);
						if(izbor == JOptionPane.YES_OPTION) {
							Sekretar s = menadzer.getSekMen().pronadjiSekretaraPoUsername(username);
							menadzer.getZahteviKursMen().izmena(z.getId(), z.getUcenik(), s, z.getKursevi(), "u obradi");
							refreshData();
						}
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabrani zahtev!", "Greska", JOptionPane.ERROR_MESSAGE);
					}
				}
				
			}
		});
		
		dodajBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				DodajZahtevEdit dze = new DodajZahtevEdit(MainSekretar.this, menadzer);
				dze.setVisible(true);
				
			}
		});
		
		odjavaItem.addActionListener(new ActionListener() {
					
				@Override
				public void actionPerformed(ActionEvent e) {
					MainSekretar.this.setVisible(false);
					LoginProzor lg = new LoginProzor(menadzer);
					lg.toString();
				}
			});
		
		predavaciItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PPTabela ppt = new PPTabela(MainSekretar.this, menadzer.getKursMen(), menadzer.getPredMen(), menadzer.getKursNoviMen());
				ppt.setVisible(true);
			}
		});
		
		registrujItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Sekretar s = menadzer.getSekMen().pronadjiSekretaraPoUsername(username);
				RegUcenikaEdit dodaj = new RegUcenikaEdit(MainSekretar.this, menadzer, s);
				dodaj.setVisible(true);
			}
		});
		
		regItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Sekretar s = menadzer.getSekMen().pronadjiSekretaraPoUsername(username);
				boolean zahtevi = false;
				for(Zahtev z : menadzer.getZahteviRegMen().getZahteviReg()) {
					if(z.getSekretar() == s)
						zahtevi = true;
				}
				if(zahtevi) {
					ZahteviRegTabela zrt = new ZahteviRegTabela(MainSekretar.this, menadzer, s);
					zrt.setVisible(true);
				}
				else {
					JOptionPane.showMessageDialog(null, "Ne postoji nijedan zahtev za registraciju", "Info", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		kursItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Sekretar s = menadzer.getSekMen().pronadjiSekretaraPoUsername(username);
				boolean zahtevi = false;
				for(Zahtev z : menadzer.getZahteviKursMen().getZahteviKurs()) {
					if(z.getSekretar() == s)
						zahtevi = true;
				}
				if(zahtevi) {
					PreuzetiZahteviTabela pzt = new PreuzetiZahteviTabela(MainSekretar.this, menadzer, s);
					pzt.setVisible(true);
				}
				else {
					JOptionPane.showMessageDialog(null, "Ne postoji nijedan preuzet zahtev", "Info", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
	}
	
	public void refreshData() {
		try {
			KreiranZahtevModel kzm = (KreiranZahtevModel)this.table.getModel();
			kzm.fireTableDataChanged();
		}catch (Exception e) {
			
		}
	}
}

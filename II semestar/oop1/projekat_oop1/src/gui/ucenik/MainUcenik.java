package gui.ucenik;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
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
import entiteti.Kurs;
import entiteti.Rezultati;
import entiteti.Test;
import entiteti.Ucenici;
import gui.LoginProzor;
import modeli.TestoviUcenikaModel;

public class MainUcenik extends JFrame{

	private static final long serialVersionUID = -517906152626883322L;
	
	private Menadzer menadzer;
	private String username;
	
	private JMenuBar mainMenu = new JMenuBar();
	
	private JMenu kurseviMenu = new JMenu("Kursevi");
	private JMenuItem tabelaItem = new JMenuItem("Moji kursevi");
	
	private JMenu odjavaMenu = new JMenu("Odjavite se");
	private JMenuItem odjavaItem = new JMenuItem("Odjava");
	
	private JPanel panelBtns = new JPanel();
	private JButton rezultatBtn = new JButton("Rezultat testa");
	private JButton prijavaTBtn = new JButton("Prijava testa");

	protected JTable table;
	protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();
	
	public MainUcenik(Menadzer menadzer, String username) {
		this.menadzer = menadzer;
		this.username = username;
		this.setTitle("Učenik: " + username);
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
		mainMenu.add(kurseviMenu);
		kurseviMenu.add(tabelaItem);
		
		mainMenu.add(odjavaMenu);
		odjavaMenu.add(odjavaItem);
		
		Ucenici ucenik = menadzer.getUcenikMen().pronadjiUcenikaPoUsername(username);
		if(!ucenik.getTestovi().isEmpty()) {
			JLabel poruka = new JLabel("MOJI TESTOVI");
			poruka.setHorizontalAlignment(JLabel.CENTER);
			add(poruka, BorderLayout.NORTH);
			
			table = new JTable(new TestoviUcenikaModel(ucenik));		
			table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.getTableHeader().setReorderingAllowed(false);
			tableSorter.setModel((AbstractTableModel) table.getModel());
			table.setRowSorter(tableSorter);
			JScrollPane sc = new JScrollPane(table);
			add(sc, BorderLayout.CENTER);
			
			panelBtns.add(rezultatBtn);
			panelBtns.add(prijavaTBtn);
			getContentPane().add(panelBtns, BorderLayout.SOUTH);
			
		}
		else {
			JLabel poruka = new JLabel("Još uvek se niste prijavili ni za jedan test!");
			poruka.setHorizontalAlignment(JLabel.CENTER);
			add(poruka, BorderLayout.CENTER);
		}
	}
	
	private void initActions() {	
		odjavaItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainUcenik.this.setVisible(false);
				LoginProzor lg = new LoginProzor(menadzer);
				lg.toString();
			}
		});
		
		rezultatBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = table.getSelectedRow();
				if(red == -1) 
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				else {
					Ucenici ucenik = menadzer.getUcenikMen().pronadjiUcenikaPoUsername(username);
					String id = table.getValueAt(red, 0).toString();
					Test t = menadzer.getTestMen().pronadjiTestPoId(id);
					String rezId = "";
					boolean ok = false;
					for(Rezultati r : menadzer.getRezMen().getRezultati()) {
						if(r.getTest() == t && r.getUcenik() == ucenik)
							rezId = r.getId();
							ok = true;
					}
					if(t.getDatum().isAfter(LocalDate.now())) {
						JOptionPane.showMessageDialog(null, "Izabran test još uvek nije prošao!", "Info", JOptionPane.INFORMATION_MESSAGE);
					}
					else if(!ok) {
						JOptionPane.showMessageDialog(null, "Test još uvek nije ocenjen!", "Info", JOptionPane.INFORMATION_MESSAGE);
					}
					else if(ok) {
						Rezultati r = menadzer.getRezMen().pronadjiRezultatPoId(rezId);
						RezultatPrikaz rp = new RezultatPrikaz(MainUcenik.this, r);
						rp.setVisible(true);
					}
				}
				
			}
		});
		
		prijavaTBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Ucenici ucenik = menadzer.getUcenikMen().pronadjiUcenikaPoUsername(username);
				List<Test> testovi = new ArrayList<Test>();
				for(Kurs k : ucenik.getKursevi()) {
					for(Test t : k.getTestovi()) {
						if(!ucenik.getTestovi().contains(t) && t.getDatum().isAfter(LocalDate.now())) {
							testovi.add(t);
						}
					}
				}
				if(testovi.isEmpty())
					JOptionPane.showMessageDialog(null, "Ne postoji nijedan dostupan test!", "Info", JOptionPane.INFORMATION_MESSAGE);
				else {
					System.out.println(testovi);
					PrijavaTestovaTabela ptt = new PrijavaTestovaTabela(MainUcenik.this, menadzer, ucenik, testovi);
					ptt.setVisible(true);
				}
			}
		});
		
		tabelaItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Ucenici ucenik = menadzer.getUcenikMen().pronadjiUcenikaPoUsername(username);
				KurseviTabela kt = new KurseviTabela(MainUcenik.this, menadzer, ucenik);
				kt.setVisible(true);
			}
		});
		
	}
	
	public void refreshData() {
		TestoviUcenikaModel tum = (TestoviUcenikaModel)this.table.getModel();
		tum.fireTableDataChanged();
	}
}

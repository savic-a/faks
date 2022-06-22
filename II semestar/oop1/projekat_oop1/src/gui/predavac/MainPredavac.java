package gui.predavac;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import citanjeIzFajla.Menadzer;
import entiteti.Kurs;
import entiteti.Predavac;
import entiteti.Test;
import gui.LoginProzor;
import modeli.KurseviPredavacaModel;

public class MainPredavac extends JFrame{
	
	private static final long serialVersionUID = -2139506992304243297L;
	
	private Menadzer menadzer;
	private String username; 
	private List<Kurs> kursevi;
	
	private JMenuBar mainMenu = new JMenuBar();
	
	private JMenu testoviMenu = new JMenu("Testovi");
	private JMenuItem kreirajItem = new JMenuItem("Kreiraj");
	private JMenuItem tabelaItem = new JMenuItem("Tabela");
	
	private JMenu odjavaMenu = new JMenu("Odjavite se");
	private JMenuItem odjavaItem = new JMenuItem("Odjava");
	
	protected JTable table;
	protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();

	public MainPredavac(Menadzer menadzer, String username) {
		this.menadzer = menadzer;
		this.username = username;
		this.kursevi = new ArrayList<Kurs>();
		this.setTitle("Predavač: " + username);
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
		mainMenu.add(testoviMenu);
		testoviMenu.add(kreirajItem);
		testoviMenu.add(tabelaItem);
		
		mainMenu.add(odjavaMenu);
		odjavaMenu.add(odjavaItem);
		
		Predavac p = menadzer.getPredMen().PronadjiPredavacaPoUsername(username);
		for(Kurs k : menadzer.getKursMen().getKursevi()) {
			if(k.getPredavac() == p) {
				kursevi.add(k);
			}
		}
		if(kursevi.size() != 0) {
			JLabel poruka = new JLabel("MOJI KURSEVI");
			poruka.setHorizontalAlignment(JLabel.CENTER);
			add(poruka, BorderLayout.NORTH);
			
			table = new JTable(new KurseviPredavacaModel(menadzer.getKursMen(), p));		
			table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.getTableHeader().setReorderingAllowed(false);
			tableSorter.setModel((AbstractTableModel) table.getModel());
			table.setRowSorter(tableSorter);
			JScrollPane sc = new JScrollPane(table);
			add(sc, BorderLayout.CENTER);
		}
		else {
			JLabel poruka = new JLabel("Još uvek ne predajete nijedan kurs!");
			poruka.setHorizontalAlignment(JLabel.CENTER);
			add(poruka, BorderLayout.CENTER);
		}
	}

	private void initActions() {
		odjavaItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainPredavac.this.setVisible(false);
				LoginProzor lg = new LoginProzor(menadzer);
				lg.toString();
			}
		});
		
		kreirajItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(kursevi.size() == 0)
					JOptionPane.showMessageDialog(null, "Ne predajete nijedan kurs pa ne možete da kreirate test.", "Info", JOptionPane.INFORMATION_MESSAGE);
				else {
					Predavac p = menadzer.getPredMen().PronadjiPredavacaPoUsername(username);
					KreirajTest kt = new KreirajTest(MainPredavac.this, menadzer, p);
					kt.setVisible(true);
				}
			}
		});
		
		tabelaItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Predavac p = menadzer.getPredMen().PronadjiPredavacaPoUsername(username);
				boolean test = false;
				for(Test t : menadzer.getTestMen().getTestovi()) {
					if(t.getPredavac() == p) {
						test = true;
					}
				}if(test) {
					TestoviTabela tt = new TestoviTabela(MainPredavac.this, menadzer, p);
					tt.setVisible(true);
				}
				else
					JOptionPane.showMessageDialog(null, "Nemate nijedan kreiran test", "Info", JOptionPane.INFORMATION_MESSAGE);
				
			}
		});
	}

	
}

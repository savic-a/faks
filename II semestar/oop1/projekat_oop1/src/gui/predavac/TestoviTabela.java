package gui.predavac;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import citanjeIzFajla.Menadzer;
import entiteti.Predavac;
import entiteti.Rezultati;
import entiteti.Test;
import entiteti.Ucenici;
import modeli.TestModel;

public class TestoviTabela extends JFrame{

	private static final long serialVersionUID = -8053296318492305300L;

	private Menadzer menadzer;
//	private Predavac p;
	
	protected JToolBar mainToolbar = new JToolBar(); 
	protected JButton dodaj = new JButton();
	protected JButton izmeni = new JButton();
	protected JTextField tfSearch = new JTextField(20);
	protected JTable table;
	protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();
	
	private JPanel panelBtns = new JPanel();
	private JButton oceniBtn = new JButton("Oceni test");
	private JButton rezBtn = new JButton("Rezultati testa");

	public TestoviTabela(JFrame parent, Menadzer mng, Predavac p) {
		this.menadzer = mng;
//		this.p = p;
		setTitle("Testovi");	
		setSize(800, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);		
		setIconImage(new ImageIcon("./src/slike/icon.png").getImage());
		
		// podesavanje toolbar-a
		ImageIcon izmeniIkonica = new ImageIcon("./src/slike/edit.gif");
		izmeni.setIcon(izmeniIkonica);
		mainToolbar.add(izmeni);
		mainToolbar.setFloatable(false);		
		add(mainToolbar, BorderLayout.NORTH);
		
		// podesavanje tabele
		table = new JTable(new TestModel(menadzer, p));		
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		tableSorter.setModel((AbstractTableModel) table.getModel());
		table.setRowSorter(tableSorter);
		JScrollPane sc = new JScrollPane(table);
		add(sc, BorderLayout.CENTER);
		
		// dugmici
		panelBtns.add(oceniBtn);
		panelBtns.add(rezBtn);
		getContentPane().add(panelBtns, BorderLayout.SOUTH);

		initActions();
	}

	private void initActions() {
		izmeni.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = table.getSelectedRow();
				if(red == -1) 
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				else {
					String id = table.getValueAt(red, 0).toString();
					Test t = menadzer.getTestMen().pronadjiTestPoId(id);
					boolean izbor = true;
					for(Ucenici ucenik : menadzer.getUcenikMen().getUcenici()) {
						if(ucenik.getTestovi().contains(t)) {
							JOptionPane.showMessageDialog(null, "Ne mozete da menjate izabran test", "Info", JOptionPane.INFORMATION_MESSAGE);
							izbor = false;
							break;
						}
					}
					if(izbor) {
						IzmenaTestaEdit ite = new IzmenaTestaEdit(TestoviTabela.this, menadzer, t);
						ite.setVisible(true);
					}
				}	
			}
		});
		
		oceniBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = table.getSelectedRow();
				if(red == -1) 
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				else {
					String id = table.getValueAt(red, 0).toString();
					Test t = menadzer.getTestMen().pronadjiTestPoId(id);
					List<String> ucenici = new ArrayList<String>();
					List<Ucenici> uceniciLista = new ArrayList<Ucenici>();
					boolean izbor = true;
					for(Ucenici ucenik : menadzer.getUcenikMen().getUcenici()) {
						if(ucenik.getTestovi().contains(t)) {
							ucenici.add(ucenik.getUsername());
							uceniciLista.add(ucenik);
						}
					}
					for(Ucenici u : menadzer.getUcenikMen().getUcenici()) {
						for(Rezultati r : menadzer.getRezMen().getRezultati()) {
							if(r.getTest() == t && r.getUcenik() == u) 
								uceniciLista.remove(u);
						}
					}
					if(ucenici.size() == 0) {
						JOptionPane.showMessageDialog(null, "Ne postoji učenik koji je radio test!", "Info", JOptionPane.INFORMATION_MESSAGE);
						izbor = false;
					}
					else if(uceniciLista.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Ocenili ste već sve učenike!", "Info", JOptionPane.INFORMATION_MESSAGE);
						izbor = false;
					}
					else if(t.getDatum().isAfter(LocalDate.now())) {
						JOptionPane.showMessageDialog(null, "Izabran test još uvek nije prošao!", "Info", JOptionPane.INFORMATION_MESSAGE);
						izbor = false;
					}
					else if(izbor) {
						OcenjivanjeEdit ocenjivanje = new OcenjivanjeEdit(TestoviTabela.this, menadzer, ucenici, t);
						ocenjivanje.setVisible(true);
					}
					
				}
			}
		});
		
		rezBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = table.getSelectedRow();
				if(red == -1) 
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				else {
					String id = table.getValueAt(red, 0).toString();
					Test t = menadzer.getTestMen().pronadjiTestPoId(id);
					List<String> ucenici = new ArrayList<String>();
//					List<Ucenici> uceniciLista = new ArrayList<Ucenici>();
					boolean izbor = true;
					for(Ucenici ucenik : menadzer.getUcenikMen().getUcenici()) {
						if(ucenik.getTestovi().contains(t)) {
							ucenici.add(ucenik.getUsername());
//							uceniciLista.add(ucenik);
						}
					}
//					for(Ucenici u : menadzer.getUcenikMen().getUcenici()) {
//						for(Rezultati r : menadzer.getRezMen().getRezultati()) {
//							if(r.getTest() == t && r.getUcenik() == u) 
//								uceniciLista.remove(u);
//						}
//					}
//					if(uceniciLista.isEmpty()) {
//						JOptionPane.showMessageDialog(null, "Ocenili ste već sve učenike!", "Info", JOptionPane.INFORMATION_MESSAGE);
//						izbor = false;
//					}
					if(ucenici.size() == 0) {
						JOptionPane.showMessageDialog(null, "Ne postoji učenik koji je radio test!", "Info", JOptionPane.INFORMATION_MESSAGE);
						izbor = false;
					}
					else if(t.getDatum().isAfter(LocalDate.now())) {
						JOptionPane.showMessageDialog(null, "Izabran test još uvek nije prošao!", "Info", JOptionPane.INFORMATION_MESSAGE);
						izbor = false;
					}
					else if(izbor) {
						RezultatiIzvestaj ri = new RezultatiIzvestaj(menadzer, t, ucenici);
						ri.setVisible(true);
					}
				}
				
			}
		});
	}

	public void refreshData() {
		TestModel tm = (TestModel)this.table.getModel();
		tm.fireTableDataChanged();
		
	}
}

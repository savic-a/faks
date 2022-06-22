package gui.administrator;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import citanjeIzFajla.Menadzer;
import citanjeIzFajla.PredMenadzer;
import entiteti.Kurs;
import entiteti.Predavac;
import modeli.PredavacModel;

public class PredavaciTabela extends JFrame{

	private static final long serialVersionUID = 3286540219875897452L;
	
	private Menadzer menadzer;
	private PredMenadzer predavacMen;
	
	protected JToolBar mainToolbar = new JToolBar();
	protected JButton dodaj = new JButton();
	protected JButton izmeni = new JButton();
	protected JButton obrisi = new JButton();
	protected JTextField tfSearch = new JTextField(20);
	protected JTable table;
	protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();
//	private JFrame parentFrame;

	public PredavaciTabela(JFrame parent, Menadzer mng) {
		this.menadzer = mng;
		this.predavacMen = mng.getPredMen();
//		this.parentFrame = parent;	
		// podesavanje prozora
		setTitle("Predavači");	
		setSize(800, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);		
		setIconImage(new ImageIcon("./src/slike/icon.png").getImage());
		
		// podesavanje toolbar-a
		ImageIcon dodajIkonica = new ImageIcon("./src/slike/add.gif");		
		dodaj.setIcon(dodajIkonica);
		mainToolbar.add(dodaj);
		ImageIcon izmeniIkonica = new ImageIcon("./src/slike/edit.gif");
		izmeni.setIcon(izmeniIkonica);
		mainToolbar.add(izmeni);
		ImageIcon obrisiIkonica = new ImageIcon("./src/slike/remove.gif");
		obrisi.setIcon(obrisiIkonica);
		mainToolbar.add(obrisi);
		mainToolbar.setFloatable(false);		
		add(mainToolbar, BorderLayout.NORTH);
		
		// podesavanje tabele
		table = new JTable(new PredavacModel(mng.getPredMen()));		
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		// podesavanje manuelnog sortera tabele, potrebno i za pretragu
		tableSorter.setModel((AbstractTableModel) table.getModel());
		table.setRowSorter(tableSorter);
		JScrollPane sc = new JScrollPane(table);
		add(sc, BorderLayout.CENTER);
				
//		table.getTableHeader().addMouseListener(new MouseAdapter() {
//			
//			@Override
//			public void mouseClicked(MouseEvent arg0) {
//				// preuzimanje indeksa kolone potrebnog za sortiranje
//				int index = table.getTableHeader().columnAtPoint(arg0.getPoint());
//				
//				// call abstract sort method
////				sort(index);
//			}
//			
//		});
		// podesavanje pretrage 
//		JPanel pSearch = new JPanel(new FlowLayout(FlowLayout.LEFT));		
//		pSearch.setBackground(Color.cyan);
//		pSearch.add(new JLabel("Search:"));
//		pSearch.add(tfSearch);
//		
//		add(pSearch, BorderLayout.SOUTH);
//		
//		tfSearch.getDocument().addDocumentListener(new DocumentListener() {
//			
//			@Override
//			public void removeUpdate(DocumentEvent e) {
//				changedUpdate(e);
//			}
//			
//			@Override
//			public void insertUpdate(DocumentEvent e) {
//				changedUpdate(e);
//			}
//			
//			@Override
//			public void changedUpdate(DocumentEvent e) {
//				//System.out.println("~ "+tfSearch.getText());
//				if (tfSearch.getText().trim().length() == 0) {
//				     tableSorter.setRowFilter(null);
//				  } else {
//					  tableSorter.setRowFilter(RowFilter.regexFilter("(?i)" + tfSearch.getText().trim()));
//				  }
//			}
//		});

		initActions();
	}
	
	

	private void initActions() {
		dodaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PredavacEdit dodaj = new PredavacEdit(PredavaciTabela.this, menadzer, null);
				dodaj.setVisible(true);
			}
		});
		
		izmeni.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = table.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					String username = table.getValueAt(red, 6).toString();
					Predavac p = predavacMen.PronadjiPredavacaPoUsername(username);
					System.out.println(username);
					if(p != null) {
						PredavacEdit izmeni = new PredavacEdit(PredavaciTabela.this, menadzer, p);
						izmeni.setVisible(true);
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabranog predavaca!", "Greska", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		obrisi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = table.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					String username = table.getValueAt(red, 6).toString();
					Predavac p = predavacMen.PronadjiPredavacaPoUsername(username);
					if(p != null) {
						int izbor = JOptionPane.showConfirmDialog(null,"Da li ste sigurni da zelite da obrisete predavaca?", 
								p.getIme() + " "+p.getPrezime() +" - Potvrda brisanja", JOptionPane.YES_NO_OPTION);
						if(izbor == JOptionPane.YES_OPTION) {
							for(Kurs k : menadzer.getKursMen().getKursevi()) {
								if(k.getPredavac() == p) {
									menadzer.getKursMen().izmena(k.getId(), k.getNaziv(), k.getJezik(), k.getCenaKursa(), k.getCenaTesta(), new Predavac(), k.getBrUcenika(), k.getTestovi());
									menadzer.getKursNoviMen().dodaj(k.getId(), k.getNaziv(), k.getJezik(), k.getCenaKursa(), k.getCenaTesta());
								}
							}
							predavacMen.brisanje(p.getUsername());
							refreshData();
						}
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabranog predavaca!", "Greska", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
	}
	// potrebno osvezavanje podataka u tabeli bez gasenja prozora
	public void refreshData() {
		PredavacModel pm = (PredavacModel)this.table.getModel();
		pm.fireTableDataChanged();
	}
}

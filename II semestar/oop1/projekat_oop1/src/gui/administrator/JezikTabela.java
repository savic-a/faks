package gui.administrator;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

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

import citanjeIzFajla.JeziciMenadzer;
import citanjeIzFajla.KursMen;
import citanjeIzFajla.PredMenadzer;
import citanjeIzFajla.TestMenadzer;
import entiteti.Jezik;
import entiteti.Kurs;
import entiteti.Predavac;
import entiteti.Test;
import modeli.JezikModel;

public class JezikTabela extends JFrame{

	private static final long serialVersionUID = 5315855896652905514L;
	
	private JeziciMenadzer jezMen;
	private KursMen kursMen;
	private PredMenadzer predMen;
	private TestMenadzer testMen;
	
	protected JToolBar mainToolbar = new JToolBar();
	protected JButton dodaj = new JButton();
	protected JButton izmeni = new JButton();
	protected JButton obrisi = new JButton();
	protected JTextField tfSearch = new JTextField(20);
	protected JTable table;
	protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();

	public JezikTabela(JFrame parent, JeziciMenadzer jezMen, KursMen kursMen, PredMenadzer predMen, TestMenadzer testMen) {
		this.jezMen = jezMen;
		this.kursMen = kursMen;
		this.predMen = predMen;
		this.testMen = testMen;
		setTitle("Jezici");	
		setSize(400, 300);
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
		table = new JTable(new JezikModel(jezMen));		
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		tableSorter.setModel((AbstractTableModel) table.getModel());
		table.setRowSorter(tableSorter);
		JScrollPane sc = new JScrollPane(table);
		add(sc, BorderLayout.CENTER);

		initActions();
	}
	
	
	private void initActions() {
		dodaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JezikEdit dodaj = new JezikEdit(JezikTabela.this, jezMen, null);
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
					String id = table.getValueAt(red, 0).toString();
					Jezik j = jezMen.pronadjiJezikPoId(id);
					if(j != null) {
						JezikEdit izmeni = new JezikEdit(JezikTabela.this, jezMen, j);
						izmeni.setVisible(true);
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabrani jezik!", "Greska", JOptionPane.ERROR_MESSAGE);
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
					String id = table.getValueAt(red, 0).toString();
					Jezik j = jezMen.pronadjiJezikPoId(id);
					if(j != null) {
						int izbor = JOptionPane.showConfirmDialog(null,"Da li ste sigurni da zelite da obrisete jezik?", 
								j.getNaziv() +" - Potvrda brisanja", JOptionPane.YES_NO_OPTION);
						if(izbor == JOptionPane.YES_OPTION) {
							for(int i=0; i<kursMen.getKursevi().size(); i++) {
								Kurs k = kursMen.getKursevi().get(i);
								if(k.getJezik() == j) {
									kursMen.brisanje(k.getId());
								}
							}
							
							for(int i=0; i<testMen.getTestovi().size(); i++) {
								Test t = testMen.getTestovi().get(i);
								if(t.getJezik() == j) {
									testMen.brisanje(t.getId());
								}
							}
							
							for(int i=0; i<predMen.getPredavaci().size(); i++) {
								Predavac p = predMen.getPredavaci().get(i);
								List<Jezik> lista = p.getJezici();
								
								if(p.getJezici().contains(j)) {
									lista.remove(j);
									predMen.izmena(p.getIme(), p.getPrezime(), p.getPol(), p.getDatum(), p.getTel(), p.getAdresa(),
											p.getUsername(), p.getLozinka(), p.getNivoSpreme(), p.getStaz(), lista);
								}
								
							}
							jezMen.brisanje(j.getId());
							refreshData();
						}
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabrani jezik!", "Greska", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
	}
	// potrebno osvezavanje podataka u tabeli bez gasenja prozora
	public void refreshData() {
		JezikModel jm = (JezikModel)this.table.getModel();
		jm.fireTableDataChanged();
	}
}

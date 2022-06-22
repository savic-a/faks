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

import citanjeIzFajla.JeziciMenadzer;
import citanjeIzFajla.KursMen;
import entiteti.Kurs;
import modeli.KursModel;

public class KursTabela extends JFrame{

	private static final long serialVersionUID = -6205440672331036176L;
	
	private KursMen kursMen;
	private JeziciMenadzer jezMen;
	
	protected JToolBar mainToolbar = new JToolBar();
	protected JButton dodaj = new JButton();
	protected JButton izmeni = new JButton();
	protected JButton obrisi = new JButton();
	protected JTextField tfSearch = new JTextField(20);
	protected JTable table;
	protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();

	public KursTabela(JFrame parent, KursMen kursMen, JeziciMenadzer jezMen) {
		this.kursMen = kursMen;
		this.jezMen = jezMen;
		setTitle("Kursevi");	
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
		table = new JTable(new KursModel(kursMen));		
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
				KursEdit dodaj = new KursEdit(KursTabela.this, kursMen, jezMen, null);
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
					Kurs k = kursMen.pronadjiKursPoId(id);
					if(k != null) {
						KursEdit izmeni = new KursEdit(KursTabela.this, kursMen, jezMen, k);
						izmeni.setVisible(true);
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabrani kurs!", "Greska", JOptionPane.ERROR_MESSAGE);
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
					Kurs k = kursMen.pronadjiKursPoId(id);
					if(k != null) {
						int izbor = JOptionPane.showConfirmDialog(null,"Da li ste sigurni da zelite da obrisete kurs?", 
								k.getNaziv() +" - Potvrda brisanja", JOptionPane.YES_NO_OPTION);
						if(izbor == JOptionPane.YES_OPTION) {
							kursMen.brisanje(k.getId());
							refreshData();
						}
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabrani kurs!", "Greska", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
	}
	
	// potrebno osvezavanje podataka u tabeli bez gasenja prozora
	public void refreshData() {
		KursModel km = (KursModel)this.table.getModel();
		km.fireTableDataChanged();
	}
}

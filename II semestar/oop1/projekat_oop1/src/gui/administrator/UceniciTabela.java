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

import citanjeIzFajla.UcenikMen;
import entiteti.Ucenici;
import modeli.UceniciModel;

public class UceniciTabela extends JFrame{

	private static final long serialVersionUID = -2985615605700122511L;

	private UcenikMen ucenikMen;
	
	protected JToolBar mainToolbar = new JToolBar();
	protected JButton izmeni = new JButton();
	protected JButton obrisi = new JButton();
	protected JTextField tfSearch = new JTextField(20);
	protected JTable table;
	protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();

	public UceniciTabela(JFrame parent, UcenikMen ucenikManadzer) {
		this.ucenikMen = ucenikManadzer;	
		// podesavanje prozora
		setTitle("Ucenici");	
		setSize(800, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);		
		setIconImage(new ImageIcon("./src/slike/icon.png").getImage());
		
		// podesavanje toolbar-a
		ImageIcon izmeniIkonica = new ImageIcon("./src/slike/edit.gif");
		izmeni.setIcon(izmeniIkonica);
		mainToolbar.add(izmeni);
		ImageIcon obrisiIkonica = new ImageIcon("./src/slike/remove.gif");
		obrisi.setIcon(obrisiIkonica);
		mainToolbar.add(obrisi);
		mainToolbar.setFloatable(false);		
		add(mainToolbar, BorderLayout.NORTH);
		
		// podesavanje tabele
		table = new JTable(new UceniciModel(ucenikManadzer));		
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		// podesavanje manuelnog sortera tabele, potrebno i za pretragu
		tableSorter.setModel((AbstractTableModel) table.getModel());
		table.setRowSorter(tableSorter);
		JScrollPane sc = new JScrollPane(table);
		add(sc, BorderLayout.CENTER);

		initActions();
	}
	
	

	private void initActions() {
		
		izmeni.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = table.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					String username = table.getValueAt(red, 6).toString();
					Ucenici u = ucenikMen.pronadjiUcenikaPoUsername(username);
					if(u != null) {
						UceniciEdit izmeni = new UceniciEdit(UceniciTabela.this, ucenikMen, u);
						izmeni.setVisible(true);
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabranog ucenika!", "Greska", JOptionPane.ERROR_MESSAGE);
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
					Ucenici u = ucenikMen.pronadjiUcenikaPoUsername(username);
					if(u != null) {
						int izbor = JOptionPane.showConfirmDialog(null,"Da li ste sigurni da zelite da obrisete ucenika?", 
								u.getIme() + " "+u.getPrezime() +" - Potvrda brisanja", JOptionPane.YES_NO_OPTION);
						if(izbor == JOptionPane.YES_OPTION) {
							ucenikMen.brisanje(u.getUsername());
							refreshData();
						}
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabranog ucenika!", "Greska", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
	}
	// potrebno osvezavanje podataka u tabeli bez gasenja prozora
	public void refreshData() {
		UceniciModel um = (UceniciModel)this.table.getModel();
		um.fireTableDataChanged();
	}
}

package gui.sekretar;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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

import citanjeIzFajla.KursMen;
import citanjeIzFajla.KursNoviMen;
import citanjeIzFajla.PredMenadzer;
import entiteti.Jezik;
import entiteti.Kurs;
import entiteti.Predavac;
import modeli.NoviKursModel;

public class PPTabela extends JFrame{

	private static final long serialVersionUID = 537554557601642422L;
	
	private KursMen kursMen;
	private PredMenadzer predMen;
	private KursNoviMen kursNoviMen;
	
	protected JToolBar mainToolbar = new JToolBar();
	protected JButton izmeni = new JButton();
	protected JTextField tfSearch = new JTextField(20);
	protected JTable table;
	protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();

	public PPTabela(JFrame parent, KursMen kursMen, PredMenadzer predMen, KursNoviMen kursNoviMen) {
		this.kursMen = kursMen;
		this.predMen = predMen;
		this.kursNoviMen = kursNoviMen;
		setTitle("Postavljanje predavaca");	
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
		table = new JTable(new NoviKursModel(kursNoviMen));		
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
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
					String id = table.getValueAt(red, 0).toString();
					Kurs k = kursMen.pronadjiKursPoId(id);
					Jezik j = k.getJezik();
					List<Predavac> dostupni = new ArrayList<Predavac>();
					for(Predavac p : predMen.getPredavaci()) {
						for(Jezik jezik : p.getJezici()) {
							if(j == jezik) {
								dostupni.add(p);
							}
						}
					}
					if(dostupni.size() == 0) {
						JOptionPane.showMessageDialog(null, "Ne postoji predavac koji predaje ovaj jezik", "Info", JOptionPane.INFORMATION_MESSAGE);
					}
					else if(k != null && dostupni.size() != 0) {
						PPEdit izmeni = new PPEdit(PPTabela.this, kursMen, predMen, kursNoviMen, k);
						izmeni.setVisible(true);
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabrani kurs!", "Greska", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
	}
	// potrebno osvezavanje podataka u tabeli bez gasenja prozora
	public void refreshData() {
		NoviKursModel nk = (NoviKursModel)this.table.getModel();
		nk.fireTableDataChanged();
	}
}

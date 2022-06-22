package gui.sekretar;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
//import java.util.ArrayList;
//import java.util.List;
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

import citanjeIzFajla.Menadzer;
import entiteti.Sekretar;
import entiteti.Zahtev;
import modeli.ZahteviRegModel;

public class ZahteviRegTabela extends JFrame{

	private static final long serialVersionUID = 7223860320975385581L;

	private Menadzer menadzer;
	private Sekretar s;
	private List<Zahtev> zahtevi = new ArrayList<Zahtev>();
	
	protected JToolBar mainToolbar = new JToolBar();
	protected JButton dodaj = new JButton();
	protected JButton izmeni = new JButton();
	protected JTextField tfSearch = new JTextField(20);
	protected JTable table;
	protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();

	public ZahteviRegTabela(JFrame parent, Menadzer mng, Sekretar s) {
		this.menadzer = mng;
		this.s = s;
		setTitle("Zahtevi za registraciju");	
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
		mainToolbar.add(izmeni);;
		mainToolbar.setFloatable(false);		
		add(mainToolbar, BorderLayout.NORTH);
		
		// podesavanje tabele
		for(Zahtev z : menadzer.getZahteviRegMen().getZahteviReg()) {
			if(z.getSekretar() == s && (z.getStatus().equals("u obradi") || z.getStatus().equals("prihvacen")) && !zahtevi.contains(z)) {
				zahtevi.add(z);
			}
		}
		if(!zahtevi.isEmpty()) {
			table = new JTable(new ZahteviRegModel(ZahteviRegTabela.this, menadzer, s));		
			table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.getTableHeader().setReorderingAllowed(false);
			tableSorter.setModel((AbstractTableModel) table.getModel());
			table.setRowSorter(tableSorter);
			JScrollPane sc = new JScrollPane(table);
			add(sc, BorderLayout.CENTER);
		}

		initActions();
	}

	private void initActions() {
		dodaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ZahteviRegEdit dodaj = new ZahteviRegEdit(ZahteviRegTabela.this, menadzer, s, true, null);
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
					Zahtev z = menadzer.getZahteviRegMen().pronadjiZahtevPoId(id);
					if(z.getStatus().equals("prihvacen")) {
						JOptionPane.showMessageDialog(null, "Zahtev je vec prihvacen!", "Info", JOptionPane.INFORMATION_MESSAGE);
					}
					if(z != null && z.getStatus().equals("u obradi")) {
						ZahteviRegEdit izmeni = new ZahteviRegEdit(ZahteviRegTabela.this, menadzer, s, false, z);
						izmeni.setVisible(true);
					}
				}
			}
		});
		
	}

	public void refreshData() {
		ZahteviRegModel zrm = (ZahteviRegModel)this.table.getModel();
		zrm.fireTableDataChanged();
		
	}
	
}

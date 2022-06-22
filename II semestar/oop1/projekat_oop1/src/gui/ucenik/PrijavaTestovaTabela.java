package gui.ucenik;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import entiteti.Test;
import entiteti.Ucenici;
import modeli.PrijavaTestovaModel;

public class PrijavaTestovaTabela extends JFrame{

	private static final long serialVersionUID = -8300939583536751582L;
	
	private JFrame parent;
	private Ucenici ucenik;
	private Menadzer menadzer;
	
	protected JToolBar mainToolbar = new JToolBar();
	protected JButton izmeni = new JButton();
	protected JTextField tfSearch = new JTextField(20);
	protected JTable table;
	protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();
	
	private JPanel panelBtns = new JPanel();
	private JButton prijavaBtn = new JButton("Prijavi test");
	
	public PrijavaTestovaTabela(JFrame parent, Menadzer menadzer, Ucenici ucenik, List<Test> testovi) {
		this.parent = parent;
		this.menadzer = menadzer;
		this.ucenik = ucenik;
		setTitle("Prijava testa");	
		setSize(800, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);		
		setIconImage(new ImageIcon("./src/slike/icon.png").getImage());
		
		// podesavanje tabele
		table = new JTable(new PrijavaTestovaModel(testovi));		
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		tableSorter.setModel((AbstractTableModel) table.getModel());
		table.setRowSorter(tableSorter);
		JScrollPane sc = new JScrollPane(table);
		add(sc, BorderLayout.CENTER);
		
		panelBtns.add(prijavaBtn);
		getContentPane().add(panelBtns, BorderLayout.SOUTH);
		
		initActions();
	}

	private void initActions() {
		prijavaBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = table.getSelectedRow();
				if(red == -1) 
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				else {
					String id = table.getValueAt(red, 0).toString();
					Test t = menadzer.getTestMen().pronadjiTestPoId(id);
					List<Test> testoviLista = ucenik.getTestovi();
					testoviLista.add(t);
					menadzer.getUcenikMen().izmenaTesta(ucenik.getUsername(), testoviLista);
					((MainUcenik) parent).refreshData();
					PrijavaTestovaTabela.this.dispose();
				}
			}
		});
		
	}
}

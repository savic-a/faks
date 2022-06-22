package gui.ucenik;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import citanjeIzFajla.Menadzer;
import entiteti.Ucenici;
import modeli.UceniciKurseviModel;

public class KurseviTabela extends JFrame{

	private static final long serialVersionUID = -742037618799704557L;
	
	private Ucenici ucenik;
	private Menadzer menadzer;
	
	protected JToolBar mainToolbar = new JToolBar();
	protected JButton izmeni = new JButton();
	protected JTextField tfSearch = new JTextField(20);
	protected JTable table;
	protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();
	
	private JPanel panelBtns = new JPanel();
	private JButton zahtevBtn = new JButton("Pošalji zahtev za kurs");
	
	public KurseviTabela(JFrame parent, Menadzer menadzer, Ucenici ucenik) {
		this.menadzer = menadzer;
		this.ucenik = ucenik;
		setTitle("Moji kursevi");	
		setSize(800, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);		
		setIconImage(new ImageIcon("./src/slike/icon.png").getImage());
		
		// podesavanje tabele
		table = new JTable(new UceniciKurseviModel(ucenik));		
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		tableSorter.setModel((AbstractTableModel) table.getModel());
		table.setRowSorter(tableSorter);
		JScrollPane sc = new JScrollPane(table);
		add(sc, BorderLayout.CENTER);
		
		panelBtns.add(zahtevBtn);
		getContentPane().add(panelBtns, BorderLayout.SOUTH);
		
		initActions();
	}

	private void initActions() {
		zahtevBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ZahteviTabela zt = new ZahteviTabela(KurseviTabela.this, menadzer, ucenik);
				zt.setVisible(true);
				
			}
		});
		
	}

}

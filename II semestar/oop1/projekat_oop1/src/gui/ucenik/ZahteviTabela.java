package gui.ucenik;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import entiteti.Kurs;
import entiteti.Sekretar;
import entiteti.Ucenici;
import entiteti.Zahtev;
import modeli.UceniciZahtevModel;

public class ZahteviTabela extends JFrame{

	private static final long serialVersionUID = 7032788984752398231L;
	
	private Ucenici ucenik;
	private Menadzer menadzer;
	private List<Kurs> kursevi;
	
	protected JToolBar mainToolbar = new JToolBar();
	protected JButton izmeni = new JButton();
	protected JTextField tfSearch = new JTextField(20);
	protected JTable table;
	protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();
	
	private JPanel panelBtns = new JPanel();
	private JButton zahtevBtn = new JButton("Pošalji zahtev");
	
	public ZahteviTabela(JFrame parent, Menadzer menadzer, Ucenici ucenik) {
		this.menadzer = menadzer;
		this.ucenik = ucenik;
		this.kursevi = new ArrayList<Kurs>();
		setTitle("Dostupni kursevi");	
		setSize(800, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);		
		setIconImage(new ImageIcon("./src/slike/icon.png").getImage());
		
		for(Kurs k : menadzer.getKursMen().getKursevi()) {
			if(!ucenik.getKursevi().contains(k) && !k.getPredavac().getUsername().equals(""))
				kursevi.add(k);
		}
		for(Zahtev z : menadzer.getZahteviKursMen().getZahteviKurs()) {
			for(Kurs kurs : z.getKursevi()) {
				System.out.println(z.getUcenik());
				if(kursevi.contains(kurs) && ucenik == z.getUcenik()) {
					kursevi.remove(kurs);
				}
			}
		}
		if(!kursevi.isEmpty()) {
			// podesavanje tabele
			table = new JTable(new UceniciZahtevModel(menadzer, ucenik));		
			table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.getTableHeader().setReorderingAllowed(false);
			tableSorter.setModel((AbstractTableModel) table.getModel());
			table.setRowSorter(tableSorter);
			JScrollPane sc = new JScrollPane(table);
			add(sc, BorderLayout.CENTER);
			
			panelBtns.add(zahtevBtn);
			getContentPane().add(panelBtns, BorderLayout.SOUTH);
		}
		
		else {
			JOptionPane.showMessageDialog(null, "Ne postoji kurs za koji možete da pošaljete zahtev", "Info", JOptionPane.INFORMATION_MESSAGE);
		}
		
		initActions();
	}

	private void initActions() {
		zahtevBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!kursevi.isEmpty()) {
					int red = table.getSelectedRow();
					if(red == -1) 
						JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
					else {
						String id = table.getValueAt(red, 0).toString();
						Kurs k = menadzer.getKursMen().pronadjiKursPoId(id);
						List<Kurs> kurs = new ArrayList<Kurs>();
						kurs.add(k);
						int intId = menadzer.getZahteviKursMen().getZahteviKurs().size() + 1;
						Zahtev z = menadzer.getZahteviKursMen().pronadjiZahtevPoId(id);
						while(z != null) {
							intId ++;
							id = Integer.toString(intId);
							z = menadzer.getZahteviRegMen().pronadjiZahtevPoId(id);
						}
						Sekretar sekretar = new Sekretar();
						menadzer.getZahteviKursMen().dodaj(id, ucenik, sekretar, kurs, "kreiran");
						ZahteviTabela.this.dispose();
					}
				}
			}
		});
		
	}

}

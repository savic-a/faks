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

import citanjeIzFajla.SekMenadzer;
import entiteti.Sekretar;
import modeli.SekretarModel;

public class SekretariTabela extends JFrame{

	private static final long serialVersionUID = -5973703481622181275L;
	
	private SekMenadzer sekretarMen;
	
	protected JToolBar mainToolbar = new JToolBar();
	protected JButton dodaj = new JButton();
	protected JButton izmeni = new JButton();
	protected JButton obrisi = new JButton();
	protected JTextField tfSearch = new JTextField(20);
	protected JTable table;
	protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();
//	private JFrame parentFrame;

	public SekretariTabela(JFrame parent, SekMenadzer sekretarManadzer) {
		this.sekretarMen = sekretarManadzer;
//		this.parentFrame = parent;	
		// podesavanje prozora
		setTitle("Sekretari");	
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
		table = new JTable(new SekretarModel(sekretarManadzer));		
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
//				sort(index);
//			}
//			
//		});

		initActions();
	}
	
	

	private void initActions() {
		dodaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SekretarEdit dodaj = new SekretarEdit(SekretariTabela.this, sekretarMen, null);
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
					Sekretar s = sekretarMen.pronadjiSekretaraPoUsername(username);
					System.out.println(username);
					if(s != null) {
						SekretarEdit izmeni = new SekretarEdit(SekretariTabela.this, sekretarMen, s);
						izmeni.setVisible(true);
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabranog sekretara!", "Greska", JOptionPane.ERROR_MESSAGE);
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
					Sekretar s = sekretarMen.pronadjiSekretaraPoUsername(username);
					if(s != null) {
						int izbor = JOptionPane.showConfirmDialog(null,"Da li ste sigurni da zelite da obrisete sekretara?", 
								s.getIme() + " "+s.getPrezime() +" - Potvrda brisanja", JOptionPane.YES_NO_OPTION);
						if(izbor == JOptionPane.YES_OPTION) {
							sekretarMen.brisanje(s.getUsername());
							refreshData();
						}
					}else {
						JOptionPane.showMessageDialog(null, "Nije moguce pronaci odabranog sekretara!", "Greska", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
	}
	// potrebno osvezavanje podataka u tabeli bez gasenja prozora
	public void refreshData() {
		SekretarModel sm = (SekretarModel)this.table.getModel();
		sm.fireTableDataChanged();
	}
	
	// Pamcenje redosleda sortiranja za svaku kolonu posebno - primer
//	@SuppressWarnings("serial")
//	private Map<Integer, Integer> sortOrder = new HashMap<Integer, Integer>() {{put(0, 1);put(1, 1);put(2, 1);put(3, 1);}};
//
//	// Manuelni sorter - potrebno za razumevanje rada podrazumevanog sortera tabele
//	protected void sort(int index) {
//		// index of table column
//		
//		this.studentMng.getStudenti().sort(new Comparator<Student>() {
//			int retVal = 0;
//			@Override
//			public int compare(Student o1, Student o2) {
//				switch (index) {
//				case 0:
//					retVal = ((Integer)o1.getId()).compareTo((Integer)o2.getId());
//					break;
//				case 1:
//					retVal = o1.getIme().compareTo(o2.getIme());
//					break;
//				case 2:
//					retVal = o1.getPrezime().compareTo(o2.getPrezime());
//					break;
//				case 3:
//					retVal = o1.getIndeks().compareTo(o2.getIndeks());
//					break;
//				default:
//					System.out.println("Prosirena tabela");
//					System.exit(1);
//					break;
//				}
//				return retVal*sortOrder.get(index);
//			}
//		});
//		
//		System.out.println("kolona "+index+" smer "+sortOrder.get(index));
//		sortOrder.put(index, sortOrder.get(index)*-1);
//		refreshData();	
//		
//	}

}

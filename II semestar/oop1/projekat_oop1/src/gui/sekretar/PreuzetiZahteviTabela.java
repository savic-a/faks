package gui.sekretar;

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
import entiteti.Sekretar;
import entiteti.Zahtev;
import modeli.PreuzetiZahteviModel;

public class PreuzetiZahteviTabela extends JFrame{

	private static final long serialVersionUID = -8971281352773244224L;
	
	private Menadzer menadzer;
	
	protected JToolBar mainToolbar = new JToolBar();
	protected JButton dodaj = new JButton();
	protected JButton izmeni = new JButton();
	protected JTextField tfSearch = new JTextField(20);
	protected JTable table;
	protected TableRowSorter<AbstractTableModel> tableSorter = new TableRowSorter<AbstractTableModel>();

	public PreuzetiZahteviTabela(JFrame parent, Menadzer mng, Sekretar s) {
		this.menadzer = mng;
		setTitle("Preuzeti zahtevi");	
		setSize(800, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);		
		setIconImage(new ImageIcon("./src/slike/icon.png").getImage());
		
		// podesavanje toolbar-a
		ImageIcon izmeniIkonica = new ImageIcon("./src/slike/edit.gif");
		izmeni.setIcon(izmeniIkonica);
		mainToolbar.add(izmeni);;
		mainToolbar.setFloatable(false);		
		add(mainToolbar, BorderLayout.NORTH);
		
		// podesavanje tabele
		table = new JTable(new PreuzetiZahteviModel(menadzer.getZahteviKursMen(), s));		
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
					Zahtev z = menadzer.getZahteviKursMen().pronadjiZahtevPoId(id);
					if(z.getStatus().equals("prihvacen")) {
						JOptionPane.showMessageDialog(null, "Zahtev je vec prihvacen!", "Info", JOptionPane.INFORMATION_MESSAGE);
					}
					if(z != null && z.getStatus().equals("u obradi")) {
						PreuzetiZahtevEdit izmeni = new PreuzetiZahtevEdit(PreuzetiZahteviTabela.this, menadzer, z);
						izmeni.setVisible(true);
					}
				}
			}
		});
		
	}

	public void refreshData() {
		PreuzetiZahteviModel pzm = (PreuzetiZahteviModel)this.table.getModel();
		pzm.fireTableDataChanged();
		
	}

}

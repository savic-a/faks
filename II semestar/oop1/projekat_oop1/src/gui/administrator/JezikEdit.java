package gui.administrator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import citanjeIzFajla.JeziciMenadzer;
import entiteti.Jezik;
import net.miginfocom.swing.MigLayout;

public class JezikEdit extends JDialog{

	private static final long serialVersionUID = 4928242808253008745L;
	
	private JeziciMenadzer jezMen;
	private Jezik editJ;
	private JFrame parent;
	
	JLabel lblId = new JLabel("ID");
	JTextField txtId = new JTextField(15);

	JLabel lblNaziv = new JLabel("Naziv");
	JTextField txtNaziv = new JTextField(15);
	
	// Jedan isti dijalog za Add i Edit
	public JezikEdit(JFrame parent, JeziciMenadzer jezMen, Jezik editJezik) {
		super(parent, true);
		this.parent = parent;
		if (editJezik != null) {
			setTitle("Izmena jezika");
		} else {
			setTitle("Dodavanje jezika");
		}
		this.jezMen = jezMen;
		this.editJ = editJezik;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		initGUI();
		pack();
	}

	private void initGUI() {
		MigLayout ml = new MigLayout("wrap 3", "[][][]", "[]10[]10[]");
		setLayout(ml);

		add(lblId);
		add(txtId, "span 2");
		add(lblNaziv);
		add(txtNaziv, "span 2");
		
		add(new JLabel());

		JButton btnOK = new JButton("OK");
		add(btnOK);
		
		JButton btnCancel = new JButton("Otkaži");
		add(btnCancel);


		if (editJ != null) {
			// popuni polja vrednostima
			txtId.setText(editJ.getId() + "");
			txtNaziv.setText(editJ.getNaziv());
		}
	

		btnOK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String id = txtId.getText().trim();
				String naziv = txtNaziv.getText().trim();
				
				if (editJ != null) {
					if(!editJ.getId().equals(id)) {
						JOptionPane.showMessageDialog(null, "Ne mozete da menjate ID!", "Greska", JOptionPane.WARNING_MESSAGE);
					}
					else if(validacija()) {
						jezMen.izmena(editJ.getId(), naziv);
						((JezikTabela) parent).refreshData();
						JezikEdit.this.dispose();}
			
				}else {
					
					Jezik j = jezMen.pronadjiJezikPoId(txtId.getText().trim());
					
					if(j != null) {
						JOptionPane.showMessageDialog(null, "Uneli ste postojeci ID!", "Greska", JOptionPane.WARNING_MESSAGE);
					}
					else if(validacija()) {
						jezMen.dodaj(id, naziv);
						((JezikTabela) parent).refreshData();
						JezikEdit.this.dispose();
					}
				}
			}
		});


		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JezikEdit.this.dispose();
			};
		});
	}

	
	
	private boolean validacija() {
		boolean ok = true;
		String poruka = "Molim Vas ispravite sledeće podatke:\n";
		
		if(txtId.getText().trim().equals("")) {
			poruka += "Unesite ID\n";
			ok = false;
		}
		if(txtNaziv.getText().trim().equals("")) {
			poruka += "Unesite naziv\n";
			ok = false;
		}
		if(ok == false) {
			JOptionPane.showMessageDialog(null, poruka, "Neispravni podaci", JOptionPane.WARNING_MESSAGE);
		}
		return ok;
		}
}

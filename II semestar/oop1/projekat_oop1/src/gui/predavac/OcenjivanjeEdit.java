package gui.predavac;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import citanjeIzFajla.Menadzer;
import entiteti.Test;
import entiteti.Ucenici;
import net.miginfocom.swing.MigLayout;

public class OcenjivanjeEdit extends JDialog{

	private static final long serialVersionUID = -8121137310069603499L;
	
	private JFrame parent;
	private Menadzer menadzer;
	private Test test;
	
	JLabel lblUcenici = new JLabel("Ucenik");	
	JComboBox<?> cbUcenici;
	
	JLabel lblPoeni = new JLabel("Poeni");
	JTextField txtPoeni = new JTextField(15);

	JLabel lblOcena = new JLabel("Ocena");
	JTextField txtOcena = new JTextField(15);
	
	
	public OcenjivanjeEdit(JFrame parent, Menadzer menadzer, List<String> ucenici, Test test) {
		super(parent, true);
		this.parent = parent;
		this.menadzer = menadzer;
		this.test = test;
		this.cbUcenici = new JComboBox<>(ucenici.toArray());
		
		setTitle("Ocenjivanje");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		initGUI();
		pack();
	}


	private void initGUI() {
		MigLayout ml = new MigLayout("wrap 3", "[][][]", "[][][]20[]");
		setLayout(ml);

		add(lblUcenici);
		add(cbUcenici, "span 2");
		add(lblPoeni);
		add(txtPoeni, "span 2");
		add(lblOcena);
		add(txtOcena, "span 2");
		
		add(new JLabel());

		JButton btnOK = new JButton("OK");
		add(btnOK);
		
		JButton btnCancel = new JButton("Otkaži");
		add(btnCancel);
		
		btnOK.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(validacija()) {
					String username = (String)cbUcenici.getSelectedItem();
					double poeni = Double.parseDouble(txtPoeni.getText().trim());
					int ocena = Integer.parseInt(txtOcena.getText().trim());
					
					int idInt = menadzer.getRezMen().getRezultati().size() + 1;
					String id = Integer.toString(idInt);
					Ucenici ucenik = menadzer.getUcenikMen().pronadjiUcenikaPoUsername(username);
					
					String polozio = "";
					if(poeni > 51)
						polozio = "polozio";
					else 
						polozio = "pao";
					menadzer.getRezMen().dodaj(id, test, ucenik, poeni, ocena, polozio);
					((TestoviTabela) parent).refreshData();
					OcenjivanjeEdit.this.dispose();	
				}
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				OcenjivanjeEdit.this.dispose();
			}
		});
	
		}
	
	private boolean validacija() {
		boolean ok = true;
		String poruka = "Molim Vas ispravite sledeće podatke:\n";
		
		if((txtOcena.getText().trim().equals("") || !validanUnosBroja(txtOcena.getText()) || (!txtOcena.getText().trim().equals("1") && txtOcena.getText().trim().equals("2") && txtOcena.getText().trim().equals("3") && txtOcena.getText().trim().equals("4") && txtOcena.getText().trim().equals("5")))) {
			poruka += "Ocenu unesite cifrom od 1 do 5\n";
			ok = false;
		}
		if(txtPoeni.getText().trim().equals("") || !validanUnosBroja(txtPoeni.getText()) || 0>Double.parseDouble(txtPoeni.getText().trim()) || Double.parseDouble(txtPoeni.getText().trim())>100) {
			poruka += "Poene unesite brojevima između 1 i 100\n";
			ok = false;
		}
		if(ok == false) {
			JOptionPane.showMessageDialog(null, poruka, "Neispravni podaci", JOptionPane.WARNING_MESSAGE);
		}
		return ok;
	}

	private boolean validanUnosBroja(String broj) {
		boolean rez = true;
		try {
            Double.parseDouble(broj);
        } catch (NumberFormatException e) {
            rez = false;
        }
		if(!rez) {
			for(int i=0; i<broj.length(); i++)
			{
				if(i==0 && broj.charAt(i) == '-')
					continue;
				if(!Character.isDigit(broj.charAt(i)))
					rez = false;          	   
			}
		}
		return rez;
	}
	
	
}

package gui.predavac;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import citanjeIzFajla.Menadzer;
import entiteti.Test;
import net.miginfocom.swing.MigLayout;

public class IzmenaTestaEdit extends JDialog{

	private static final long serialVersionUID = 7303110615105797086L;
	
	private JFrame parent;
	private Menadzer menadzer;
	private Test editT;
	
	JLabel lblId = new JLabel("ID");
	JTextField txtId = new JTextField(15);

	JLabel lblNaziv = new JLabel("Naziv");
	JTextField txtNaziv = new JTextField(15);

	JLabel lblBrUci = new JLabel("Broj ucionice");
	JTextField txtBrUci = new JTextField(15);
	
	JLabel lblDatum = new JLabel("Datum");
	JTextField txtDatum = new JTextField(15);
	
	public IzmenaTestaEdit(JFrame parent, Menadzer menadzer, Test editT) {
		super(parent, true);
		this.parent = parent;
		this.menadzer = menadzer;
		this.editT = editT;
		
		setTitle("Izmena testa");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		initGUI();
		pack();
	}

	private void initGUI() {
		MigLayout ml = new MigLayout("wrap 3", "[][][]", "[][][][]20[]");
		setLayout(ml);

		add(lblId);
		add(txtId, "span 2");
		add(lblNaziv);
		add(txtNaziv, "span 2");
		add(lblBrUci);
		add(txtBrUci, "span 2");
		add(lblDatum);
		add(txtDatum, "span 2");
		
		add(new JLabel());

		JButton btnOK = new JButton("OK");
		add(btnOK);
		
		JButton btnCancel = new JButton("Otkaži");
		add(btnCancel);

		txtId.setText(editT.getId() + "");
		txtNaziv.setText(editT.getNaziv());
		txtBrUci.setText(editT.getBrUcionice());
		String datum = editT.getDatum().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		txtDatum.setText(datum);

		btnOK.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(validacija()) {
					String id = txtId.getText().trim();
					String naziv = txtNaziv.getText().trim();
					String brUcionice = txtBrUci.getText().trim();
					String datumStr = txtDatum.getText().trim();
					DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					LocalDate datum = LocalDate.parse(datumStr, format);
					
					boolean moze = true;
					if(!editT.getId().equals(id)) {
						JOptionPane.showMessageDialog(null, "Ne mozete da menjate ID!", "Greska", JOptionPane.WARNING_MESSAGE);
						moze = false;
					}
//					else if(!datum.isAfter(LocalDate.now())) {
//						JOptionPane.showMessageDialog(null, "Unesite datum koji nije prosao!", "Greska", JOptionPane.WARNING_MESSAGE);
//						moze = false;
//					}
					else if(moze) {
						boolean ok = true;
						for(Test t : menadzer.getTestMen().getTestovi()) {
							if(t != editT && t.getKurs().equals(editT.getKurs()) && datum.isAfter(LocalDate.now()) && t.getDatum().isAfter(LocalDate.now())) {
								JOptionPane.showMessageDialog(null, "Ne mozete da menjate datum izabranog testa jer vec postoji jedan aktivan test!", "Greska", JOptionPane.WARNING_MESSAGE);
								ok = false;
								break;
							}
						}
						if(ok) {
							menadzer.getTestMen().izmena(id, naziv, editT.getJezik(), editT.getKurs(), editT.getPredavac(), brUcionice, datum);
							((TestoviTabela) parent).refreshData();
							IzmenaTestaEdit.this.dispose();
						}
					}
				}	
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				IzmenaTestaEdit.this.dispose();
			}
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
			poruka += "Unesite naziv testa\n";
			ok = false;
		}
		if(txtBrUci.getText().trim().equals("") || !validanUnosBroja(txtBrUci.getText())) {
			poruka += "Broj ucionice unesite ciframa\n";
			ok = false;
		}
		if(txtDatum.getText().trim().equals("") || !validacijaDatuma(txtDatum.getText())) {
			poruka += "Unesite datum u fomatu dd/MM/yyyy\n";
			ok = false;
		}
		if(ok == false) {
			JOptionPane.showMessageDialog(null, poruka, "Neispravni podaci", JOptionPane.WARNING_MESSAGE);
		}
		return ok;
	}

	private boolean validacijaDatuma(String datum) {
		boolean validan = false;
		try {
			LocalDate.parse(datum, DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT));
			validan = true;
		} catch(DateTimeParseException e) {
            validan = false;
        } catch(DateTimeException e) {
            validan = false;
        }
		return validan;
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

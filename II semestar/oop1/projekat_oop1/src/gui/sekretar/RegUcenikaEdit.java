package gui.sekretar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import citanjeIzFajla.Menadzer;
import entiteti.Pol;
import entiteti.Sekretar;
import entiteti.Zahtev;
import net.miginfocom.swing.MigLayout;

public class RegUcenikaEdit extends JDialog{

	private static final long serialVersionUID = 4998003127469190781L;
	
	private Menadzer menadzer;
	private Sekretar sekretar;
	
	JLabel lblIme = new JLabel("Ime");
	JTextField txtIme = new JTextField(15);

	JLabel lblPrezime = new JLabel("Prezime");
	JTextField txtPrezime = new JTextField(15);

	JLabel lblPol = new JLabel("Pol");
	JComboBox<Pol> comboBox = new JComboBox<Pol>(Pol.values());

	JLabel lblDatum = new JLabel("Datum");
	JTextField txtDatum = new JTextField(15);
	
	JLabel lblTel = new JLabel("Telefon");
	JTextField txtTel = new JTextField(15);

	JLabel lblAdresa = new JLabel("Adresa");
	JTextField txtAdresa = new JTextField(15);
	
	JLabel lblUsername = new JLabel("Korisničko ime");
	JTextField txtUsername = new JTextField(15);

	JLabel lblLozinka = new JLabel("Lozinka");
	JPasswordField txtLozinka = new JPasswordField(15);
	
	JButton btnOK = new JButton("OK");
	JButton btnCancel = new JButton("Otkaži");

	
	public RegUcenikaEdit(JFrame parent, Menadzer mng, Sekretar s) {
		super(parent, true);
		setTitle("Zahtev za registraciju ucenika");
		
		this.menadzer = mng;
		this.sekretar = s;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		initGUI();
		pack();
	}

	private void initGUI() {
		MigLayout ml = new MigLayout("wrap 3", "[][][]", "[][][][][][][][]20[]");
		setLayout(ml);
		
		add(lblIme);
		add(txtIme, "span 2");
		add(lblPrezime);
		add(txtPrezime, "span 2");
		add(lblPol);
		add(comboBox, "span 2");
		add(lblDatum);
		add(txtDatum, "span 2");
		add(lblTel);
		add(txtTel, "span 2");
		add(lblAdresa);
		add(txtAdresa, "span 2");
		add(lblUsername);
		add(txtUsername, "span 2");
		add(lblLozinka);
		add(txtLozinka, "span 2");
		add(new JLabel());

		add(btnOK);
		add(btnCancel);
		
		btnOK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(validacija()) {
					String ime = txtIme.getText().trim();
					String prezime = txtPrezime.getText().trim();
					Pol pol = (Pol)comboBox.getSelectedItem();
					String datum = txtDatum.getText().trim();
					String tel = txtTel.getText().trim();
					String adresa = txtAdresa.getText().trim();
					String username = txtUsername.getText().trim();
					String lozinka = String.valueOf(txtLozinka.getPassword()).trim();
				
					DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					LocalDate date = LocalDate.parse(datum,format);
					
					if(menadzer.getUcenikMen().pronadjiUcenikaPoUsername(username) != null)
							JOptionPane.showMessageDialog(null, "Uneli ste postojece korisnicko ime!", "Greska", JOptionPane.WARNING_MESSAGE);
					
					else if(validacija()) {
						int intId = menadzer.getZahteviRegMen().getZahteviReg().size() + 1;
						String id = Integer.toString(intId);
						Zahtev z = menadzer.getZahteviRegMen().pronadjiZahtevPoId(id);
						while(z != null) {
							intId ++;
							id = Integer.toString(intId);
							z = menadzer.getZahteviRegMen().pronadjiZahtevPoId(id);
						}
						menadzer.getZahteviRegMen().dodaj(id, ime, prezime, pol, date, tel, adresa, username, lozinka, sekretar, "u obradi");
						RegUcenikaEdit.this.dispose();
					}
				}
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				RegUcenikaEdit.this.dispose();
			}
		});
	}
	private boolean validacija() {
		boolean ok = true;
		String poruka = "Molim Vas ispravite sledeće podatke:\n";
		
		if(txtIme.getText().trim().equals("")) {
			poruka += "Unesite ime\n";
			ok = false;
		}
		if(txtPrezime.getText().trim().equals("")) {
			poruka += "Unesite prezime\n";
			ok = false;
		}
		if(txtDatum.getText().trim().equals("") || !validanDatum(txtDatum.getText())) {
			poruka += "Unesite datum u fomatu dd/MM/yyyy\n";
			ok = false;
		}
		if(txtTel.getText().trim().equals("") || !validanUnosBroja(txtTel.getText())) {
			poruka += "Pri unosu broja telefona unesite samo cifre\n";
			ok = false;
		}
		if(txtAdresa.getText().trim().equals("")) {
			poruka += "Unesite adresu\n";
			ok = false;
		}
		if(ok == false) {
			JOptionPane.showMessageDialog(null, poruka, "Neispravni podaci", JOptionPane.WARNING_MESSAGE);
		}
		return ok;
		}
	

	public boolean validanDatum(String datum) {
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
	
	public boolean validanUnosBroja(String broj) {
		boolean rez = true;
		for(int i=0; i<broj.length(); i++)
		{
			if(i==0 && broj.charAt(i) == '-')
				continue;
			if(!Character.isDigit(broj.charAt(i)))
				rez=false;          	   
		}
		return rez;
		}
	
	}

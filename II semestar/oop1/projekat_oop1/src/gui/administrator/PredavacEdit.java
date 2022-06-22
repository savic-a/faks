package gui.administrator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import citanjeIzFajla.JeziciMenadzer;
import citanjeIzFajla.KursMen;
import citanjeIzFajla.KursNoviMen;
import citanjeIzFajla.Menadzer;
import citanjeIzFajla.PredMenadzer;
import entiteti.Jezik;
import entiteti.Kurs;
import entiteti.Pol;
import entiteti.Predavac;
import net.miginfocom.swing.MigLayout;

public class PredavacEdit extends JDialog{

	private static final long serialVersionUID = -2352266465417484667L;
	
	private PredMenadzer predMen;
	private JeziciMenadzer jezMen;
	private KursMen kursMen;
	private KursNoviMen noviKursMen;
	private Predavac editP;
	private JFrame parent;
	private List<Jezik> jezici;
	private List<JCheckBox> cbLista;
	
	
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

	JLabel lblNss = new JLabel("Nivo spreme");
	JTextField txtNss = new JTextField(15);
	
	JLabel lblStaz = new JLabel("Godine staža");
	JTextField txtStaz = new JTextField(15);
	
	JLabel lblJezici = new JLabel("Jezici");
	
	// Jedan isti dijalog za Add i Edit
	public PredavacEdit(JFrame parent, Menadzer mng, Predavac editPredavac) {
		super(parent, true);
		this.parent = parent;
		if (editPredavac != null) {
			setTitle("Izmena predavača");
		} else {
			setTitle("Dodavanje predavača");
		}
		this.predMen = mng.getPredMen();
		this.jezMen = mng.getJezMen();
		this.kursMen = mng.getKursMen();
		this.noviKursMen = mng.getKursNoviMen();
		this.editP = editPredavac;
		this.jezici = new ArrayList<Jezik>();
		this.cbLista = new ArrayList<JCheckBox>();

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		initGUI();
		pack();
	}

	private void initGUI() {
		MigLayout ml = new MigLayout("wrap 3", "[][][]", "[][][][][][][][][][][]20[]");
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
		add(lblNss);
		add(txtNss, "span 2");
		add(lblStaz);
		add(txtStaz, "span 2");
		add(lblJezici);
		
		for(Jezik j : jezMen.getJezici()) {
			JCheckBox cb = new JCheckBox(j.getNaziv());
			if(editP != null && !editP.getJezici().contains(null)) {
				if(editP.getJezici().contains(j))
					cb.setSelected(true);
			}
			cbLista.add(cb);
			add(cb, "span 2");
			add(new JLabel());
		}


		JButton btnOK = new JButton("OK");
		add(btnOK);
		
		JButton btnCancel = new JButton("Otkaži");
		add(btnCancel);

		if (editP != null) {
			// popuni polja vrednostima
			txtIme.setText(editP.getIme() + "");
			txtPrezime.setText(editP.getPrezime());
			comboBox.setSelectedItem(editP.getPol());
			String datum = editP.getDatum().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			txtDatum.setText(datum);
			txtTel.setText(editP.getTel());
			txtAdresa.setText(editP.getAdresa());
			txtUsername.setText(editP.getUsername());
			txtLozinka.setText(editP.getLozinka());
			txtNss.setText(editP.getNivoSpreme());
			txtStaz.setText(editP.getStaz());
		}

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
				String nss = txtNss.getText().trim();
				String staz = txtStaz.getText().trim();
				
				DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				LocalDate date = LocalDate.parse(datum,format);
				
				// za proveru
				for(JCheckBox cb : cbLista) {
					if(cb.isSelected())
						jezici.add(jezMen.pronadjiJezikPoNazivu(cb.getText()));
				}
				
				if (editP != null) {
					for(Jezik j : editP.getJezici()) {
						if(!jezici.contains(j)) {
							for(Kurs k : kursMen.getKursevi()) {
								if(editP == k.getPredavac()) {
									Predavac p = new Predavac();
									kursMen.izmena(k.getId(), k.getNaziv(), k.getJezik(), k.getCenaKursa(), k.getCenaTesta(), p, k.getBrUcenika(), k.getTestovi());
									noviKursMen.dodaj(k.getId(), k.getNaziv(), k.getJezik(), k.getCenaKursa(), k.getCenaTesta());
								}
							}
						}
					}
					
					if(!editP.getUsername().equals(username)) {
						JOptionPane.showMessageDialog(null, "Ne mozete da menjate korisnicko ime!", "Greska", JOptionPane.WARNING_MESSAGE);
					}
					else if(jezici.size() == 0)
						JOptionPane.showMessageDialog(null, "Predavacu morate da dodelite jezik!", "Greska", JOptionPane.WARNING_MESSAGE);
					else if(validacija()) {
						predMen.izmena(ime, prezime, pol, date, tel, adresa, editP.getUsername(), lozinka, nss, staz, jezici);
						((PredavaciTabela) parent).refreshData();
						PredavacEdit.this.dispose();}
					
			
				}else {
					Predavac p = predMen.PronadjiPredavacaPoUsername(username);
					if(p != null) {
						JOptionPane.showMessageDialog(null, "Uneli ste postojece korisnicko ime!", "Greska", JOptionPane.WARNING_MESSAGE);
					}
					else if(jezici.size() == 0)
						JOptionPane.showMessageDialog(null, "Predavacu morate da dodelite jezik!", "Greska", JOptionPane.WARNING_MESSAGE);
					else if(validacija()) {
						predMen.dodaj(ime, prezime, pol, date, tel, adresa, username, lozinka, nss, staz, jezici);
						((PredavaciTabela) parent).refreshData();
						PredavacEdit.this.dispose();}
					}
				}
			}
		});

		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PredavacEdit.this.dispose();
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
		if(txtUsername.getText().trim().equals("")) {
			poruka += "Unesite korisničko ime\n";
			ok = false;
		}
		
		String lozinka = new String(txtLozinka.getPassword()).trim();
		if(lozinka.equals("")) {
			poruka += "Unesite lozinku\n";
			ok = false;
		}
		if(txtStaz.getText().trim().equals("") || !validanUnosBroja(txtStaz.getText())) {
			poruka += "Pri unosu godine staža unesite samo cifre\n";
			ok = false;
		}
		if(txtNss.getText().trim().equals("") || !validanUnosBroja(txtNss.getText()) || 1>Integer.parseInt(txtNss.getText().trim()) || Integer.parseInt(txtNss.getText().trim())>8) {
			poruka += "Pri unosu nivoa stručne smene unesite samo cifre od 1 do 8\n";
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

package gui.predavac;

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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import citanjeIzFajla.Menadzer;
import entiteti.Jezik;
import entiteti.Kurs;
import entiteti.Predavac;
import entiteti.Test;
import net.miginfocom.swing.MigLayout;

public class KreirajTest extends JDialog{

	private static final long serialVersionUID = 4307065533035438062L;
	
	private Menadzer menadzer;
	private Predavac predavac;
	private List<String> kursevi = new ArrayList<String>();
	private List<String> jezici = new ArrayList<String>();
	
	JLabel lblNaziv = new JLabel("Naziv");
	JTextField txtNaziv = new JTextField(15);
	
	JLabel lblKurs = new JLabel("Kurs");	
	JComboBox<?> cbKurs;
	
	JLabel lblJezik = new JLabel("Jezik");	
	JComboBox<?> cbJezik;
	
	JLabel lblBrUcionice = new JLabel("Broj ucionice");
	JTextField txtBrUci = new JTextField(15);
	
	JLabel lblDatum = new JLabel("Datum");
	JTextField txtDatum = new JTextField(15);
	
	
	public KreirajTest(JFrame parent, Menadzer menadzer, Predavac p) {
		super(parent, true);
		this.menadzer = menadzer;
		this.predavac = p;
		this.kursevi = pronadjiKurseve();
		this.cbKurs = new JComboBox<>(kursevi.toArray());
		this.jezici = pronadjiJezike();
		this.cbJezik = new JComboBox<>(jezici.toArray());
		
		setTitle("Kreiranje testa");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		initGUI();
		pack();
	}


	private List<String> pronadjiJezike() {
		for(Predavac p : menadzer.getPredMen().getPredavaci()) {
			if(p == predavac) {
				for(Jezik j : p.getJezici()) {
					jezici.add(j.getNaziv());
				}
			} 
				
		}return jezici;
	}


	private List<String> pronadjiKurseve() {
		for(Kurs k : menadzer.getKursMen().getKursevi()) {
			if(k.getPredavac() == predavac) {
				kursevi.add(k.getNaziv());
			}
		}return kursevi;
	}


	private void initGUI() {
		MigLayout ml = new MigLayout("wrap 3", "[][][]", "[][][][][][]20[]");
		setLayout(ml);
		
		add(lblNaziv);
		add(txtNaziv, "span 2");
		
		add(lblKurs);
		add(cbKurs, "span 2");
		
		add(lblJezik);
		add(cbJezik, "span 2");
		
		add(lblBrUcionice);
		add(txtBrUci, "span 2");
		
		add(lblDatum);
		add(txtDatum, "span 2");
		
		add(new JLabel());

		JButton btnOK = new JButton("OK");
		add(btnOK);
		
		JButton btnCancel = new JButton("Otkaži");
		add(btnCancel);
		
		btnOK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(validacija()) {
					int intId = menadzer.getTestMen().getTestovi().size() + 1;
					String id = Integer.toString(intId);
					String naziv = txtNaziv.getText().trim();
					String kurs = cbKurs.getSelectedItem().toString();
					String jezikStr = cbJezik.getSelectedItem().toString();
					Jezik jezik = menadzer.getJezMen().pronadjiJezikPoNazivu(jezikStr);
					String brUcionice = txtBrUci.getText();
					String datumStr = txtDatum.getText();
					DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					LocalDate datum = LocalDate.parse(datumStr, format);
					boolean moze = true;
					for(Test t : menadzer.getTestMen().getTestovi()) {
						if(t.getKurs().equals(kurs) && t.getDatum().isAfter(LocalDate.now())) {
							JOptionPane.showMessageDialog(null, "Ne mozete da kreirate test za ovaj kurs, jer vec postoji!", "Info", JOptionPane.INFORMATION_MESSAGE);
							KreirajTest.this.dispose();
							moze = false;
							break;
						}
					}
					for(Kurs k : menadzer.getKursMen().getKursevi()) {
						if(k.getNaziv() == kurs && k.getJezik() != jezik) {
							JOptionPane.showMessageDialog(null, "Izabran kurs nije kurs izabranog jezika!", "Greska", JOptionPane.WARNING_MESSAGE);
							KreirajTest.this.dispose();
							moze = false;
							break;
						}
					}
					if(!datum.isAfter(LocalDate.now()))
						JOptionPane.showMessageDialog(null, "Unesite datum koji nije prosao!", "Greska", JOptionPane.WARNING_MESSAGE);
					if(moze) {
						menadzer.getTestMen().dodaj(id, naziv, jezik, kurs, predavac, brUcionice, datum);
						KreirajTest.this.dispose();
					}
				}
			}
		});

		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				KreirajTest.this.dispose();
			}
		});
	}
	
	private boolean validacija() {
		boolean ok = true;
		String poruka = "Molim Vas ispravite sledeće podatke:\n";
		
		if(txtNaziv.getText().trim().equals("")) {
			poruka += "Unesite naziv\n";
			ok = false;
		}
		if(txtBrUci.getText().trim().equals("") || !validanUnosBroja(txtBrUci.getText())) {
			poruka += "Pri unosu broja ucionice unisite samo cifre\n";
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
	
	public boolean validacijaDatuma(final String datum) {
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

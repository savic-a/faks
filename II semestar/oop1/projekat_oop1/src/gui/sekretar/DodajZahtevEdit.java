package gui.sekretar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import citanjeIzFajla.Menadzer;
import entiteti.Kurs;
import entiteti.Sekretar;
import entiteti.Ucenici;
import entiteti.Zahtev;
import net.miginfocom.swing.MigLayout;

public class DodajZahtevEdit extends JDialog{

	private static final long serialVersionUID = -3915773930299862353L;
	
	private JFrame parent;
	private Menadzer menadzer;
	private List<String> ucenici = new ArrayList<String>();
	private List<Kurs> kursevi;
	private List<JCheckBox> cbLista;
	
	JLabel lblUcenik = new JLabel("Ucenik");	
	JComboBox<?> cbUcenik;
	
	JLabel lblKurs = new JLabel("Kursevi");
	
	
	public DodajZahtevEdit(JFrame parent, Menadzer menadzer) {
		super(parent, true);
		setTitle("Zahtev za kurs");
		
		this.parent = parent;
		this.menadzer = menadzer;
		this.kursevi = new ArrayList<Kurs>();
		this.cbLista = new ArrayList<JCheckBox>();
		this.ucenici = pronadjiUcenike();
		this.cbUcenik = new JComboBox<>(ucenici.toArray());
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		initGUI();
		pack();
	}


	private List<String> pronadjiUcenike() {
		for(Ucenici u : menadzer.getUcenikMen().getUcenici()) {
			ucenici.add(u.getUsername());
		}
		return ucenici;
	}


	private void initGUI() {
		MigLayout ml = new MigLayout("wrap 3", "[][][]", "[][]20[]");
		setLayout(ml);
		
		add(lblUcenik);
		add(cbUcenik, "span 2");
		
		add(lblKurs);
		
		for(Kurs k : menadzer.getKursMen().getKursevi()) {
			if(menadzer.getPredMen().PronadjiPredavacaPoUsername(k.getPredavac().getUsername()) != null) {
				JCheckBox cb = new JCheckBox(k.getNaziv());
				cbLista.add(cb);
				add(cb, "span 2");
				add(new JLabel());
			}
		}


		JButton btnOK = new JButton("OK");
		add(btnOK);
		
		JButton btnCancel = new JButton("Otkaži");
		add(btnCancel);
		
		
		btnOK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				for(JCheckBox cb : cbLista) {
					if(cb.isSelected())
						kursevi.add(menadzer.getKursMen().pronadjiKursPoNazivu(cb.getText()));
				}
				if(kursevi.size() == 0)
					JOptionPane.showMessageDialog(null, "Morate da odaberete neki kurs!", "Greska", JOptionPane.WARNING_MESSAGE);
				else {
					String username = cbUcenik.getSelectedItem().toString();
					Ucenici ucenik = menadzer.getUcenikMen().pronadjiUcenikaPoUsername(username);
					boolean greska = false;
					for(Kurs k : kursevi) {
						if(ucenik.getKursevi().contains(k)) {
							JOptionPane.showMessageDialog(null, "Ucenik "+ucenik.getUsername()+" je vec prijavljen na "+k.getNaziv()+"!", "Greska", JOptionPane.WARNING_MESSAGE);
							greska = true;
							DodajZahtevEdit.this.dispose();
						}
					}	
					for(Zahtev z : menadzer.getZahteviKursMen().getZahteviKurs()) {
						if(z.getUcenik() == ucenik) {
							for(Kurs kurs : kursevi) {
								if(z.getKursevi().contains(kurs)) {
									JOptionPane.showMessageDialog(null, "Ucenik "+ucenik.getUsername()+" je vec podneo zahtev za "+kurs.getNaziv()+"!", "Greska", JOptionPane.WARNING_MESSAGE);
									greska = true;
									DodajZahtevEdit.this.dispose();
								}
							}
						}
					}
					if(!greska) {
						int intId = menadzer.getZahteviKursMen().getZahteviKurs().size() + 1;
						String id = Integer.toString(intId);
						Zahtev z = menadzer.getZahteviKursMen().pronadjiZahtevPoId(id);
						while(z != null) {
							intId ++;
							id = Integer.toString(intId);
							z = menadzer.getZahteviKursMen().pronadjiZahtevPoId(id);
						}
						menadzer.getZahteviKursMen().dodaj(id, ucenik, new Sekretar(), kursevi, "kreiran"); 
						((MainSekretar) parent).refreshData();
						DodajZahtevEdit.this.dispose();
					}
				}
			}
		});

		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				DodajZahtevEdit.this.dispose();
			}
		});
	}
}

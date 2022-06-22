package gui.sekretar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import citanjeIzFajla.Menadzer;
import entiteti.Kurs;
import entiteti.Sekretar;
import entiteti.Ucenici;
import entiteti.Zahtev;
import net.miginfocom.swing.MigLayout;

public class PreuzetiZahtevEdit extends JDialog{

	private static final long serialVersionUID = -3450454814729397569L;
	
	private JFrame parent;
	private Menadzer menadzer;
	private Zahtev editZ;
	
	String[] status = {"prihvacen", "odbijen"};
	
	JLabel lblStatus = new JLabel("Status");
	JComboBox<?> cb = new JComboBox<>(status);
	
	JButton btnOK = new JButton("OK");
	JButton btnCancel = new JButton("Otkaži");
	
	public PreuzetiZahtevEdit(JFrame parent, Menadzer mng, Zahtev editZ) {
		super(parent, true);
		setTitle("Zahtevi za upis na kurs");
		
		this.parent = parent;
		this.menadzer = mng;
		this.editZ = editZ;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		initGUI();
		pack();
	}

	private void initGUI() {
		MigLayout ml = new MigLayout("wrap 3", "[][][]", "[]20[]");
		setLayout(ml);
		
		add(lblStatus);
		add(cb);
		add(new JLabel());

		add(btnOK);
		add(btnCancel);
		
		btnOK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String id = editZ.getId();
				Ucenici ucenik = editZ.getUcenik();
				Sekretar sekretar = editZ.getSekretar();
				List<Kurs> kursevi = editZ.getKursevi();
				
				if(cb.getSelectedItem() == "odbijen") {
					menadzer.getZahteviKursMen().brisanje(editZ.getId());
					((PreuzetiZahteviTabela) parent).refreshData();
					PreuzetiZahtevEdit.this.dispose();
				}
				else {
					menadzer.getZahteviKursMen().izmena(id, ucenik, sekretar, kursevi, "prihvacen");
					for(Kurs k : ucenik.getKursevi()) {
						kursevi.add(k);
					} 
					menadzer.getUcenikMen().izmenaKursa(ucenik.getUsername(), kursevi);
					for(Kurs k : kursevi) {
						System.out.println(k.getNaziv());
						menadzer.getKursMen().izmena(k.getId(), k.getNaziv(), k.getJezik(), k.getCenaKursa(), k.getCenaTesta(), k.getPredavac(), k.getBrUcenika()+1, k.getTestovi());
					}
					((PreuzetiZahteviTabela) parent).refreshData();
					PreuzetiZahtevEdit.this.dispose();
				}
			}
		});
		
	}

}

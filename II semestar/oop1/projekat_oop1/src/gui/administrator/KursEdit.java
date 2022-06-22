package gui.administrator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import citanjeIzFajla.JeziciMenadzer;
import citanjeIzFajla.KursMen;
import entiteti.Jezik;
import entiteti.Kurs;
import entiteti.Test;
import net.miginfocom.swing.MigLayout;

public class KursEdit extends JDialog{

	private static final long serialVersionUID = 7841013954184553532L;
	
	private KursMen kursMen;
	private JeziciMenadzer jezMen;
	private Kurs editK;
	private JFrame parent;
	
	JLabel lblId = new JLabel("ID");
	JTextField txtId = new JTextField(15);

	JLabel lblNaziv = new JLabel("Naziv");
	JTextField txtNaziv = new JTextField(15);

	JLabel lblJezik = new JLabel("Jezik");
	JTextField txtJezik = new JTextField(15);
	
	JLabel lblCena = new JLabel("Cena kursa");
	JTextField txtCena = new JTextField(15);
	
	JLabel lblCenaTesta = new JLabel("Cena testa");
	JTextField txtCenaTesta = new JTextField(15);
	
	// Jedan isti dijalog za Add i Edit
	public KursEdit(JFrame parent, KursMen kursMen, JeziciMenadzer jezMen, Kurs editKurs) {
		super(parent, true);
		this.parent = parent;
		if (editKurs != null) {
			setTitle("Izmena kursa");
		} else {
			setTitle("Dodavanje kursa");
		}
		this.kursMen = kursMen;
		this.jezMen = jezMen;
		this.editK = editKurs;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		initGUI();
		pack();
	}

	private void initGUI() {
		MigLayout ml = new MigLayout("wrap 3", "[][][]", "[]10[]10[]10[]10[]10[]");
		setLayout(ml);

		add(lblId);
		add(txtId, "span 2");
		add(lblNaziv);
		add(txtNaziv, "span 2");
		add(lblJezik);
		add(txtJezik, "span 2");
		add(lblCena);
		add(txtCena, "span 2");
		add(lblCenaTesta);
		add(txtCenaTesta, "span 2");

		if (editK != null) {
			// popuni polja vrednostima
			txtId.setText(editK.getId() + "");
			txtNaziv.setText(editK.getNaziv());
			txtJezik.setText(editK.getJezik().getNaziv());
			txtCena.setText(Double.toString(editK.getCenaKursa()));
			txtCenaTesta.setText(Double.toString(editK.getCenaTesta()));
		
			System.out.println(editK.getTestovi());
			if (!editK.getTestovi().contains(null) && editK.getTestovi().size() > 0) {
				JTextArea textArea = new JTextArea(editK.getTestovi().size() + 1, 10);
				JScrollPane scrollPane = new JScrollPane(textArea);
				textArea.setEditable(false);
	
				for (Test t : editK.getTestovi()) {
						textArea.append(t.getNaziv() + "\n");
				}
	
				JLabel lblTestovi = new JLabel("Testovi");
				add(lblTestovi);
	
				add(scrollPane, "span 2");
			}
		}
		
		add(new JLabel());

		JButton btnOK = new JButton("OK");
		add(btnOK);
		
		JButton btnCancel = new JButton("Otkaži");
		add(btnCancel);

		btnOK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(validacija()) {
				String id = txtId.getText().trim();
				String naziv = txtNaziv.getText().trim();
				String jezik = txtJezik.getText().trim().toLowerCase();
				String cena  = txtCena.getText().trim();
				String cenaTesta = txtCenaTesta.getText().trim();
				
				if (editK != null) {
					
					String idJezik = editK.getJezik().getId();
					Jezik j = jezMen.pronadjiJezikPoId(idJezik);
					
					if(!editK.getId().equals(id)) {
						JOptionPane.showMessageDialog(null, "Ne mozete da menjate ID!", "Greska", JOptionPane.WARNING_MESSAGE);
					}
					else if(!editK.getJezik().getNaziv().equals(jezik)) {
						JOptionPane.showMessageDialog(null, "Ne mozete da menjate jezik!", "Greska", JOptionPane.WARNING_MESSAGE);
					}
//					else if(!p.getJezici().contains(jezMen.pronadjiJezikPoNazivu(jezik))) {
//						JOptionPane.showMessageDialog(null, "Unesen predavac ne predaje unesen jezik!", "Greska", JOptionPane.WARNING_MESSAGE);
//					}
					else if(validacija()) {
						kursMen.izmena(editK.getId(), naziv, j, Double.parseDouble(cena), Double.parseDouble(cenaTesta), editK.getPredavac(), editK.getBrUcenika(), editK.getTestovi());
						((KursTabela) parent).refreshData();
						KursEdit.this.dispose();}
			
				}else {
					
					Kurs k = kursMen.pronadjiKursPoId(id);
					Jezik j = jezMen.pronadjiJezikPoNazivu(jezik);
					
					if(k != null) {
						JOptionPane.showMessageDialog(null, "Uneli ste postojeci ID!", "Greska", JOptionPane.WARNING_MESSAGE);
					}
					else if(validacija()) {
						System.out.println("proslo");
						kursMen.dodaj(id, naziv, j, Double.parseDouble(cena), Double.parseDouble(cenaTesta));
						((KursTabela) parent).refreshData();
						KursEdit.this.dispose();}
					}
				}
			}
		});

		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				KursEdit.this.dispose();
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
			poruka += "Unesite naziv\n";
			ok = false;
		}
		if(txtJezik.getText().trim().equals("") || jezMen.pronadjiJezikPoNazivu(txtJezik.getText().trim().toLowerCase()) == null) {
			poruka += "Niste uneli postojeci jezik\n";
			ok = false;
		}
		if(txtCena.getText().trim().equals("") || !validanUnosBroja(txtCena.getText())) {
			poruka += "Cenu kursa unesite ciframa\n";
			ok = false;
		}
		if(txtCenaTesta.getText().trim().equals("") || !validanUnosBroja(txtCenaTesta.getText())) {
			poruka += "Cenu testa unesite ciframa\n";
			ok = false;
		}
		// ako ne postoji taj predavac
//		System.out.println(predMen.PronadjiPredavacaPoUsername(txtPredavac.getText()));
//		if(txtPredavac.getText().trim().equals("") || predMen.PronadjiPredavacaPoUsername(txtPredavac.getText()) == null) {
//			poruka += "Uneli ste nepostojeceg predavaca\n";
//			ok = false;
//		}
		if(ok == false) {
			JOptionPane.showMessageDialog(null, poruka, "Neispravni podaci", JOptionPane.WARNING_MESSAGE);
		}
		return ok;
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

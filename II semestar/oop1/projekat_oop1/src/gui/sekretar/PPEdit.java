package gui.sekretar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import citanjeIzFajla.KursMen;
import citanjeIzFajla.KursNoviMen;
import citanjeIzFajla.PredMenadzer;
import entiteti.Jezik;
import entiteti.Kurs;
import entiteti.Predavac;
import net.miginfocom.swing.MigLayout;

public class PPEdit extends JDialog{

	private static final long serialVersionUID = 3601172166862572189L;
	
	private List<String> dostupni = new ArrayList<String>();
	private KursMen kursMen;
	private PredMenadzer predMen;
	private KursNoviMen kursNoviMen;
	private Kurs editK;
	private JFrame parent;
	
	JLabel lblPredavac = new JLabel("Predavac");	
	JComboBox<?> cbPred;
	
	public PPEdit(JFrame parent, KursMen kursMen, PredMenadzer predMen, KursNoviMen kursNoviMen, Kurs editKurs) {
		super(parent, true);
		this.parent = parent;
		this.editK = editKurs;
		this.kursMen = kursMen;
		this.predMen = predMen;
		this.kursNoviMen = kursNoviMen;
		this.dostupni = pronadjiPredavace();
		this.cbPred = new JComboBox<>(dostupni.toArray());
		setTitle("Postavljanje predavaca");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		initGUI();
		pack();
	}

	
	private void initGUI() {
		MigLayout ml = new MigLayout("wrap 3", "[][][]", "[]10[]");
		setLayout(ml);
		
		add(lblPredavac);
		add(cbPred, "span 2");
		
		add(new JLabel());

		JButton btnOK = new JButton("OK");
		add(btnOK);
		
		JButton btnCancel = new JButton("Otkaži");
		add(btnCancel);

		cbPred.setSelectedItem(editK.getPredavac());
		
		btnOK.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = (String)cbPred.getSelectedItem();
				Predavac pred = predMen.PronadjiPredavacaPoUsername(username);
				kursMen.izmena(editK.getId(), editK.getNaziv(), editK.getJezik(), editK.getCenaKursa(), editK.getCenaTesta(), pred, editK.getBrUcenika(), editK.getTestovi());
				kursNoviMen.brisanje(editK.getId());
				((PPTabela) parent).refreshData();
				PPEdit.this.dispose();
				
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PPEdit.this.dispose();
			}
		});
		
	}
	
	private List<String> pronadjiPredavace() {
		Jezik j = editK.getJezik();
		for(Predavac p : predMen.getPredavaci()) {
			for(Jezik jezik : p.getJezici()) {
				if(j == jezik) {
					dostupni.add(p.getUsername());
				}
			}
		}
		return dostupni;
	}	
	
}

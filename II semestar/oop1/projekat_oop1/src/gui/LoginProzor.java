package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import citanjeIzFajla.Menadzer;
import gui.administrator.MainAdm;
import gui.predavac.MainPredavac;
import gui.sekretar.MainSekretar;
import gui.ucenik.MainUcenik;
import net.miginfocom.swing.MigLayout;

public class LoginProzor extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private Menadzer menadzer;
	
	private JLabel lblNaslov = new JLabel("DOBRODOŠLI U ŠKOLU JEZIKA FOCUS!");
	private JLabel lblUsername = new JLabel("Korisničko ime");
	private JTextField tfUsername = new JTextField(20);
	private JLabel lblLozinka = new JLabel("Lozinka");
	private JPasswordField pfLozinka = new JPasswordField(20);
	private JButton btnOk = new JButton("Prijava");
	private JButton btnCancel = new JButton("Otkaži");
	
	public LoginProzor(Menadzer menadzer) {
		this.menadzer = menadzer;
		prijavaDialog();
	}

	private void prijavaDialog() {
		JDialog d = new JDialog();
		d.setTitle("Prijava");
		d.setLocationRelativeTo(null);
		d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		d.setResizable(false);
		initLoginGUI(d);
		initActions(d);
		d.pack();
		d.setVisible(true);
	}

	private void initLoginGUI(JDialog d) {
		MigLayout mig = new MigLayout("wrap 2", "[][]", "[]20[][]20[]10[]");
		d.setLayout(mig);
		
		d.add(lblNaslov, "span 2");
		d.add(lblUsername);
		d.add(tfUsername);
		d.add(lblLozinka);
		d.add(pfLozinka);
		d.add(new JLabel());
		d.add(btnOk, "split 2");
		d.add(btnCancel);
		d.add(new JLabel());
		
		d.getRootPane().setDefaultButton(btnOk);
	}
	
	public void initActions(JDialog d) {
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LoginProzor.this.dispose();
				LoginProzor.this.setVisible(false);
			}
		});
		
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = tfUsername.getText().trim();
				String lozinka = new String(pfLozinka.getPassword()).trim();
				
				if(username.equals("") || lozinka.equals("")) {
					// tekst poruke, naslov, vrsta poruke
					JOptionPane.showMessageDialog(null, "Niste uneli sve podatke za prijavu!", "Greška", JOptionPane.WARNING_MESSAGE);
				}
				else {
					if(menadzer.getAdmMen().login(username, lozinka) != null) {
						d.setVisible(false);
						d.dispose();
						MainAdm mainAdm = new MainAdm(menadzer, username);
						mainAdm.setVisible(true);
					}
					else if(menadzer.getSekMen().login(username, lozinka) != null) {
						d.setVisible(false);
						d.dispose();
						MainSekretar ms = new MainSekretar(menadzer, username);
						ms.setVisible(true);
					}
					else if(menadzer.getPredMen().login(username, lozinka) != null) {
						d.setVisible(false);
						d.dispose();
						MainPredavac mp = new MainPredavac(menadzer, username);
						mp.setVisible(true);
					}
					else if(menadzer.getUcenikMen().login(username, lozinka) != null) {
						d.setVisible(false);
						d.dispose();
						MainUcenik mu = new MainUcenik(menadzer, username);
						mu.setVisible(true);
					}
					else {
						JOptionPane.showMessageDialog(null, "Uneli ste pogrešno ime i/ili lozinku!", "Info", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});
	}
}
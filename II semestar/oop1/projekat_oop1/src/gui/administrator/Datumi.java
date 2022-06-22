package gui.administrator;

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
import net.miginfocom.swing.MigLayout;

public class Datumi extends JDialog{

	private static final long serialVersionUID = 7077526976025739792L;
	
	private Menadzer menadzer;
	
	JLabel lblOd = new JLabel("Od");	
	JTextField txtOd = new JTextField(15);
	
	JLabel lblDo = new JLabel("Do");	
	JTextField txtDo = new JTextField(15);
	
	public Datumi(Menadzer menadzer) {
		super();
		this.menadzer = menadzer;
		setTitle("Opseg datuma");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		initGUI();
		pack();
	}

	private void initGUI() {
		MigLayout ml = new MigLayout("wrap 3", "[][][]", "[][]10[]");
		setLayout(ml);
		
		add(lblOd);
		add(txtOd, "span 2");
		add(lblDo);
		add(txtDo, "span 2");
		
		add(new JLabel());

		JButton btnOK = new JButton("OK");
		add(btnOK);
		
		JButton btnCancel = new JButton("Otkaži");
		add(btnCancel);	
		
		btnOK.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(validacija()) {
					DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					LocalDate pocetak = LocalDate.parse(txtOd.getText().trim(), format);
					LocalDate kraj = LocalDate.parse(txtDo.getText().trim(), format);
					Izvestaj1 izvestaj = new Izvestaj1(pocetak, kraj, menadzer);
					izvestaj.setVisible(true);
					Datumi.this.dispose();
				}
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Datumi.this.dispose();
			}
		});
	}
	
	private boolean validacija() {
		boolean ok = true;
		String poruka = "Molim Vas ispravite sledeće podatke:\n";
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		if(txtOd.getText().trim().equals("") || !validanDatum(txtOd.getText())) {
			poruka += "Unesite datum u fomatu dd/MM/yyyy\n";
			ok = false;
		}
		else if(txtDo.getText().trim().equals("") || !validanDatum(txtDo.getText())) {
			poruka += "Unesite datum u fomatu dd/MM/yyyy\n";
			ok = false;
		}
		else if(LocalDate.parse(txtDo.getText().trim(), format).isBefore(LocalDate.parse(txtOd.getText().trim(), format))) {
			poruka += "Datum od treba da je pre do\n";
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
}

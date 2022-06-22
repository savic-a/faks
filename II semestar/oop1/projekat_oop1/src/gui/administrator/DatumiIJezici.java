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
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import citanjeIzFajla.Menadzer;
import entiteti.Jezik;
import net.miginfocom.swing.MigLayout;

public class DatumiIJezici extends JDialog{

	private static final long serialVersionUID = -9146064452398111180L;

	private Menadzer menadzer;
	private List<Jezik> jezici;
	private List<JCheckBox> cbLista;
	
	JLabel lblOd = new JLabel("Od");	
	JTextField txtOd = new JTextField(15);
	
	JLabel lblDo = new JLabel("Do");	
	JTextField txtDo = new JTextField(15);
	
	JLabel lblJezici = new JLabel("Jezici");
	
	public DatumiIJezici(Menadzer menadzer) {
		super();
		this.menadzer = menadzer;
		this.jezici = new ArrayList<Jezik>();
		this.cbLista = new ArrayList<JCheckBox>();
		setTitle("Opseg datuma i odabir jezika");
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
		add(lblJezici);
		
		for(Jezik j : menadzer.getJezMen().getJezici()) {
			JCheckBox cb = new JCheckBox(j.getNaziv());
			cbLista.add(cb);
			add(cb, "span 2");
			add(new JLabel());
		}

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
					for(JCheckBox cb : cbLista) {
						if(cb.isSelected())
							jezici.add(menadzer.getJezMen().pronadjiJezikPoNazivu(cb.getText()));
					}
					
					Izvestaj2 izvestaj = new Izvestaj2(pocetak, kraj, menadzer, jezici);
					izvestaj.setVisible(true);
					DatumiIJezici.this.dispose();
				}
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				DatumiIJezici.this.dispose();
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

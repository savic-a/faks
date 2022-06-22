package gui.predavac;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.internal.chartpart.Chart;

import citanjeIzFajla.Menadzer;
import entiteti.Rezultati;
import entiteti.Test;
import net.miginfocom.swing.MigLayout;

public class RezultatiIzvestaj extends JFrame{
	
	private static final long serialVersionUID = -2274521633842740192L;
	private Menadzer menadzer;
	private Test t;
	private List<String> ucenici;
	private JPanel panelBtns = new JPanel();
	private DefaultTableModel tModel;
	private JTable tabela;
	
	JLabel lblTest = new JLabel("Test");
	
	public RezultatiIzvestaj(Menadzer menadzer, Test t, List<String> ucenici) {
		super();
		this.menadzer = menadzer;
		this.t = t;
		this.ucenici = ucenici;
		
		setTitle("Rezultati testa");
		setSize(550, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setIconImage(new ImageIcon("./src/slike/icon.png").getImage());
		ekran();
		initGUI();
		getContentPane().add(panelBtns, BorderLayout.NORTH);
	}

	private void ekran() {
		MigLayout ml = new MigLayout("wrap 3", "[][][]", "20[][]20");
		setLayout(ml);
		add(lblTest);
		
		JTextArea textArea = new JTextArea(5, 40);
		JScrollPane scrollPane = new JScrollPane(textArea);
		textArea.setEditable(false);
		
		textArea.append("Naziv: "+ t.getNaziv() + ",    ");
		textArea.append("Kurs: "+ t.getKurs() + ",    ");
		textArea.append("Predavac: "+ t.getPredavac().getUsername() + "\n");
		textArea.append("Broj ucionice: "+ t.getBrUcionice() + ",    ");
		textArea.append("Datum: "+ t.getDatum().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n");
		
		add(scrollPane, "span 2");
		
		String[] zaglavlje = new String[] {"Ucenik", "Bodovi", "Ocena", "Polozio/pao"};
		Object[][] sadrzaj = new Object[ucenici.size()][zaglavlje.length];
		
		int i = 0;
		for(Rezultati r : menadzer.getRezMen().getRezultati()) {
			if(r.getTest() == t) {
				sadrzaj[i][0] = r.getUcenik().getUsername();
				sadrzaj[i][1] = Double.toString(r.getPoeni());
				sadrzaj[i][2] = Integer.toString(r.getOcena());
				sadrzaj[i][3] = r.getPolozio();
				i++;
			}
		}
		
		tModel = new DefaultTableModel(sadrzaj, zaglavlje);
		tabela = new JTable(tModel);
		
		tabela.setRowSelectionAllowed(false);
		tabela.setColumnSelectionAllowed(false);
		tabela.setDefaultEditor(Object.class, null);
		JScrollPane sp = new JScrollPane(tabela);
		sp.setPreferredSize(new Dimension(550, 80));
		add(sp, "span 3");
	}

	private void initGUI() {
		ExampleChart<PieChart> exampleChart = new Grafik(menadzer, t);
	    PieChart chart = exampleChart.getChart();
	    XChartPanel<Chart<?,?>> grafikon = new XChartPanel<>(chart);
	    panelBtns.setLayout(new java.awt.BorderLayout());
	    panelBtns.add(grafikon, BorderLayout.NORTH);
	    panelBtns.validate();
	}
}


package gui.ucenik;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import entiteti.Rezultati;
import net.miginfocom.swing.MigLayout;

public class RezultatPrikaz extends JDialog{

	private static final long serialVersionUID = 5378334915459958550L;
	
	private Rezultati rezultat;
	private boolean clicked = false;
	
	JLabel lblNaziv = new JLabel("Naziv testa");
	JTextField txtNaziv = new JTextField(15);
	
	JLabel lblKurs = new JLabel("Naziv kursa");
	JTextField txtKurs = new JTextField(15);
	
	JLabel lblPredavac = new JLabel("Predavač");
	JTextField txtPredavac = new JTextField(15);
	
	JLabel lblBodovi = new JLabel("Bodovi");
	JTextField txtBodovi = new JTextField(15);
	
	JLabel lblOcena = new JLabel("Ocena");
	JTextField txtOcena = new JTextField(15);
	
	JLabel lblPolozio = new JLabel("Polozio");
	JTextField txtPolozio = new JTextField(15);
	
	public RezultatPrikaz(JFrame parent, Rezultati rezultat) {
		super(parent, true);
		this.rezultat = rezultat;
		
		setTitle("Rezultat testa");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		initGUI();
		pack();
	}

	private void initGUI() {
		MigLayout ml = new MigLayout("wrap 3", "[][][]", "[][][][][][]20[]");
		setLayout(ml);

		add(lblNaziv);
		add(txtNaziv, "span 2");
		add(lblKurs);
		add(txtKurs, "span 2");
		add(lblPredavac);
		add(txtPredavac, "span 2");
		add(lblBodovi);
		add(txtBodovi, "span 2");
		add(lblOcena);
		add(txtOcena, "span 2");
		add(lblPolozio);
		add(txtPolozio, "span 2");
		
		txtNaziv.setText(rezultat.getTest().getNaziv());
		txtKurs.setText(rezultat.getTest().getKurs());
		txtPredavac.setText(rezultat.getTest().getPredavac().getUsername());
		txtBodovi.setText(Double.toString(rezultat.getPoeni()));
		txtOcena.setText(Integer.toString(rezultat.getOcena()));
		txtPolozio.setText(rezultat.getPolozio());
		
		txtNaziv.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e){
                if(!clicked){
                    clicked=true;
                    txtNaziv.setText("");
                }
            }
		});
		
		txtKurs.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e){
                if(!clicked){
                    clicked=true;
                    txtKurs.setText("");
                }
            }
		});
		
		txtPredavac.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e){
                if(!clicked){
                    clicked=true;
                    txtPredavac.setText("");
                }
            }
		});
		
		txtBodovi.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e){
                if(!clicked){
                    clicked=true;
                    txtBodovi.setText("");
                }
            }
		});
		
		txtOcena.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e){
                if(!clicked){
                    clicked=true;
                    txtOcena.setText("");
                }
            }
		});
		
		txtPolozio.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e){
                if(!clicked){
                    clicked=true;
                    txtPolozio.setText("");
                }
            }
		});
		
		
	}
}

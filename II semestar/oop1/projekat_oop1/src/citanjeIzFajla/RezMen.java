package citanjeIzFajla;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import entiteti.Rezultati;
import entiteti.Test;
import entiteti.Ucenici;

public class RezMen {
	private List<Rezultati> rezultati;
	private List<Test> testovi;
	private String rezFajl;
	private UcenikMen ucenikMen;
	private TestMenadzer testMen;
	
	public RezMen(String rezFajl, UcenikMen ucenikMen, TestMenadzer testMen) {
		super();
		this.rezFajl = rezFajl;
		this.ucenikMen = ucenikMen;
		this.testMen = testMen;
		this.rezultati = new ArrayList<Rezultati>();
		this.testovi = new ArrayList<Test>();
	}
	
	public List<Rezultati> getRezultati() {
		return rezultati;
	}
	
	public Rezultati pronadjiRezultatPoId(String id)
    {
        Rezultati retVal = null;
        for (int i=0; i<rezultati.size(); i++)
        {
            Rezultati r = rezultati.get(i);
            if (r.getId().equals(id))
            {
                retVal = r;
                break;
            }
        }
        return retVal;
    }

   
    public boolean citanje() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(this.rezFajl));
			String linija = null;
			while ((linija = br.readLine()) != null) {
				String[] tokeni = linija.split("\\|");
				Test t = testMen.pronadjiTestPoId(tokeni[1]);
				Ucenici u = ucenikMen.pronadjiUcenikaPoUsername(tokeni[2]);
				Rezultati r = new Rezultati(tokeni[0], t, u, Double.parseDouble(tokeni[3]), Integer.parseInt(tokeni[4]), tokeni[5]);
				this.testovi.add(t);
				this.rezultati.add(r);
			}
				
			int i = 0;
			for(Test test : testovi) {
				i = test.getPredavac().getOcenjeni();
				test.getPredavac().setOcenjeni(i+1);
				}
			
			br.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	public boolean cuvanje() {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(this.rezFajl, false));
			for (Rezultati r : rezultati) {
				pw.println(r.toFileString());
			}
			pw.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	public void dodaj(String id, Test t, Ucenici u, double poeni, int ocena, String polozio) {
		this.rezultati.add(new Rezultati(id, t, u, poeni, ocena, polozio));	
		this.cuvanje();
	}


	public void izmena(String id, Test t, Ucenici u, double poeni, int ocena, String polozio) {
		Rezultati r = this.pronadjiRezultatPoId(id);
		r.setId(id);
		r.setTest(t);
		r.setUcenik(u);
		r.setPoeni(poeni);
		r.setOcena(ocena);
		r.setPolozio(polozio);
		this.cuvanje();
	}


	public void brisanje(String id) {
		Rezultati r = pronadjiRezultatPoId(id);
		this.rezultati.remove(r);
		this.cuvanje();
	}
	
}

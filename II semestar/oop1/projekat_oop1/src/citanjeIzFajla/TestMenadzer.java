package citanjeIzFajla;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import entiteti.Jezik;
import entiteti.Predavac;
import entiteti.Test;

public class TestMenadzer {
	private List<Test> testovi;
	private String testFajl;
	private JeziciMenadzer jezMen;
	private PredMenadzer predMen;

	public TestMenadzer(String testFajl, JeziciMenadzer jezMen, PredMenadzer predMen) {
		super();
		this.testFajl = testFajl;
		this.testovi = new ArrayList<Test>();
		this.jezMen = jezMen;
		this.predMen = predMen;
	}
	
	public List<Test> getTestovi() {
		return testovi;
	}

	// pronadji test
    public Test pronadjiTestPoId(String id)
    {
        Test retVal = null;
        for (int i=0; i<testovi.size(); i++)
        {
            Test t = testovi.get(i);
            if (t.getId().equals(id))
            {
                retVal = t;
                break;
            }
        }
        return retVal;
    }

   
    public boolean citanje() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(this.testFajl));
			String linija = null;
			while ((linija = br.readLine()) != null) {
				String[] tokeni = linija.split("\\|");
				Jezik jezik = this.jezMen.pronadjiJezikPoId(tokeni[2]);
				Predavac predavac = this.predMen.PronadjiPredavacaPoUsername(tokeni[4]);
				DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				LocalDate datum = LocalDate.parse(tokeni[6],format);
				Test t = new Test(tokeni[0], tokeni[1], jezik, tokeni[3], predavac, tokeni[5], datum);
				this.testovi.add(t);
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
			pw = new PrintWriter(new FileWriter(this.testFajl, false));
			for (Test t : testovi) {
				pw.println(t.toFileString());
			}
			pw.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	public void dodaj(String id, String naziv, Jezik jezik, String kurs, Predavac predavac, String brUcionice, LocalDate datum) {
		this.testovi.add(new Test(id, naziv, jezik, kurs, predavac, brUcionice, datum));	
		this.cuvanje();
	}


	public void izmena(String id, String naziv, Jezik jezik, String kurs, Predavac predavac, String brUcionice, LocalDate datum) {
		Test t = this.pronadjiTestPoId(id);
		t.setId(id);
		t.setNaziv(naziv);
		t.setJezik(jezik);
		t.setKurs(kurs);
		t.setPredavac(predavac);
		t.setBrUcionice(brUcionice);
		t.setDatum(datum);
		this.cuvanje();
	}


	public void brisanje(String id) {
		Test t = pronadjiTestPoId(id);
		this.testovi.remove(t);
		this.cuvanje();
	}

}

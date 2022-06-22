package citanjeIzFajla;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import entiteti.Jezik;
import entiteti.Kurs;
import entiteti.Predavac;
import entiteti.Test;

public class KursMen {
	private List<Kurs> kursevi;
	private String kursFajl;
	private JeziciMenadzer jezMen;
	private TestMenadzer testMen;
	private PredMenadzer predMen;

	public KursMen(String kursFajl, JeziciMenadzer jezMen, TestMenadzer testMen, PredMenadzer predMen) {
		super();
		this.kursevi = new ArrayList<Kurs>();
		this.kursFajl = kursFajl;
		this.jezMen = jezMen;
		this.testMen = testMen;
		this.predMen = predMen;
	}
	
	public List<Kurs> getKursevi() {
		return kursevi;
	}

	// pronadji kurs
    public Kurs pronadjiKursPoId(String id)
    {
        Kurs retVal = null;
        for (int i=0; i<kursevi.size(); i++)
        {
            Kurs k = kursevi.get(i);
            if (k.getId().equals(id))
            {
                retVal = k;
                break;
            }
        }
        return retVal;
    }
    
    
    public Kurs pronadjiKursPoNazivu(String naziv)
    {
        Kurs retVal = null;
        for (int i = 0; i < kursevi.size(); i++)
        {
            Kurs k = kursevi.get(i);
            if (k.getNaziv().equals(naziv))
            {
                retVal = k;
                break;
            }
        }
        return retVal;
    }

   
    public boolean citanje() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(this.kursFajl));
			String linija = null;
			while ((linija = br.readLine()) != null) {
				String[] tokeni = linija.split("\\|");
				Jezik jezik = this.jezMen.pronadjiJezikPoId(tokeni[2]);
				Predavac predavac = this.predMen.PronadjiPredavacaPoUsername(tokeni[5]);
				if(predavac == null)
					predavac = new Predavac();
				int i = predavac.getBrKurseva();
				predavac.setBrKurseva(i+1);
				Kurs k = new Kurs(tokeni[0], tokeni[1], jezik, Double.parseDouble(tokeni[3]), Double.parseDouble(tokeni[4]), predavac, Integer.parseInt(tokeni[6]));
				if (tokeni.length > 7) {
					for (String id : tokeni[7].split(",")) {
						k.getTestovi().add(this.testMen.pronadjiTestPoId(id));						
					}						
				}
				this.kursevi.add(k);
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
			pw = new PrintWriter(new FileWriter(this.kursFajl, false));
			for (Kurs k : kursevi) {
				pw.println(k.toFileString());
			}
			pw.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	public void dodaj(String id, String naziv, Jezik jezik, double cenaKursa, double cenaTesta) {
		this.kursevi.add(new Kurs(id, naziv, jezik, cenaKursa, cenaTesta));	
		this.cuvanje();
	}


	public void izmena(String id, String naziv, Jezik jezik, double cenaKursa, double cenaTesta, Predavac predavac, int brUcenika, List<Test> testovi) {
		Kurs k = this.pronadjiKursPoId(id);
		k.setId(id);
		k.setNaziv(naziv);
		k.setJezik(jezik);
		k.setCenaKursa(cenaKursa);
		k.setCenaTesta(cenaTesta);
		k.setPredavac(predavac);
		k.setBrUcenici(brUcenika);
		k.setTestovi(testovi);
		this.cuvanje();
	}


	public void brisanje(String id) {
		Kurs k = pronadjiKursPoId(id);
		this.kursevi.remove(k);
		this.cuvanje();
	}
}

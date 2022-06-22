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

public class KursNoviMen {
	private List<Kurs> noviKursevi;
	private String noviKursFajl;
	private JeziciMenadzer jezMen;

	public KursNoviMen(String kursFajl, JeziciMenadzer jezMen) {
		super();
		this.noviKursevi = new ArrayList<Kurs>();
		this.noviKursFajl = kursFajl;
		this.jezMen = jezMen;
	}
	
	public List<Kurs> getKursevi() {
		return noviKursevi;
	}

	// pronadji kurs
    public Kurs pronadjiKursPoId(String id)
    {
        Kurs retVal = null;
        for (int i=0; i<noviKursevi.size(); i++)
        {
            Kurs k = noviKursevi.get(i);
            if (k.getId().equals(id))
            {
                retVal = k;
                break;
            }
        }
        return retVal;
    }

   
    public boolean citanje() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(this.noviKursFajl));
			String linija = null;
			while ((linija = br.readLine()) != null) {
				String[] tokeni = linija.split("\\|");
				Jezik jezik = this.jezMen.pronadjiJezikPoId(tokeni[2]);
//				Predavac predavac = new Predavac();
				Kurs k = new Kurs(tokeni[0], tokeni[1], jezik, Double.parseDouble(tokeni[3]), Double.parseDouble(tokeni[4]));
				this.noviKursevi.add(k);
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
			pw = new PrintWriter(new FileWriter(this.noviKursFajl, false));
			for (Kurs k : noviKursevi) {
				pw.println(k.toFileString());
			}
			pw.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	public void dodaj(String id, String naziv, Jezik jezik, double cenaKursa, double cenaTesta) {
		this.noviKursevi.add(new Kurs(id, naziv, jezik, cenaKursa, cenaTesta));	
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
		this.noviKursevi.remove(k);
		this.cuvanje();
	}
}

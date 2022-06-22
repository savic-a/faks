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
import entiteti.Pol;
import entiteti.Predavac;

public class PredMenadzer {
	private String predavacFajl;
	private JeziciMenadzer jezMen;
	private List<Predavac> predavaci;
	
	public PredMenadzer(String predavacFajl, JeziciMenadzer jezMen) {
		super();
		this.predavacFajl = predavacFajl;
		this.jezMen = jezMen;
		this.predavaci = new ArrayList<Predavac>();
	}
	
	public List<Predavac> getPredavaci() {
		return predavaci;
	}

    //pronadji predavaca
    public Predavac PronadjiPredavacaPoUsername(String username)
    {
    	Predavac retVal = null;
        for (int i = 0; i < predavaci.size(); i++)
        {
        	Predavac p = predavaci.get(i);
            if (p.getUsername().equals(username))
            {
                retVal = p;
                break;
            }
        }
        return retVal;
    }
    
    public Predavac login(String username, String lozinka) {
    	Predavac predavac = null;
    	for (int i=0; i<predavaci.size(); i++) {
    		Predavac p = predavaci.get(i);
    		if(p.getUsername().equals(username) && p.getLozinka().equals(lozinka)) {
    			predavac = p;
    			break;
    		}
    	}
    	return predavac;
    }

	public boolean citanje() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(this.predavacFajl));
			String linija = null;
			while ((linija = br.readLine()) != null) {
				String[] tokeni = linija.split("\\|");
				int polInt = Integer.parseInt(tokeni[2]);
				Pol pol = Pol.values()[polInt];
				DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				LocalDate datum = LocalDate.parse(tokeni[3],format);
				Predavac p = new Predavac(tokeni[0], tokeni[1], pol, datum, tokeni[4], tokeni[5], tokeni[6], tokeni[7], tokeni[8], tokeni[9]);
				if (tokeni.length > 10) {
					for (String jezId : tokeni[10].split(",")) {
						p.getJezici().add(this.jezMen.pronadjiJezikPoId(jezId));						
					}
				}
				System.out.println(p);
				this.predavaci.add(p);
			}
			br.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
//	public boolean cuvanje() {
//		PrintWriter pw = null;
//		try {
//			pw = new PrintWriter(new FileWriter(this.predavacFajl, false));
//			for (Predavac p : predavaci) {
//				pw.println(p.toFileString());
//			}
//			pw.close();
//		} catch (IOException e) {
//			return false;
//		}
//		return true;
//	}
	
	public boolean cuvanje() {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(this.predavacFajl, false));
			for (Predavac p : predavaci) {
				pw.println(p.toFileStringLang());
			}
			pw.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}


	public void dodaj(String ime, String prezime, Pol pol, LocalDate datum, String tel, String adresa, String username, String lozinka, String nss, String staz, List<Jezik> jezici) {
		this.predavaci.add(new Predavac(ime, prezime, pol, datum, adresa, tel, username, lozinka, nss, staz, jezici));	
		this.cuvanje();
	}


	public void izmena(String ime, String prezime, Pol pol, LocalDate datum, String tel, String adresa, String username, String lozinka, String nss, String staz, List<Jezik> jezici) {
		Predavac p = this.PronadjiPredavacaPoUsername(username);
		p.setIme(ime);
		p.setPrezime(prezime);
		p.setPol(pol);
		p.setDatum(datum);
		p.setAdresa(adresa);
		p.setTel(tel);
		p.setUsername(username);
		p.setLozinka(lozinka);
		p.setNivoSpreme(nss);
		p.setStaz(staz);
		p.setJezici(jezici);
		this.cuvanje();
	}


	public void brisanje(String username) {
		Predavac p = PronadjiPredavacaPoUsername(username);
		this.predavaci.remove(p);
		this.cuvanje();
	}
	
	
//	public void izmenaJezikaPredavaca(String ime, String prezime, Pol pol, LocalDate datum, String tel, String adresa, String username, String lozinka, String nss, String staz, List<Jezik> jezici) {
//		Predavac p = this.PronadjiPredavacaPoUsername(username);
//		p.setIme(ime);
//		p.setPrezime(prezime);
//		p.setPol(pol);
//		p.setDatum(datum);
//		p.setAdresa(adresa);
//		p.setTel(tel);
//		p.setUsername(username);
//		p.setLozinka(lozinka);
//		p.setJezici(jezici);
//		p.setNivoSpreme(nss);
//		p.setStaz(staz);
//		this.cuvanjeIJezika();
//	}
}

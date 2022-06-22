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

import entiteti.Pol;
import entiteti.Sekretar;

public class SekMenadzer {
	private String sekretarFajl;
	private List<Sekretar> sekretari;
	
	public SekMenadzer(String sekretarFajl) {
		super();
		this.sekretarFajl = sekretarFajl;
		this.sekretari = new ArrayList<Sekretar>();
	}
	
	public List<Sekretar> getSekretari() {
		return sekretari;
	}


    //pronadji sekretara
    public Sekretar pronadjiSekretaraPoUsername(String username)
    {
    	Sekretar retVal = null;
        for (int i = 0; i < sekretari.size(); i++)
        {
        	Sekretar st = sekretari.get(i);
            if (st.getUsername().equals(username))
            {
                retVal = st;
                break;
            }
        }
        return retVal;
    }
    
    public Sekretar login(String username, String lozinka) {
    	Sekretar sekretar = null;
    	for (int i=0; i<sekretari.size(); i++) {
    		Sekretar s = sekretari.get(i);
    		if(s.getUsername().equals(username) && s.getLozinka().equals(lozinka)) {
    			sekretar = s;
    			break;
    		}
    	}
    	return sekretar;
    }

	public boolean citanje() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(this.sekretarFajl));
			String linija = null;
			while ((linija = br.readLine()) != null) {
				String[] tokeni = linija.split("\\|");
				int polInt = Integer.parseInt(tokeni[2]);
				Pol pol = Pol.values()[polInt];
				DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				LocalDate datum = LocalDate.parse(tokeni[3],format);
				Sekretar s = new Sekretar(tokeni[0], tokeni[1], pol, datum, tokeni[4], tokeni[5], tokeni[6], tokeni[7], tokeni[8], tokeni[9]);
//				System.out.println(s);
				this.sekretari.add(s);
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
			pw = new PrintWriter(new FileWriter(this.sekretarFajl, false));
			for (Sekretar s : sekretari) {
				pw.println(s.toFileString());
			}
			pw.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}


	public void dodaj(String ime, String prezime, Pol pol, LocalDate datum, String tel, String adresa, String username, String lozinka, String nss, String staz) {
		this.sekretari.add(new Sekretar(ime, prezime, pol, datum, adresa, tel, username, lozinka, nss, staz));	
		this.cuvanje();
	}


	public void izmena(String ime, String prezime, Pol pol, LocalDate datum, String tel, String adresa, String username, String lozinka, String nss, String staz) {
		Sekretar s = this.pronadjiSekretaraPoUsername(username);
		s.setIme(ime);
		s.setPrezime(prezime);
		s.setPol(pol);
		s.setDatum(datum);
		s.setAdresa(adresa);
		s.setTel(tel);
		s.setUsername(username);
		s.setLozinka(lozinka);
		s.setNivoSpreme(nss);
		s.setStaz(staz);
		this.cuvanje();
	}


	public void brisanje(String username) {
		Sekretar s = pronadjiSekretaraPoUsername(username);
		this.sekretari.remove(s);
		this.cuvanje();
	}
}

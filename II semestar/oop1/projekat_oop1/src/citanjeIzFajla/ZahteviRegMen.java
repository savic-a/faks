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
import entiteti.Zahtev;

public class ZahteviRegMen {
	private List<Zahtev> zahtevi;
	private String zahteviRegFajl;
	private SekMenadzer sekMen;

	public ZahteviRegMen(String zahteviFajl, SekMenadzer sekMen) {
		super();
		this.zahtevi = new ArrayList<Zahtev>();
		this.zahteviRegFajl = zahteviFajl;
		this.sekMen = sekMen;
	}
	
	public List<Zahtev> getZahteviReg() {
		return zahtevi;
	}

	// pronadji zahtev
    public Zahtev pronadjiZahtevPoId(String id)
    {
        Zahtev retVal = null;
        for (int i=0; i<zahtevi.size(); i++)
        {
            Zahtev z = zahtevi.get(i);
            if (z.getId().equals(id))
            {
                retVal = z;
                break;
            }
        }
        return retVal;
    }

   
    public boolean citanje() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(this.zahteviRegFajl));
			String linija = null;
			while ((linija = br.readLine()) != null) {
				String[] tokeni = linija.split("\\|");
				
				int polInt = Integer.parseInt(tokeni[3]);
				Pol pol = Pol.values()[polInt];
				DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				LocalDate datum = LocalDate.parse(tokeni[4],format);
				Sekretar sekretar = this.sekMen.pronadjiSekretaraPoUsername(tokeni[9]);
				LocalDate datumZahteva = LocalDate.parse(tokeni[11],format);
				Zahtev z = new Zahtev(tokeni[0], tokeni[1], tokeni[2], pol, datum, tokeni[5], tokeni[6], tokeni[7], tokeni[8], sekretar, tokeni[10], datumZahteva);
				this.zahtevi.add(z);
				
				int i = 0;
				for(Sekretar s : sekMen.getSekretari()) {
					i = 0;
					for(Zahtev zahtev : zahtevi) {
						if(zahtev.getSekretar() == s && zahtev.getStatus().equals("prihvacen")) 
							i++;
					}
				s.setZahtevReg(i);
				}
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
			pw = new PrintWriter(new FileWriter(this.zahteviRegFajl, false));
			for (Zahtev z : zahtevi) {
				pw.println(z.toFileString());
			}
			pw.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	public void dodaj(String id, String ime, String prezime, Pol pol, LocalDate datum, String tel, String adresa, String username, String lozinka, Sekretar s, String status) {
		this.zahtevi.add(new Zahtev(id, ime, prezime, pol, datum, tel, adresa, username, lozinka, s, status, LocalDate.now()));	
		this.cuvanje();
	}


	public void izmena(String id, String ime, String prezime, Pol pol, LocalDate datum, String tel, String adresa, String username, String lozinka, Sekretar s, String status) {
		Zahtev z = this.pronadjiZahtevPoId(id);
		z.setId(id);
		z.setIme(ime);
		z.setPrezime(prezime);
		z.setPol(pol);
		z.setDatum(datum);
		z.setTel(tel);
		z.setAdresa(adresa);
		z.setUsername(username);
		z.setLozinka(lozinka);
		z.setSekretar(s);
		z.setStatus(status);
		this.cuvanje();
	}


	public void brisanje(String id) {
		Zahtev z = pronadjiZahtevPoId(id);
		this.zahtevi.remove(z);
		this.cuvanje();
	}
}

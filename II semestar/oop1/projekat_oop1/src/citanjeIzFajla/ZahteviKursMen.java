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

import entiteti.Kurs;
import entiteti.Sekretar;
import entiteti.Ucenici;
import entiteti.Zahtev;

public class ZahteviKursMen {
	
	private List<Zahtev> zahtevi;
	private String zahteviKursFajl;
	private SekMenadzer sekMen;
	private UcenikMen ucenikMen;
	private KursMen kursMen;
	private List<Kurs> kursevi;
	
	public ZahteviKursMen(String zahteviKursFajl, SekMenadzer sekMen, UcenikMen ucenikMen, KursMen kursMen) {
		super();
		this.zahtevi = new ArrayList<Zahtev>();
		this.zahteviKursFajl = zahteviKursFajl;
		this.sekMen = sekMen;
		this.ucenikMen = ucenikMen;
		this.kursMen = kursMen;
		this.kursevi = new ArrayList<Kurs>();
	}
	
	public List<Zahtev> getZahteviKurs() {
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
			BufferedReader br = new BufferedReader(new FileReader(this.zahteviKursFajl));
			String linija = null;
			while ((linija = br.readLine()) != null) {
				String[] tokeni = linija.split("\\|");
				
				Ucenici ucenik = this.ucenikMen.pronadjiUcenikaPoUsername(tokeni[1]);
				Sekretar sekretar = this.sekMen.pronadjiSekretaraPoUsername(tokeni[2]);
				if(sekretar == null) {
					sekretar = new Sekretar();
				}
				for (String id : tokeni[3].split(",")) {
					Kurs k = this.kursMen.pronadjiKursPoId(id);
					kursevi.add(k);
				}
				DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				LocalDate datum = LocalDate.parse(tokeni[5],format);
				Zahtev z = new Zahtev(tokeni[0], ucenik, sekretar, tokeni[4], datum);
				
				for (String id : tokeni[3].split(",")) {
					z.getKursevi().add(this.kursMen.pronadjiKursPoId(id));
				}
			this.zahtevi.add(z);
			
			int i = 0;
			for(Sekretar s : sekMen.getSekretari()) {
				i = 0;
				for(Zahtev zahtev : zahtevi) {
					if(zahtev.getSekretar() == s && zahtev.getStatus().equals("prihvacen")) 
						i++;
				}
			s.setZahtevKurs(i);
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
			pw = new PrintWriter(new FileWriter(this.zahteviKursFajl, false));
			for (Zahtev z : zahtevi) {
				pw.println(z.toFileStringKurs());
			}
			pw.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	public void dodaj(String id, Ucenici ucenik, Sekretar s, List<Kurs> kursevi, String status) {
		this.zahtevi.add(new Zahtev(id, ucenik, s, kursevi, status, LocalDate.now()));	
		this.cuvanje();
	}


	public void izmena(String id, Ucenici ucenik, Sekretar s, List<Kurs> kursevi, String status) {
		Zahtev z = this.pronadjiZahtevPoId(id);
		z.setId(id);
		z.setUcenik(ucenik);
		z.setSekretar(s);
		z.setKursevi(kursevi);
		z.setStatus(status);
		this.cuvanje();
	}


	public void brisanje(String id) {
		Zahtev z = pronadjiZahtevPoId(id);
		this.zahtevi.remove(z);
		this.cuvanje();
	}
	
	
}

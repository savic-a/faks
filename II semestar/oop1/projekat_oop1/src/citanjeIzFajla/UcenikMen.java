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
import entiteti.Pol;
import entiteti.Test;
import entiteti.Ucenici;

public class UcenikMen {
	private String uceniciFajl;
	private List<Ucenici> ucenici;
	private KursMen kursMen;
	private TestMenadzer testMen;
	
	public UcenikMen(String uceniciFajl, KursMen kursMen, TestMenadzer testMen) {
		super();
		this.uceniciFajl = uceniciFajl;
		this.ucenici = new ArrayList<Ucenici>();
		this.kursMen = kursMen;
		this.testMen = testMen;
	}
	
	public List<Ucenici> getUcenici() {
		return ucenici;
	}
	
	 public Ucenici pronadjiUcenikaPoUsername(String username)
	    {
	    	Ucenici retVal = null;
	        for (int i = 0; i < ucenici.size(); i++)
	        {
	        	Ucenici u = ucenici.get(i);
	            if (u.getUsername().equals(username))
	            {
	                retVal = u;
	                break;
	            }
	        }
	        return retVal;
	    }
	 
	 public Ucenici login(String username, String lozinka) {
			Ucenici ucenik = null;
	    	for (int i=0; i<ucenici.size(); i++) {
	    		Ucenici u = ucenici.get(i);
	    		if(u.getUsername().equals(username) && u.getLozinka().equals(lozinka)) {
	    			ucenik = u;
	    			break;
	    		}
	    	}
	    	return ucenik;
		}

		public boolean citanje() {
			try {
				BufferedReader br = new BufferedReader(new FileReader(this.uceniciFajl));
				String linija = null;
				while ((linija = br.readLine()) != null) {
					String[] tokeni = linija.split("\\|");
					int polInt = Integer.parseInt(tokeni[2]);
					Pol pol = Pol.values()[polInt];
					DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					LocalDate datum = LocalDate.parse(tokeni[3],format);
					Ucenici u = new Ucenici(tokeni[0], tokeni[1], pol, datum, tokeni[4], tokeni[5], tokeni[6], tokeni[7]);
					if (tokeni.length > 8) {
						for (String id : tokeni[8].split(",")) {
							u.getKursevi().add(this.kursMen.pronadjiKursPoId(id));
						}
					} if(tokeni.length > 9) {
						for (String id : tokeni[9].split(",")) {
							u.getTestovi().add(this.testMen.pronadjiTestPoId(id));
						}
					}
					System.out.println(u);
					this.ucenici.add(u);
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
				pw = new PrintWriter(new FileWriter(this.uceniciFajl, false));
				for (Ucenici u : ucenici) {
					pw.println(u.toFileString());
				}
				pw.close();
			} catch (IOException e) {
				return false;
			}
			return true;
		}


		public void dodaj(String ime, String prezime, Pol pol, LocalDate datum, String tel, String adresa, String username, String lozinka) {
			this.ucenici.add(new Ucenici(ime, prezime, pol, datum, tel, adresa, username, lozinka));	
			this.cuvanje();
		}


		public void izmena(String ime, String prezime, Pol pol, LocalDate datum, String tel, String adresa, String username, String lozinka) {
			Ucenici u = this.pronadjiUcenikaPoUsername(username);
			u.setIme(ime);
			u.setPrezime(prezime);
			u.setPol(pol);
			u.setDatum(datum); 
			u.setTel(tel);
			u.setAdresa(adresa);
			u.setUsername(username);
			u.setLozinka(lozinka);
			this.cuvanje();
		}
		
		public void izmenaKursa(String username, List<Kurs> kursevi) {
			Ucenici u = this.pronadjiUcenikaPoUsername(username);
			u.setKursevi(kursevi);
			this.cuvanje();
		}
		
		public void izmenaTesta(String username, List<Test> testovi) {
			Ucenici u = this.pronadjiUcenikaPoUsername(username);
			u.setTestovi(testovi);
			this.cuvanje();
		}


		public void brisanje(String username) {
			Ucenici u = pronadjiUcenikaPoUsername(username);
			this.ucenici.remove(u);
			this.cuvanje();
		}
}

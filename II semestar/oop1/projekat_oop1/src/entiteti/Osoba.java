package entiteti;

import java.time.LocalDate;

public class Osoba {
	private String ime;
	private String prezime;
	private Pol pol;
	private LocalDate datum;
	private String tel;
	private String adresa;
	private String username;
	private String lozinka;
	
	public Osoba() {
		this.ime = "";
		this.prezime = "";
		this.pol = Pol.ZENSKO;
		this.datum = LocalDate.now();
		this.tel = "";
		this.adresa = "";
		this.username = "";
		this.lozinka = "";
		}
	
	public Osoba(String ime, String prezime, Pol pol, LocalDate datum, String tel, String adresa, String username, String lozinka) {
		this();
		this.ime = ime;
		this.prezime = prezime;
		this.pol = pol;
		this.datum = datum;
		this.tel = tel;
		this.adresa = adresa;
		this.username = username;
		this.lozinka = lozinka;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public Pol getPol() {
		return pol;
	}

	public void setPol(Pol pol) {
		this.pol = pol;
	}

	public LocalDate getDatum() {
		return datum;
	}

	public void setDatum(LocalDate datum) {
		this.datum = datum;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	@Override
	public String toString() {
		return "Osoba [ime=" + ime + ", prezime=" + prezime + ", pol=" + pol + ", datum=" + datum + ", tel=" + tel
				+ ", adresa=" + adresa + ", username=" + username + ", lozinka=" + lozinka + " ";
	}
}

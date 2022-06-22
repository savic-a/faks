package entiteti;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Sekretar extends Osoba{
	private String nivoSpreme;
	private String staz;
	private double plata;
	
	private int zahtevReg;
	private int zahtevKurs;
	
	public Sekretar(String ime, String prezime, Pol pol, LocalDate datum, String tel, String adresa, String username,
			String lozinka, String nivoSpreme, String staz) {
		super(ime, prezime, pol, datum, tel, adresa, username, lozinka);
		this.nivoSpreme = nivoSpreme;
		this.staz = staz;
		this.plata = izracunajPlatu(nivoSpreme, staz);
	}
	
	public Sekretar() {
		super();
	}


	private double izracunajPlatu(String nivoSpreme, String staz) {
		return Double.parseDouble(staz) / 10 * 5000 * Integer.parseInt(nivoSpreme) + getBonus();
	}
	
	
	public String getNivoSpreme() {
		return nivoSpreme;
	}


	public void setNivoSpreme(String nivoSpreme) {
		this.nivoSpreme = nivoSpreme;
	}


	public String getStaz() {
		return staz;
	}


	public void setStaz(String staz) {
		this.staz = staz;
	}
	

	public double getPlata() {
		return izracunajPlatu(nivoSpreme, staz);
	}
	
	public void setPlata(double plata) {
		this.plata = plata;
	}

	public int getBonus() {
		return getZahtevReg() + getZahtevKurs();
	}

	public int getZahtevReg() {
		return zahtevReg;
	}

	public void setZahtevReg(int zahtevReg) {
		this.zahtevReg = zahtevReg;
	}

	public int getZahtevKurs() {
		return zahtevKurs;
	}

	public void setZahtevKurs(int zahtevKurs) {
		this.zahtevKurs = zahtevKurs;
	}

	public String toString() {
		return "Sekretar [nivoSpreme=" + nivoSpreme + ", staz=" + staz + ", plata=" + plata
				+ ", Ime=" + getIme() + ", Prezime=" + getPrezime() + ", Pol=" + getPol()
				+ ", Datum=" + getDatum() + ", Tel=" + getTel() + ", Adresa=" + getAdresa()
				+ ", Username=" + getUsername() + ", Lozinka=" + getLozinka() + "]";
	}
	
	public String toFileString() {
		int brPol = getPol().ordinal();
		String pol = Integer.toString(brPol); 
		String datum = getDatum().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		return getIme()+"|"+getPrezime()+"|"+pol+"|"+datum+"|"+getTel()+"|"+getAdresa()+"|"+getUsername()+"|"+getLozinka()+"|"+nivoSpreme+"|"+staz;
	}
}

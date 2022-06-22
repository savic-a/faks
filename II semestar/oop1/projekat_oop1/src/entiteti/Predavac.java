package entiteti;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Predavac extends Osoba{
	private String nivoSpreme;
	private String staz;
	private double plata;
	private List<Jezik> jezici;
//	private List<Kurs> kursevi;
	
	private int brKurseva;
	private int ocenjeni;
	
	public Predavac(String ime, String prezime, Pol pol, LocalDate datum, String tel, String adresa, String username,
			String lozinka, String nivoSpreme, String staz, List<Jezik> jezici) {
		super(ime, prezime, pol, datum, tel, adresa, username, lozinka);
		this.nivoSpreme = nivoSpreme;
		this.staz = staz;
		this.plata = izracunajPlatu(nivoSpreme, staz);
		this.jezici = jezici;
//		this.kusevi = kursevi;
	}
	
	public Predavac(String ime, String prezime, Pol pol, LocalDate datum, String tel, String adresa, String username,
			String lozinka, String nivoSpreme, String staz) {
		super(ime, prezime, pol, datum, tel, adresa, username, lozinka);
		this.nivoSpreme = nivoSpreme;
		this.staz = staz;
		this.plata = izracunajPlatu(nivoSpreme, staz);
		this.jezici = new ArrayList<Jezik>();
//		this.kursevi = kursevi;
	}
	
	
	public Predavac() {
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
	

	public List<Jezik> getJezici() {
		return jezici;
	}


	public void setJezici(List<Jezik> jezici) {
		this.jezici = jezici;
	}

	public int getBonus() {
		return getOcenjeni() + getBrKurseva();
	}

	public int getOcenjeni() {
		return ocenjeni;
	}

	public void setOcenjeni(int ocenjeni) {
		this.ocenjeni = ocenjeni;
	}

	public int getBrKurseva() {
		return brKurseva;
	}

	public void setBrKurseva(int brKurseva) {
		this.brKurseva = brKurseva;
	}

	public void setPlata(double plata) {
		this.plata = plata;
	}

	@Override
	public String toString() {
		return "Predavac [plata=" + plata + ", jezici=" + jezici + ", Ime=" + getIme() + ", Prezime="
				+ getPrezime() + ", Pol=" + getPol() + ", Datum=" + getDatum() + ", Tel=" + getTel()
				+ ", Adresa=" + getAdresa() + ", Username=" + getUsername() + ", Lozinka=" + getLozinka() + "]";
	}


	public String toFileString() {
		int brPol = getPol().ordinal();
		String pol = Integer.toString(brPol); 
		String datum = getDatum().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		return getIme()+"|"+getPrezime()+"|"+pol+"|"+datum+"|"+getTel()+"|"+getAdresa()+"|"+getUsername()+"|"+getLozinka()+"|"+nivoSpreme+"|"+staz;
	}

	public String toFileStringLang() {
		String ids = "";
		if (!this.jezici.contains(null) && this.jezici.size()>0) {
			for (Jezik j : jezici) {
				ids +=j.getId()+",";
			}
			ids = ids.substring(0, ids.length()-1);
			ids = "|"+ids;
		}
		int brPol = getPol().ordinal();
		String pol = Integer.toString(brPol); 
		String datum = getDatum().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		return getIme()+"|"+getPrezime()+"|"+pol+"|"+datum+"|"+getTel()+"|"+getAdresa()+"|"+getUsername()+"|"+getLozinka()+"|"+nivoSpreme+
				"|"+staz+ids;
	}
}

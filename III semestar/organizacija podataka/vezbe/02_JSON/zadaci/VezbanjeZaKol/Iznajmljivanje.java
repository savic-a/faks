package VezbanjeZaKol;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Iznajmljivanje {
	@JsonProperty("sifra")
	String sifra;
	@JsonProperty("auto")
	Auto auto;
	@JsonProperty("mesto")
	String mesto;
	@JsonProperty("datum_iznajmljivanja")
	String datum_iznajmljivanja;
	@JsonProperty("datum_vracanja")
	String datum_vracanja;
	@JsonProperty("kilometri")
	double kilometri;
	@JsonProperty("cena")
	double cena;
	@JsonProperty("broj_dana")
	int broj_dana;
	@JsonProperty("osoba")
	Osoba osoba;
	
	public Iznajmljivanje(String sifra, Auto auto, String mesto, String datum_iznajmljivanja, String datum_vracanja,
			double kilometri, double cena, int broj_dana, Osoba osoba) {
		super();
		this.sifra = sifra;
		this.auto = auto;
		this.mesto = mesto;
		this.datum_iznajmljivanja = datum_iznajmljivanja;
		this.datum_vracanja = datum_vracanja;
		this.kilometri = kilometri;
		this.cena = cena;
		this.broj_dana = broj_dana;
		this.osoba = osoba;
	}
	public Auto getAuto() {
		return auto;
	}
	public void setAuto(Auto auto) {
		this.auto = auto;
	}
	public String getSifra() {
		return sifra;
	}
	public void setSifra(String sifra) {
		this.sifra = sifra;
	}
	public String getMesto() {
		return mesto;
	}
	public void setMesto(String mesto) {
		this.mesto = mesto;
	}
	public String getDatum_iznajmljivanja() {
		return datum_iznajmljivanja;
	}
	public void setDatum_iznajmljivanja(String datum_iznajmljivanja) {
		this.datum_iznajmljivanja = datum_iznajmljivanja;
	}
	public String getDatum_vracanja() {
		return datum_vracanja;
	}
	public void setDatum_vracanja(String datum_vracanja) {
		this.datum_vracanja = datum_vracanja;
	}
	public double getKilometri() {
		return kilometri;
	}
	public void setKilometri(double kilometri) {
		this.kilometri = kilometri;
	}
	public double getCena() {
		return cena;
	}
	public void setCena(double cena) {
		this.cena = cena;
	}
	public int getBroj_dana() {
		return broj_dana;
	}
	public void setBroj_dana(int broj_dana) {
		this.broj_dana = broj_dana;
	}
	public Osoba getOsoba() {
		return osoba;
	}
	public void setOsoba(Osoba osoba) {
		this.osoba = osoba;
	}
	public Iznajmljivanje() {
		super();
	}
	@Override
	public String toString() {
		return "Iznajmljivanje [sifra=" + sifra + ", auto=" + auto + ", mesto=" + mesto + ", datum_iznajmljivanja="
				+ datum_iznajmljivanja + ", datum_vracanja=" + datum_vracanja + ", kilometri=" + kilometri + ", cena="
				+ cena + ", broj_dana=" + broj_dana + ", osoba=" + osoba + "]";
	}
	
	
}

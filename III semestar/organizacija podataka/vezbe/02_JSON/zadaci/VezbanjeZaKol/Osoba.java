package VezbanjeZaKol;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Osoba {
	@JsonProperty("ime")
	String ime;
	@JsonProperty("prezime")
	String prezime;
	@JsonProperty("maticni_broj")
	String maticni_broj;
	
	
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
	public String getMaticni_broj() {
		return maticni_broj;
	}
	public void setMaticni_broj(String maticni_broj) {
		this.maticni_broj = maticni_broj;
	}
	public Osoba(String ime, String prezime, String maticni_broj) {
		super();
		this.ime = ime;
		this.prezime = prezime;
		this.maticni_broj = maticni_broj;
	}
	public Osoba() {
		super();
	}
	@Override
	public String toString() {
		return "Osoba [ime=" + ime + ", prezime=" + prezime + ", maticni_broj=" + maticni_broj + "]";
	}
	
	
}

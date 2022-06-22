package VezbanjeZaKol;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByPosition;

public class Polazak {
	@JsonProperty("Sifra")
	@CsvBindByPosition(position = 0, required = true)
	public String sifra;
	@CsvBindByPosition(position = 1, required = true)
	@JsonProperty("Polazak")
	public String polazak;
	@CsvBindByPosition(position = 2, required = true)
	@JsonProperty("Dolazak")
	public String dolazak;
	@CsvBindByPosition(position = 3, required = true)
	@JsonProperty("DatumDolaska")
	public String datum_polaska;
	@CsvBindByPosition(position = 4, required = true)
	@JsonProperty("VremePolaska")
	public String vreme_polaska;
	@CsvBindByPosition(position = 5, required = true)
	@JsonProperty("VremeDolaska")
	public String vreme_dolaska;
	@CsvBindByPosition(position = 6, required = true)
	@JsonProperty("Peron")
	public String peron;
	@CsvBindByPosition(position = 6, required = true)
	@JsonProperty("Cena")
	public double cena;
	@CsvBindByPosition(position = 7, required = true)
	@JsonProperty("BrojProdanih")
	public int broj_prodanih;
	public String getSifra() {
		return sifra;
	}
	public void setSifra(String sifra) {
		this.sifra = sifra;
	}
	public String getPolazak() {
		return polazak;
	}
	public void setPolazak(String polazak) {
		this.polazak = polazak;
	}
	public String getDolazak() {
		return dolazak;
	}
	public void setDolazak(String dolazak) {
		this.dolazak = dolazak;
	}
	public String getDatum_polaska() {
		return datum_polaska;
	}
	public void setDatum_polaska(String datum_polaska) {
		this.datum_polaska = datum_polaska;
	}
	public String getVreme_polaska() {
		return vreme_polaska;
	}
	public void setVreme_polaska(String vreme_polaska) {
		this.vreme_polaska = vreme_polaska;
	}
	public String getVreme_dolaska() {
		return vreme_dolaska;
	}
	public void setVreme_dolaska(String vreme_dolaska) {
		this.vreme_dolaska = vreme_dolaska;
	}
	public String getPeron() {
		return peron;
	}
	public void setPeron(String peron) {
		this.peron = peron;
	}
	public double getCena() {
		return cena;
	}
	public void setCena(double cena) {
		this.cena = cena;
	}
	public int getBroj_prodanih() {
		return broj_prodanih;
	}
	public void setBroj_prodanih(int broj_prodanih) {
		this.broj_prodanih = broj_prodanih;
	}
	@Override
	public String toString() {
		return "Polazak [sifra=" + sifra + ", polazak=" + polazak + ", dolazak=" + dolazak + ", datum_polaska="
				+ datum_polaska + ", vreme_polaska=" + vreme_polaska + ", vreme_dolaska=" + vreme_dolaska + ", peron="
				+ peron + ", cena=" + cena + ", broj_prodanih=" + broj_prodanih + "]";
	}
	public Polazak(String sifra, String polazak, String dolazak, String datum_polaska, String vreme_polaska,
			String vreme_dolaska, String peron, double cena, int broj_prodanih) {
		super();
		this.sifra = sifra;
		this.polazak = polazak;
		this.dolazak = dolazak;
		this.datum_polaska = datum_polaska;
		this.vreme_polaska = vreme_polaska;
		this.vreme_dolaska = vreme_dolaska;
		this.peron = peron;
		this.cena = cena;
		this.broj_prodanih = broj_prodanih;
	}
	public Polazak() {
		super();
	}
	
	
	
	
	
}

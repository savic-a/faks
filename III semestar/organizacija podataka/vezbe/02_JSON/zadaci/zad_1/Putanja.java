package zad_1;

import com.opencsv.bean.CsvBindByPosition;

public class Putanja {
	@CsvBindByPosition(position = 0, required = true)
	private String id;
	@CsvBindByPosition(position = 1, required = true)
	private String polazak;
	@CsvBindByPosition(position = 2, required = true)
	private String dolazak;
	@CsvBindByPosition(position = 3, required = true)
	private String datum;
	@CsvBindByPosition(position = 4, required = true)
	private String vremePolazak;
	@CsvBindByPosition(position = 5, required = true)
	private String vremeDolaska;
	@CsvBindByPosition(position = 6, required = true)
	private String peron;
	@CsvBindByPosition(position = 7, required = true)
	private String cena;
	@CsvBindByPosition(position = 8, required = true)
	private String brKarata;
	
	public Putanja() {}

	public Putanja(String id, String polazak, String dolazak, String datum, String vremePolazak, String vremeDolaska,
			String peron, String cena, String brKarata) {
		super();
		this.id = id;
		this.polazak = polazak;
		this.dolazak = dolazak;
		this.datum = datum;
		this.vremePolazak = vremePolazak;
		this.vremeDolaska = vremeDolaska;
		this.peron = peron;
		this.cena = cena;
		this.brKarata = brKarata;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getDatum() {
		return datum;
	}

	public void setDatum(String datum) {
		this.datum = datum;
	}

	public String getVremePolazak() {
		return vremePolazak;
	}

	public void setVremePolazak(String vremePolazak) {
		this.vremePolazak = vremePolazak;
	}

	public String getVremeDolaska() {
		return vremeDolaska;
	}

	public void setVremeDolaska(String vremeDolaska) {
		this.vremeDolaska = vremeDolaska;
	}

	public String getPeron() {
		return peron;
	}

	public void setPeron(String peron) {
		this.peron = peron;
	}

	public String getCena() {
		return cena;
	}

	public void setCena(String cena) {
		this.cena = cena;
	}

	public String getBrKarata() {
		return brKarata;
	}

	public void setBrKarata(String brKarata) {
		this.brKarata = brKarata;
	}

	@Override
	public String toString() {
		return "Putanja [id=" + id + ", polazak=" + polazak + ", dolazak=" + dolazak + ", datum=" + datum
				+ ", vremePolazak=" + vremePolazak + ", vremeDolaska=" + vremeDolaska + ", peron=" + peron + ", cena="
				+ cena + ", brKarata=" + brKarata + "]";
	}
}

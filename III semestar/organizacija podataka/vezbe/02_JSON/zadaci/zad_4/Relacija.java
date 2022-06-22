package zad_4;

public class Relacija {
	private String id;
	private String polazak;
	private String dolazak;
	private String datum;
	private String vremePolazak;
	private String vremeDolaska;
	private String peron;
	private String cena;
	private String brKarata;
	
	public Relacija() {}

	public Relacija(String id, String polazak, String dolazak, String datum, String vremePolazak, String vremeDolaska,
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
		return "Relacija [id=" + id + ", polazak=" + polazak + ", dolazak=" + dolazak + ", datum=" + datum
				+ ", vremePolazak=" + vremePolazak + ", vremeDolaska=" + vremeDolaska + ", peron=" + peron + ", cena="
				+ cena + ", brKarata=" + brKarata + "]";
	}
	
	
}

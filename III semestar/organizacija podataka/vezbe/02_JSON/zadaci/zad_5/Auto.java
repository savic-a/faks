package zad_5;

import com.opencsv.bean.CsvBindByPosition;

public class Auto {
	@CsvBindByPosition(position = 0, required = true)
	private String id;
	@CsvBindByPosition(position = 1, required = true)
	private String mesto;
	@CsvBindByPosition(position = 2, required = true)
	private String datum;
	@CsvBindByPosition(position = 3, required = false)
	private String vracanjeDatum;
	@CsvBindByPosition(position = 4, required = false)
	private String km;
	@CsvBindByPosition(position = 5, required = true)
	private String cena;
	@CsvBindByPosition(position = 6, required = true)
	private String brDana;
	@CsvBindByPosition(position = 7, required = true)
	private Osoba osoba;
	
	public Auto() {
	}

	public Auto(String id, String mesto, String datum, String vracanjeDatum, String km, String cena, String brDana,
			Osoba osoba) {
		super();
		this.id = id;
		this.mesto = mesto;
		this.datum = datum;
		this.vracanjeDatum = vracanjeDatum;
		this.km = km;
		this.cena = cena;
		this.brDana = brDana;
		this.osoba = osoba;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMesto() {
		return mesto;
	}

	public void setMesto(String mesto) {
		this.mesto = mesto;
	}

	public String getDatum() {
		return datum;
	}

	public void setDatum(String datum) {
		this.datum = datum;
	}

	public String getVracanjeDatum() {
		return vracanjeDatum;
	}

	public void setVracanjeDatum(String vracanjeDatum) {
		this.vracanjeDatum = vracanjeDatum;
	}

	public String getKm() {
		return km;
	}

	public void setKm(String km) {
		this.km = km;
	}

	public String getCena() {
		return cena;
	}

	public void setCena(String cena) {
		this.cena = cena;
	}

	public String getBrDana() {
		return brDana;
	}

	public void setBrDana(String brDana) {
		this.brDana = brDana;
	}

	public Osoba getOsoba() {
		return osoba;
	}

	public void setOsoba(Osoba osoba) {
		this.osoba = osoba;
	}

	@Override
	public String toString() {
		return "Auto [id=" + id + ", mesto=" + mesto + ", datum=" + datum + ", vracanjeDatum=" + vracanjeDatum + ", km="
				+ km + ", cena=" + cena + ", brDana=" + brDana + ", osoba=" + osoba + "]";
	}
}

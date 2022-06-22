package entiteti;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Test {
	private String id;
	private String naziv;
	private Jezik jezik;
	private String kurs;
	private Predavac predavac;
	private String brUcionice;
	private LocalDate datum;
//	private Rezultati rezultati;
	
	public Test(String id, String naziv, Jezik jezik, String kurs, Predavac predavac, String brUcionice, LocalDate datum) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.jezik = jezik;
		this.kurs = kurs;
		this.predavac = predavac;
		this.brUcionice = brUcionice;
		this.datum = datum;
//		this.rezultati = rezultati;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Jezik getJezik() {
		return jezik;
	}

	public void setJezik(Jezik jezik) {
		this.jezik = jezik;
	}

	public String getKurs() {
		return kurs;
	}

	public void setKurs(String kurs) {
		this.kurs = kurs;
	}

	public Predavac getPredavac() {
		return predavac;
	}

	public void setPredavac(Predavac predavac) {
		this.predavac = predavac;
	}

	public String getBrUcionice() {
		return brUcionice;
	}

	public void setBrUcionice(String brUcionice) {
		this.brUcionice = brUcionice;
	}

	public LocalDate getDatum() {
		return datum;
	}

	public void setDatum(LocalDate datum) {
		this.datum = datum;
	}

	@Override
	public String toString() {
		return "Test [id=" + id + ", naziv=" + naziv + ", kurs=" + kurs + ", predavac=" + predavac + ", brUcionice="
				+ brUcionice + ", datum=" + datum + "]";
	}

	public String toFileString() {
		String datum = getDatum().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		return id+"|"+naziv+"|"+getJezik().getId() +"|"+kurs+"|"+predavac.getUsername()+"|"+brUcionice+"|"+datum;
	}
	
	

//	public Rezultati getRezultati() {
//		return rezultati;
//	}
//
//	public void setRezultati(Rezultati rezultati) {
//		this.rezultati = rezultati;
//	}
	
}

package entiteti;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Ucenici extends Osoba {
	private List<Kurs> kursevi;
	private List<Test> testovi;
	
	public Ucenici(String ime, String prezime, Pol pol, LocalDate datum, String tel, String adresa, String username,
			String lozinka, List<Kurs> kursevi, List<Test> testovi) {
		super(ime, prezime, pol, datum, tel, adresa, username, lozinka);
		this.kursevi = kursevi;
		this.testovi = testovi;
	}

	public Ucenici(String ime, String prezime, Pol pol, LocalDate datum, String tel, String adresa, String username,
			String lozinka) {
		super(ime, prezime, pol, datum, tel, adresa, username, lozinka);
		this.kursevi = new ArrayList<Kurs>();
		this.testovi = new ArrayList<Test>();
	}
	
	public List<Kurs> getKursevi() {
		return kursevi;
	}

	public void setKursevi(List<Kurs> kursevi) {
		this.kursevi = kursevi;
	}

	public List<Test> getTestovi() {
		return testovi;
	}

	public void setTestovi(List<Test> testovi) {
		this.testovi = testovi;
	}

	@Override
	public String toString() {
		return "Ucenici" + kursevi + ", Ime=" + getIme() + ", Prezime=" + getPrezime()
				+ ", Pol=" + getPol() + ", Datum=" + getDatum() + ", Tel=" + getTel() + ", Adresa="
				+ getAdresa() + ", Username=" + getUsername() + ", Lozinka=" + getLozinka() + testovi+"]";
	}

	public String toFileString() {
		String idsk = "";
		if (!this.kursevi.contains(null) && this.kursevi.size()>0) {
			for (Kurs k : kursevi) {
				idsk +=k.getId()+",";
			}
			idsk = idsk.substring(0, idsk.length()-1);
			idsk = "|"+idsk;
		}
		String idst = "";
		if (!this.testovi.contains(null) && this.testovi.size()>0) {
			for (Test t : testovi) {
				idst +=t.getId()+",";
			}
			idst = idst.substring(0, idst.length()-1);
			idst = "|"+idst;
		}
		
		int brPol = getPol().ordinal();
		String pol = Integer.toString(brPol); 
		String datum = getDatum().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		return getIme()+"|"+getPrezime()+"|"+pol+"|"+datum+"|"+getTel()+"|"+getAdresa()+"|"+getUsername()+"|"+getLozinka()+idsk+idst;
	}
}

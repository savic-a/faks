package entiteti;

import java.util.ArrayList;
import java.util.List;

public class Kurs {
	private String id;
	private String naziv;
	private Jezik jezik;
	private double cenaKursa;
	private double cenaTesta;
	private Predavac predavac;
	private int brUcenika;
	private List<Test> testovi;
//	private List<Zahtev> zahtevi;
//	private boolean aktivan;
	
	// kada administrator dodaje nov kurs
	public Kurs(String id, String naziv, Jezik jezik, double cenaKursa, double cenaTesta) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.jezik = jezik;
		this.cenaKursa = cenaKursa;
		this.cenaTesta = cenaTesta;
		this.predavac = new Predavac();
		this.testovi = new ArrayList<Test>();
		this.brUcenika = 0;
//		this.zahtevi = zahtevi;
//		this.aktivan = aktivan;
	}
	
	//kada seketar postavi predavca, ali jos niko nije upisan na kurs
	public Kurs(String id, String naziv, Jezik jezik, double cenaKursa, double cenaTesta, Predavac predavac, int brUcenika) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.jezik = jezik;
		this.cenaKursa = cenaKursa;
		this.cenaTesta = cenaTesta;
		this.predavac = predavac;
		this.brUcenika = brUcenika;
		this.testovi = new ArrayList<Test>();
//		this.zahtevi = zahtevi;
//		this.aktivan = aktivan;
	}
	
	public Kurs(String id, String naziv, Jezik jezik, double cenaKursa, double cenaTesta, Predavac predavac, List<Test> testovi, int brUcenika) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.jezik = jezik;
		this.cenaKursa = cenaKursa;
		this.cenaTesta = cenaTesta;
		this.predavac = predavac;
		this.testovi = testovi;
		this.brUcenika = brUcenika;
//		this.zahtevi = zahtevi;
//		this.aktivan = aktivan;
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

	public double getCenaKursa() {
		return cenaKursa;
	}

	public void setCenaKursa(double cenaKursa) {
		this.cenaKursa = cenaKursa;
	}

	public double getCenaTesta() {
		return cenaTesta;
	}

	public void setCenaTesta(double cenaTesta) {
		this.cenaTesta = cenaTesta;
	}

	public int getBrUcenika() {
		return brUcenika;
	}

	public void setBrUcenici(int brUcenika) {
		this.brUcenika = brUcenika;
	}

	public Predavac getPredavac() {
		return predavac;
	}

	public void setPredavac(Predavac predavac) {
		this.predavac = predavac;
	}

	public List<Test> getTestovi() {
		return testovi;
	}

	public void setTestovi(List<Test> testovi) {
		this.testovi = testovi;
	}

	@Override
	public String toString() {
		return "Kurs [id=" + id + ", naziv=" + naziv + ", jezik=" + jezik + ", cenaKursa=" + cenaKursa + ", cenaTesta=" + cenaTesta + ", predavac="
				+ predavac + ", testovi=" + testovi + ", brUcenici=" + brUcenika + "]";
	}

	public String toFileString() {
		String ids = "";
		if (!this.testovi.contains(null) && this.testovi.size()>0) {
			for (Test t : testovi) {
				ids +=t.getId()+",";
			}
			ids = ids.substring(0, ids.length()-1);
			ids = "|"+ids;
		} 
		return getId()+"|"+getNaziv()+"|"+getJezik().getId()+"|"+Double.toString(getCenaKursa())+"|"+Double.toString(getCenaTesta())+"|"+getPredavac().getUsername()+"|"
				+Integer.toString(getBrUcenika())+ids;
	}

//	public List<Zahtev> getZahtevi() {
//		return zahtevi;
//	}
//
//	public void setZahtevi(List<Zahtev> zahtevi) {
//		this.zahtevi = zahtevi;
//	}
//
//	public boolean isAktivan() {
//		return aktivan;
//	}
//
//	public void setAktivan(boolean aktivan) {
//		this.aktivan = aktivan;
//	}
	
	
}

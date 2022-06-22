package podesavanje;

public class Ucitavanje {
	private String admFajlNaziv, sekretarFajlNaziv, predavacFajlNaziv, uceniciFajlNaziv, jeziciFajlNaziv, testoviFajlNaziv, kurseviFajlNaziv
	,kursNoviFajlNaziv, zahteviRegFajlNaziv, zahteviKursFajlNaziv, rezultatiFajlNaziv;

	public Ucitavanje(String admFajlNaziv, String sekretarFajlNaziv, String predavacFajlNaziv,
			String uceniciFajlNaziv, String jeziciFajlNaziv, String testoviFajlNaziv, String kurseviFajlNaziv, String kursNoviFajlNaziv,
			String zahteviRegFajlNaziv, String zahteviKursFajlNaziv, String rezultatiFajlNaziv) {
		super();
		this.admFajlNaziv = admFajlNaziv;
		this.sekretarFajlNaziv = sekretarFajlNaziv;
		this.predavacFajlNaziv = predavacFajlNaziv;
		this.uceniciFajlNaziv = uceniciFajlNaziv;
		this.jeziciFajlNaziv = jeziciFajlNaziv;
		this.testoviFajlNaziv = testoviFajlNaziv;
		this.kurseviFajlNaziv = kurseviFajlNaziv;
		this.kursNoviFajlNaziv = kursNoviFajlNaziv;
		this.zahteviRegFajlNaziv = zahteviRegFajlNaziv;
		this.zahteviKursFajlNaziv = zahteviKursFajlNaziv;
		this.rezultatiFajlNaziv = rezultatiFajlNaziv;
	}

	public String getAdmFajlNaziv() {
		return admFajlNaziv;
	}

	public String getSekretarFajlNaziv() {
		return sekretarFajlNaziv;
	}

	public String getPredavacFajlNaziv() {
		return predavacFajlNaziv;
	}

	public String getUceniciFajlNaziv() {
		return uceniciFajlNaziv;
	}

	public String getJeziciFajlNaziv() {
		return jeziciFajlNaziv;
	}

	public String getTestoviFajlNaziv() {
		return testoviFajlNaziv;
	}

	public String getKurseviFajlNaziv() {
		return kurseviFajlNaziv;
	}
	
	public String getKursNoviFajlNaziv() {
		return kursNoviFajlNaziv;
	}

	public String getZahteviRegFajlNaziv() {
		return zahteviRegFajlNaziv;
	}	
	
	public String getZahteviKursFajlNaziv() {
		return zahteviKursFajlNaziv;
	}
	
	public String getRezultatiFajlNaziv() {
		return rezultatiFajlNaziv;
	}
}

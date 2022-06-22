package entiteti;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Zahtev {
	private String id;
	private String ime;
	private String prezime;
	private Pol pol;
	private LocalDate datum;
	private String tel;
	private String adresa;
	private String username;
	private String lozinka;
	private Sekretar sekretar;
	private String status;
	
	private Ucenici ucenik;
	private List<Kurs> kursevi;
	
	private LocalDate datumZahteva;
	
	public Zahtev(String id, String ime, String prezime, Pol pol, LocalDate datum, String tel, String adresa, String username,
			String lozinka, Sekretar sekretar, String status, LocalDate datumZahteva) {
		super();
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.pol = pol;
		this.datum = datum;
		this.tel = tel;
		this.adresa = adresa;
		this.username = username;
		this.lozinka = lozinka;
		this.sekretar = sekretar;
		this.status = status;
		this.datumZahteva = datumZahteva;
	}
	
	public Zahtev(String id, Ucenici ucenik, Sekretar sekretar, String status, LocalDate datumZahteva) {
		this.id = id;
		this.ucenik = ucenik;
		this.sekretar = sekretar;
		this.kursevi = new ArrayList<Kurs>();
		this.status = status;
		this.datumZahteva = datumZahteva;
	}
	
	public Zahtev(String id, Ucenici ucenik, Sekretar sekretar, List<Kurs> kursevi, String status, LocalDate datumZahteva) {
		this.id = id;
		this.ucenik = ucenik;
		this.sekretar = sekretar;
		this.kursevi = kursevi;
		this.status = status;
		this.datumZahteva = datumZahteva;
	}

	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



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


	public Pol getPol() {
		return pol;
	}


	public void setPol(Pol pol) {
		this.pol = pol;
	}


	public LocalDate getDatum() {
		return datum;
	}


	public void setDatum(LocalDate datum) {
		this.datum = datum;
	}


	public String getTel() {
		return tel;
	}


	public void setTel(String tel) {
		this.tel = tel;
	}


	public String getAdresa() {
		return adresa;
	}


	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getLozinka() {
		return lozinka;
	}


	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}


	public Sekretar getSekretar() {
		return sekretar;
	}


	public void setSekretar(Sekretar sekretar) {
		this.sekretar = sekretar;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}

	public Ucenici getUcenik() {
		return ucenik;
	}

	public void setUcenik(Ucenici ucenik) {
		this.ucenik = ucenik;
	}

	public List<Kurs> getKursevi() {
		return kursevi;
	}

	public void setKursevi(List<Kurs> kursevi) {
		this.kursevi = kursevi;
	}
	
	public LocalDate getDatumZahteva() {
		return datumZahteva;
	}

	public void setDatumZahteva(LocalDate datumZahteva) {
		this.datumZahteva = datumZahteva;
	}

	@Override
	public String toString() {
		return "Zahtev [ kursevi=" + kursevi + "]";
	}

	public String toFileString() {
		int brPol = getPol().ordinal();
		String pol = Integer.toString(brPol); 
		String datum = getDatum().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		return getId()+"|"+getIme()+"|"+getPrezime()+"|"+pol+"|"+datum+"|"+getTel()+"|"+getAdresa()+"|"+getUsername()+"|"+getLozinka()+"|"+getSekretar().getUsername()+
				"|"+getStatus();

	}

	public String toFileStringKurs() {
		String ids = "";
		if (!this.kursevi.contains(null) && this.kursevi.size()>0) {
			for (Kurs k : kursevi) {
				ids +=k.getId()+",";
			}
			ids = ids.substring(0, ids.length()-1);
			ids = "|"+ids;
		}
		String datum = getDatumZahteva().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		return getId()+"|"+getUcenik().getUsername()+"|"+getSekretar().getUsername()+ids+"|"+getStatus()+"|"+datum;
	}
	
	
}

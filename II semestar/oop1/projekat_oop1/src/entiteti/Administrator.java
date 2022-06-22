package entiteti;

import java.time.LocalDate;

public class Administrator extends Osoba{

	public Administrator() {
		super();
	}

	public Administrator(String ime, String prezime, Pol pol, LocalDate datum, String tel, String adresa,
			String username, String lozinka) {
		super(ime, prezime, pol, datum, tel, adresa, username, lozinka);
	}

	@Override
	public String toString() {
		return "Administrator [Ime=" + getIme() + ", Prezime=" + getPrezime() + ", Pol=" + getPol()
				+ ", Datum=" + getDatum() + ", Tel=" + getTel() + ", Adresa=" + getAdresa()
				+ ", Username=" + getUsername() + ", Lozinka=" + getLozinka() + "]";
	}
	
}

package VezbanjeZaKol;

import com.opencsv.bean.CsvBindByPosition;

public class OsobaPodaci {
	@CsvBindByPosition(position = 0, required = true)
	Osoba osoba;
	@CsvBindByPosition(position = 1, required = true)
	double potrosena_suma;
	@CsvBindByPosition(position = 2, required = true)
	double prosek_dana;
	public Osoba getOsoba() {
		return osoba;
	}
	public void setOsoba(Osoba osoba) {
		this.osoba = osoba;
	}
	public double getPotrosena_suma() {
		return potrosena_suma;
	}
	public void setPotrosena_suma(double potrosena_suma) {
		this.potrosena_suma = potrosena_suma;
	}
	public double getProsek_dana() {
		return prosek_dana;
	}
	public void setProsek_dana(double prosek_dana) {
		this.prosek_dana = prosek_dana;
	}
	@Override
	public String toString() {
		return "OsobaPodaci [osoba=" + osoba + ", potrosena_suma=" + potrosena_suma + ", prosek_dana=" + prosek_dana
				+ "]";
	}
	public OsobaPodaci(Osoba osoba, double potrosena_suma, double prosek_dana) {
		super();
		this.osoba = osoba;
		this.potrosena_suma = potrosena_suma;
		this.prosek_dana = prosek_dana;
	}
	public OsobaPodaci() {
		super();
	}
	
	
}

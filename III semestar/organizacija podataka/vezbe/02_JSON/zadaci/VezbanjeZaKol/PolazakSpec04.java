package VezbanjeZaKol;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PolazakSpec04 {
	@JsonProperty("PolaznaStanica")
	public String polaznaStanica;
	@JsonProperty("DolaznaStanica")
	public String dolaznaStanica;
	@JsonProperty("VremePolaska")
	public String vremePolaska;
	@Override
	public String toString() {
		return "PolazakSpec04 [polaznaStanica=" + polaznaStanica + ", dolaznaStanica=" + dolaznaStanica
				+ ", vremePolaska=" + vremePolaska + "]";
	}
	public String getPolaznaStanica() {
		return polaznaStanica;
	}
	public void setPolaznaStanica(String polaznaStanica) {
		this.polaznaStanica = polaznaStanica;
	}
	public String getDolaznaStanica() {
		return dolaznaStanica;
	}
	public void setDolaznaStanica(String dolaznaStanica) {
		this.dolaznaStanica = dolaznaStanica;
	}
	public String getVremePolaska() {
		return vremePolaska;
	}
	public void setVremePolaska(String vremePolaska) {
		this.vremePolaska = vremePolaska;
	}
	public PolazakSpec04(String polaznaStanica, String dolaznaStanica, String vremePolaska) {
		super();
		this.polaznaStanica = polaznaStanica;
		this.dolaznaStanica = dolaznaStanica;
		this.vremePolaska = vremePolaska;
	}
	public PolazakSpec04() {
		super();
	}
	public PolazakSpec04(Polazak p) {
		super();
		this.polaznaStanica = p.getPolazak();
		this.dolaznaStanica = p.getDolazak();
		this.vremePolaska = p.getVreme_polaska();
	}
	
	
}

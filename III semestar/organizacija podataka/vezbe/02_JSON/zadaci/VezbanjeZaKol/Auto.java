package VezbanjeZaKol;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Auto {
	@JsonProperty("model")
	String model;
	@JsonProperty("godina")
	String godina;
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getGodina() {
		return godina;
	}
	public void setGodina(String godina) {
		this.godina = godina;
	}
	public Auto(String model, String godina) {
		super();
		this.model = model;
		this.godina = godina;
	}
	public Auto() {
		super();
	}
	@Override
	public String toString() {
		return "Auto [model=" + model + ", godina=" + godina + "]";
	}
	
	
	
}

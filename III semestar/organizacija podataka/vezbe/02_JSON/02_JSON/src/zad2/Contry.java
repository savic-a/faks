package zad2;

import com.opencsv.bean.CsvBindByName;

public class Contry {
	@CsvBindByName(column = "CountryName")
	private String contryName;
	@CsvBindByName(column = "CapitalName")
	private String capitalName;
	@CsvBindByName(column = "CapitalLatitude")
	private String capitalLatitude;
	@CsvBindByName(column = "CapitalLongitude")
	private String capitalLongitude;
	@CsvBindByName(column = "CountryCode")
	private String contryCode;
	@CsvBindByName(column = "ContinentName")
	private String continentName;
	
	public Contry() {
	}

	public Contry(String contryName, String capitalName, String capitalLatitude, String capitalLongitude,
			String contryCode, String continentName) {
		super();
		this.contryName = contryName;
		this.capitalName = capitalName;
		this.capitalLatitude = capitalLatitude;
		this.capitalLongitude = capitalLongitude;
		this.contryCode = contryCode;
		this.continentName = continentName;
	}

	public String getContryName() {
		return contryName;
	}

	public void setContryName(String contryName) {
		this.contryName = contryName;
	}

	public String getCapitalName() {
		return capitalName;
	}

	public void setCapitalName(String capitalName) {
		this.capitalName = capitalName;
	}

	public String getCapitalLatitude() {
		return capitalLatitude;
	}

	public void setCapitalLatitude(String capitalLatitude) {
		this.capitalLatitude = capitalLatitude;
	}

	public String getCapitalLongitude() {
		return capitalLongitude;
	}

	public void setCapitalLongitude(String capitalLongitude) {
		this.capitalLongitude = capitalLongitude;
	}

	public String getContryCode() {
		return contryCode;
	}

	public void setContryCode(String contryCode) {
		this.contryCode = contryCode;
	}

	public String getContinentName() {
		return continentName;
	}

	public void setContinentName(String continentName) {
		this.continentName = continentName;
	}

	@Override
	public String toString() {
		return "Contry [contryName=" + contryName + ", capitalName=" + capitalName + ", capitalLatitude="
				+ capitalLatitude + ", capitalLongitude=" + capitalLongitude + ", contryCode=" + contryCode
				+ ", continentName=" + continentName + "]";
	}
}

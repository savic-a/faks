package entiteti;


public class Rezultati {
	private String id;
	private Test test;
	private Ucenici ucenik;
	private double poeni;
	private int ocena;
	private String polozio;
	
	public Rezultati(String id, Test test, Ucenici ucenik, double poeni, int ocena, String polozio) {
		super();
		this.id = id;
		this.test = test;
		this.ucenik = ucenik;
		this.poeni = poeni;
		this.ocena = ocena;
		this.polozio = polozio;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

	public Ucenici getUcenik() {
		return ucenik;
	}

	public void setUcenik(Ucenici ucenik) {
		this.ucenik = ucenik;
	}

	public double getPoeni() {
		return poeni;
	}

	public void setPoeni(double poeni) {
		this.poeni = poeni;
	}

	public int getOcena() {
		return ocena;
	}

	public void setOcena(int ocena) {
		this.ocena = ocena;
	}

	public String getPolozio() {
		return polozio;
	}

	public void setPolozio(String polozio) {
		this.polozio = polozio;
	}

	@Override
	public String toString() {
		return "Rezultati [id=" + id + ", test=" + test + ", ucenik=" + ucenik + ", Poeni=" + poeni + ", ocena="
				+ ocena + ", polozio=" + polozio + "]";
	}
	
	public String toFileString() {
		return id+"|"+test.getId()+"|"+ucenik.getUsername() +"|"+Double.toString(poeni)+"|"+Integer.toString(ocena)+"|"+polozio;
	}
}

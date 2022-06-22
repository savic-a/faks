package entiteti;

public class Jezik {
	private String id;
	private String naziv;
	
	public Jezik(String id, String naziv) {
		super();
		this.id = id;
		this.naziv = naziv;
	}
	
	public Jezik() {
		super();
		this.id = "";
		this.naziv = "";
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

	public String toFileString() {
		return this.id+"|"+this.naziv;
	}

	@Override
	public String toString() {
		return "Jezik [id=" + id + ", naziv=" + naziv + "]";
	}
	
}

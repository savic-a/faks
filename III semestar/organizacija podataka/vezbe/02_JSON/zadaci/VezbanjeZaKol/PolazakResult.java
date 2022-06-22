package VezbanjeZaKol;

public class PolazakResult<T> {
	public String relacija;
	public T vrednost;
	@Override
	public String toString() {
		return "PolazakResult [relacija=" + relacija + ", vrednost=" + vrednost + "]";
	}
	public PolazakResult(String relacija, T vrednost) {
		super();
		this.relacija = relacija;
		this.vrednost = vrednost;
	}
	public PolazakResult() {
		super();
	}
	public String getRelacija() {
		return relacija;
	}
	public void setRelacija(String relacija) {
		this.relacija = relacija;
	}
	public T getVrednost() {
		return vrednost;
	}
	public void setVrednost(T vrednost) {
		this.vrednost = vrednost;
	}
	
	
}

package entitie;


public class Staff {
	private int id;
	private String name;
	private String surname;
	private int totalSalary;
	
	public Staff(int id, String name, String surname, int salary) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.totalSalary = salary;
	}
	
	public Staff(int id, String name, String surname) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public int getTotalSalary() {
		return totalSalary;
	}

	public void setTotalSalary(int totalSalary) {
		this.totalSalary = totalSalary;
	}
	
	@Override
	public String toString() {
		return String.format("STAFF{Id: %d, Name: %10s, Surname: %10s, totalSalary: %5d}", id, name, surname, totalSalary);
	}
	
	public String print() {
		return String.format("STAFF{Id: %d, Name: %8s, Surname: %8s}", id, name, surname);
	}

}

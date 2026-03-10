package iss.spring.web.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.Length;

@Entity
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Length(min=1, message="{firstName.length}")
	private String firstName;
	
	private String lastName;

	@ManyToOne
	private City birthCity;
	
	public Student() {
		
	}

	public Student(Long id, String firstName, String lastName) {
		this();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Student(Student other) {
		this.id = other.id;
		this.firstName = other.firstName;
		this.lastName = other.lastName;
		this.birthCity = other.birthCity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public City getBirthCity() {
		return birthCity;
	}

	public void setBirthCity(City birthCity) {
		this.birthCity = birthCity;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}
}

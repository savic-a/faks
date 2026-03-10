package iss.spring.web.dtos;

import java.io.Serializable;

import iss.spring.web.entities.relations.onetomany.Person;

public class PersonDto implements Serializable {

	private static final long serialVersionUID = -8178366724097283480L;
	private Long id;
	private String name;
	private int age;


	public PersonDto() {
	}
	
	public PersonDto(Long id, String name, int age) {
		this();
		this.id = id;
		this.name = name;
		this.age = age;
	}

	public PersonDto(Person p) {
		this();
		this.id = p.getId();
		this.name = p.getName();
		this.age = p.getAge();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "PersonDto [id=" + id + ", name=" + name + ", age=" + age + "]";
	}
	
}

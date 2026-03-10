package iss.spring.web.dtos;

import iss.spring.web.entities.relations.manytomany.Student;

public class StudentDto {
	private Long id;
	private String name;
	
	public StudentDto() {
	}

	public StudentDto(Long id, String name) {
		this();
		this.id = id;
		this.name = name;
	}
	
	public StudentDto(Student s) {
		this();
		this.id = s.getId();
		this.name = s.getName();
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

	@Override
	public String toString() {
		return "StudentDto [id=" + id + ", name=" + name + "]";
	}
}

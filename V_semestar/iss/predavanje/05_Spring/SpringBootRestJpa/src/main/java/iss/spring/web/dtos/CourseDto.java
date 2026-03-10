package iss.spring.web.dtos;

import iss.spring.web.entities.relations.manytomany.Course;

public class CourseDto {
	private Long id;
	private String name;
	
	public CourseDto() {
	}

	public CourseDto(Long id, String name) {
		this();
		this.id = id;
		this.name = name;
	}

	public CourseDto(Course c) {
		this();
		this.id = c.getId();
		this.name = c.getName();	
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
		return "CourseDto [id=" + id + ", name=" + name + "]";
	}
}

package iss.spring.web.entities.relations.manytomany;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@TableGenerator(name = "course_id_generator", table = "primary_keys", pkColumnName = "key_pk", valueColumnName = "value_pk", pkColumnValue = "course")
@Table(name = "courses")
public class Course implements java.io.Serializable {

	private static final long serialVersionUID = 4321329176664772333L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "course_id_generator")
	private Long id;
	private String name;
	@ManyToMany(cascade = {}, fetch = FetchType.EAGER, mappedBy = "courses")
	private Collection<Student> students = new ArrayList<Student>();

	public Course() {

	}

	public Course(String name) {
		this();
		this.name = name;
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

	public Collection<Student> getStudents() {
		return students;
	}

	public void setStudents(Collection<Student> students) {
		this.students = students;
	}

	// @Override
	public String toString() {
		return "(Course: " + getId() + ", Name: " + getName() + ")";
	}

}

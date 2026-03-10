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

@Entity(name = "Student2")
@TableGenerator(name = "student_id_generator", table = "primary_keys", pkColumnName = "key_pk", valueColumnName = "value_pk", pkColumnValue = "student")
@Table(name = "students")
public class Student implements java.io.Serializable {

	private static final long serialVersionUID = 885128404599049470L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "student_id_generator")
	private Long id;
	private String name;
	@ManyToMany(cascade = {}, fetch = FetchType.EAGER)
	private Collection<Course> courses = new ArrayList<Course>();

	public Student() {

	}

	public Student(String name) {
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

	public Collection<Course> getCourses() {
		return courses;
	}

	public void setCourses(Collection<Course> courses) {
		this.courses = courses;
	}

	@Override
	public String toString() {
		return "Student: " + getId() + ", Name: " + getName() + ", attends " + getCourses();
	}

}

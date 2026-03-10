package iss.spring.web.services.interfaces;

import java.util.Collection;

import iss.spring.web.entities.Student;

public interface IStudentService {
	
	public Collection<Student> getAll();
	
	public Student findStudent(Long studentId);

	public Collection<Student> findStudentsByFirstNameAndBirthCityName(String firstName, String cityName);

	public Student insert(Student student);

	public Student update(Student student);
	
	public Student delete(Long studentId);
	
	public void deleteAll();
	
}

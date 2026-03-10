package iss.spring.security.services.interfaces;

import java.util.Collection;

import iss.spring.security.entities.Student;

public interface IStudentService {
	
	public Collection<Student> getAll();
	
	public Student findStudent(Long studentId);

	public Student insert(Student student);

	public Student update(Student student);
	
	public Student delete(Long studentId);
	
	public void deleteAll();
	
}

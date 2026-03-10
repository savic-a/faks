package iss.spring.web.services.interfaces.relations;

import java.util.Collection;

import iss.spring.web.dtos.CourseDto;
import iss.spring.web.dtos.StudentDto;

public interface IStudentCourseService {
	
	public Collection<StudentDto> getAll();
	
	public Collection<CourseDto> getCoursesForStudent(Long studentId);
	
	public Collection<StudentDto> getStudentsForCourse(Long courseId);
	
	public StudentDto createStudent(String studentName);
	
	public CourseDto createCourse(String courseName);
	
	public void linkStudentAndCourse(StudentDto student, CourseDto course);
	
	public void deleteAll();

}

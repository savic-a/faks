package iss.spring.web.controllers.relations;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iss.spring.web.dtos.CourseDto;
import iss.spring.web.dtos.StudentDto;
import iss.spring.web.services.interfaces.relations.IStudentCourseService;

@RestController
@RequestMapping("/api/v2/student2")
public class ManyToManyController {

	@Autowired
	IStudentCourseService service;

	@GetMapping
	public Collection<StudentDto> getAll() {
		return service.getAll();
	}

	@GetMapping("/{studentId}")
	public Collection<CourseDto> getCoursesForStudent(@PathVariable Long studentId) {
		return service.getCoursesForStudent(studentId);
	}
	
	@GetMapping("/course/{courseId}")
	public Collection<StudentDto> getStudentsForCourse(@PathVariable Long courseId) {
		return service.getStudentsForCourse(courseId);
	}

	@PostMapping
	public void createAll() {
		
		StudentDto mika = service.createStudent("Mika");
		CourseDto analiza1 = service.createCourse("Analiza1");
		
		service.linkStudentAndCourse(mika, analiza1);
		CourseDto algebra =  service.createCourse("Algebra");
		service.linkStudentAndCourse(mika, algebra);
		
		StudentDto pera = service.createStudent("Pera");
		service.linkStudentAndCourse(pera, analiza1);
	}

	@DeleteMapping
	public void deleteAll() {
		service.deleteAll();
	}

}

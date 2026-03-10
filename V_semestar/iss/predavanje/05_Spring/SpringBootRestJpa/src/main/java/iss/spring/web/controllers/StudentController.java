package iss.spring.web.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iss.spring.web.entities.Student;
import iss.spring.web.services.interfaces.IStudentService;

@RestController
@RequestMapping("/api/v2/student")
public class StudentController {

	@Autowired
	IStudentService service;
	
	@GetMapping
	public Collection<Student> getStudents() {
		return service.getAll();
	}

	@GetMapping("/{studentId}")
	public Student findStudentById(@PathVariable Long studentId) {
		return service.findStudent(studentId);
	}

	@GetMapping("/{firstName}/{cityName}")
	public Collection<Student> findStudentByFirstNameAndBirthCityName(@PathVariable String firstName, @PathVariable String cityName) {
		return service.findStudentsByFirstNameAndBirthCityName(firstName, cityName);
	}

	@PostMapping
	public Student insert(@RequestBody Student student) {
		return service.insert(student);
	}
	
	@PutMapping
	public Student update(@RequestBody Student student) {
		return service.update(student);
	}
	
	@DeleteMapping("/{studentId}")
	public Student delete(@PathVariable Long studentId) {
		return service.delete(studentId);
	}
	
	@DeleteMapping
	public void deleteAll() {
		service.deleteAll();
	}

}

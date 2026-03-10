package iss.spring.security.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iss.spring.security.entities.Student;
import iss.spring.security.services.interfaces.IStudentService;

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

	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public Student insert(@RequestBody Student student) {
		return service.insert(student);
	}
	
	@PutMapping
	@PreAuthorize("hasRole('ADMIN')")
	public Student update(@RequestBody Student student) {
		System.out.println("UPDATE");
		return service.update(student);
	}
	
	@DeleteMapping("/{studentId}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public Student delete(@PathVariable Long studentId) {
		return service.delete(studentId);
	}
	
	@DeleteMapping
	public void deleteAll() {
		service.deleteAll();
	}

}

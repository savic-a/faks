package iss.spring.web.repositories.relations;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import iss.spring.web.dtos.CourseDto;
import iss.spring.web.dtos.StudentDto;
import iss.spring.web.entities.relations.manytomany.Student;

public interface StudentCourseRepository  extends JpaRepository<Student, Long> {
	@Query("select new iss.spring.web.dtos.StudentDto(s) from Student2 s")
	public Collection<StudentDto> findAllStudents();
	
	@Query("select new iss.spring.web.dtos.CourseDto(c.id, c.name) from Student2 s join s.courses c where s.id=:studentId")
	public Collection<CourseDto> findAllCourses(Long studentId);
	
}

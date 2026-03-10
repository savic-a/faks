package iss.spring.web.repositories.relations;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import iss.spring.web.dtos.StudentDto;
import iss.spring.web.entities.relations.manytomany.Course;

public interface CourseRepository  extends JpaRepository<Course, Long>{
	@Query("select new iss.spring.web.dtos.StudentDto(s.id, s.name) from Course c join c.students s where c.id=:courseId")
	public Collection<StudentDto> findAllStudents(Long courseId);

	@Query("delete from Course c")
	public void deleteAllCourses();
}

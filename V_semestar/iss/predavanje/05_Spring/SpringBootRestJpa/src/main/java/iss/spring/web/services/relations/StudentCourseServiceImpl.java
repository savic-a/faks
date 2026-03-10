package iss.spring.web.services.relations;

import java.util.Collection;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import iss.spring.web.dtos.CourseDto;
import iss.spring.web.dtos.StudentDto;
import iss.spring.web.entities.relations.manytomany.Course;
import iss.spring.web.entities.relations.manytomany.Student;
import iss.spring.web.repositories.relations.CourseRepository;
import iss.spring.web.repositories.relations.StudentCourseRepository;
import iss.spring.web.services.interfaces.relations.IStudentCourseService;

@Service
public class StudentCourseServiceImpl implements IStudentCourseService {

	@Autowired
	StudentCourseRepository allStudents;

	@Autowired
	CourseRepository allCourses;
	
	ResourceBundle bundle = ResourceBundle.getBundle("ValidationMessages", LocaleContextHolder.getLocale());

	@Override
	public Collection<StudentDto> getAll() {
		return allStudents.findAllStudents();
	}
	
	@Override
	public Collection<CourseDto> getCoursesForStudent(Long studentId) {
		return allStudents.findAllCourses(studentId);
	}

	public Collection<StudentDto> getStudentsForCourse(Long courseId) {
		return allCourses.findAllStudents(courseId);
	}
	
	public StudentDto createStudent(String studentName) {
		return new StudentDto(allStudents.save(new Student(studentName)));
	}
	
	public CourseDto createCourse(String courseName) {
		return new CourseDto(allCourses.save(new Course(courseName)));
	}
	
	public void linkStudentAndCourse(StudentDto student, CourseDto course) {
		Optional <Student> students = allStudents.findById(student.getId());
		if (students.isEmpty()) {
			// ne postoji student
			String value = bundle.getString("StudentCourse.notFound");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, value);
		}

		Optional <Course> courses = allCourses.findById(course.getId());
		if (courses.isEmpty()) {
			// ne postoji student
			String value = bundle.getString("CourseStudent.notFound");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, value);
		}

		Student s = students.get();
		Course c = courses.get();
		
		s.getCourses().add(c);
		c.getStudents().add(s);
		
		allStudents.save(s);
		allCourses.save(c);
	}

	@Override
	public void deleteAll() {
		allStudents.deleteAll(); // ovo će obrisati sve studente, kao i sve redove u veznoj tabeli student-course

		//allCourses.deleteAll(); <-- ovo će ipak ostaviti sve kurseve u bazi, 
		// jer ova metoda zapravo pokušava da obriše svaki pojedinačan kurs, veza sa studentima je dvosmerna, 
		// pa kursu ima i dalje studenata u kolekciji (Course.students), koji, iako su obrisani, čine da brisanje kursa ne uspe 
		allCourses.deleteAllInBatch(); // <-- ovo briše sve kurseve, jer zapravo radi nad bazom ovaj SQL: delete from Course
		// sinhronizovati keš i bazu; svakako bi se dogodilo po izlasku iz metode (transakcije)
		allCourses.flush();
	}

}

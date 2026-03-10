package iss.spring.security.services;

import java.util.Collection;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import iss.spring.security.entities.Student;
import iss.spring.security.repositories.StudentRepository;
import iss.spring.security.services.interfaces.IStudentService;

@Service
public class StudentServiceImpl implements IStudentService {
	@Autowired
	StudentRepository allStudents;
	
	ResourceBundle bundle = ResourceBundle.getBundle("ValidationMessages", LocaleContextHolder.getLocale());
	
	@Override
	public Collection<Student> getAll() {
		System.out.println(allStudents.findAll());
		return allStudents.findAll();
	}

	@Override
	public Student findStudent(Long studentId) {
		Optional<Student> found = allStudents.findById(studentId);
		if (!found.isPresent()) {
			String value = bundle.getString("notFound");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, value);
		}
		return found.get();
	}

	@Override
	public Student insert(Student student) {
		try {
			allStudents.save(student);
			allStudents.flush();
			return student;
		} catch (ConstraintViolationException ex) {
			Set<ConstraintViolation<?>> errors = ex.getConstraintViolations();
			StringBuilder sb = new StringBuilder(1000);
			for (ConstraintViolation<?> error : errors) {
				sb.append(error.getMessage() + "\n");
			}
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, sb.toString());
		}
	}

	@Override
	public Student update(Student student) {
		try {
			findStudent(student.getId()); // this will throw ResponseStatusException if student is not found
			allStudents.save(student);
			allStudents.flush();
			return student;
		} catch (RuntimeException ex) {
			Throwable e = ex;
			Throwable c = null;
		    while ((e != null) && !((c = e.getCause()) instanceof ConstraintViolationException) ) {
		      e = (RuntimeException) c;
		    }
		    if ((c != null) && (c instanceof ConstraintViolationException)) {
		        ConstraintViolationException c2 = (ConstraintViolationException) c;
		    	Set<ConstraintViolation<?>> errors = c2.getConstraintViolations();
				StringBuilder sb = new StringBuilder(1000);
				for (ConstraintViolation<?> error : errors) {
					sb.append(error.getMessage() + "\n");
				}
				throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, sb.toString());
		    }
		    throw ex;
		}
	}

	@Override
	public Student delete(Long studentId) {
		Student found = findStudent(studentId); // this will throw StudentNotFoundException if student is not found
		allStudents.delete(found);
		allStudents.flush();
		return found;
	}

	@Override
	public void deleteAll() {
		allStudents.deleteAll();
		allStudents.flush();
	}
}

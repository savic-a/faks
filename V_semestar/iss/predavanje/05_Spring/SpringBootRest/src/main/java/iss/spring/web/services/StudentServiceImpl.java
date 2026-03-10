package iss.spring.web.services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import iss.spring.web.entities.Student;
import iss.spring.web.exceptions.StudentNotFoundException;
import iss.spring.web.services.interfaces.IStudentService;

@Service
public class StudentServiceImpl implements IStudentService {

	private Map<Long, Student> allStudents = new HashMap<Long, Student>();
	
	@Override
	public Collection<Student> getAll() {
		return allStudents.values();
	}
	
	@Override
	public Student findStudent(Long studentId) {
		Student found = allStudents.get(studentId);
		if (found != null)
			return allStudents.get(studentId);
		throw new StudentNotFoundException();
	}
	
	@Override
	public Student insert(Student student) {
		int size = allStudents.size();
		student.setId((long)size + 1);
		
		allStudents.put(student.getId(), student);
		return student;
	}

	@Override
	public Student update(Student student) {
		Student found = allStudents.get(student.getId());
		if (found != null) {
			found.setFirstName(student.getFirstName());
			found.setLastName(student.getLastName());
			return found;
		}
		throw new StudentNotFoundException();
	}

	@Override
	public Student delete(Long studentId) {
		Student found = allStudents.get(studentId);
		if (found != null) {
			allStudents.remove(studentId);
			return found;
		}
		throw new StudentNotFoundException();
	}

	@Override
	public void deleteAll() {
		allStudents.clear();
	}
}

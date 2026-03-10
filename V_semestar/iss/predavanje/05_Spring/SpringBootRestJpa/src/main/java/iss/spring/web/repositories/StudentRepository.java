package iss.spring.web.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import iss.spring.web.entities.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

	@Query("select s from Student s where s.firstName like ?1 and s.birthCity.name=?2")
	public Collection<Student> findByFirstNameAndBirthCityName(String firstName, String cityName);
}

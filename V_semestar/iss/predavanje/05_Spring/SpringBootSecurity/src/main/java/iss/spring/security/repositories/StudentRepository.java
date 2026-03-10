package iss.spring.security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import iss.spring.security.entities.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

}

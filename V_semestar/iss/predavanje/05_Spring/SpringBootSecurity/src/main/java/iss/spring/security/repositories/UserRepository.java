package iss.spring.security.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import iss.spring.security.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	public Optional<User> findByUsername(String username);

}

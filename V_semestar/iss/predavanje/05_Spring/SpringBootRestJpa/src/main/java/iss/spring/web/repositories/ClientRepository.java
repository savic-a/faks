package iss.spring.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import iss.spring.web.entities.lob.Client;

public interface ClientRepository  extends JpaRepository<Client, Long> {
	
}

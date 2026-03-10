package iss.spring.web.repositories.relations;

import org.springframework.data.jpa.repository.JpaRepository;

import iss.spring.web.entities.relations.onetoone.Worker;

public interface WorkerRepository  extends JpaRepository<Worker, Long>{

}

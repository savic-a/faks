package iss.spring.web.repositories.relations;

import org.springframework.data.jpa.repository.JpaRepository;

import iss.spring.web.entities.relations.manytoone.Worker;

public interface Worker2Repository  extends JpaRepository<Worker, Long>{

}

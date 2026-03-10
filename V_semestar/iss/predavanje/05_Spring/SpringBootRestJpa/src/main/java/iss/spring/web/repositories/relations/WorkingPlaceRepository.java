package iss.spring.web.repositories.relations;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import iss.spring.web.entities.relations.manytoone.WorkingPlace;

public interface WorkingPlaceRepository  extends JpaRepository<WorkingPlace, Long>{
	public Optional<WorkingPlace> findByName(String name);
}

package iss.spring.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import iss.spring.web.entities.City;

public interface CityRepository extends JpaRepository<City, Long> {

}

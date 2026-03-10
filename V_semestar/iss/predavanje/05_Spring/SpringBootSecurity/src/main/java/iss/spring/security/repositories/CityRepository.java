package iss.spring.security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import iss.spring.security.entities.City;


public interface CityRepository extends JpaRepository<City, Long> {

}

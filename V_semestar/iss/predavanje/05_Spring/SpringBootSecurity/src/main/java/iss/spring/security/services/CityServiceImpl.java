package iss.spring.security.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iss.spring.security.entities.City;
import iss.spring.security.repositories.CityRepository;
import iss.spring.security.services.interfaces.ICityService;


@Service
public class CityServiceImpl implements ICityService {

	@Autowired
	CityRepository allCities;
	
	@Override
	public Collection<City> getAll() {
		return allCities.findAll();
	}

}

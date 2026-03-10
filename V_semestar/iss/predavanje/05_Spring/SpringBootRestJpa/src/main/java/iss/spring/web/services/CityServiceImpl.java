package iss.spring.web.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iss.spring.web.entities.City;
import iss.spring.web.repositories.CityRepository;
import iss.spring.web.services.interfaces.ICityService;

@Service
public class CityServiceImpl implements ICityService {

	@Autowired
	CityRepository allCities;
	
	@Override
	public Collection<City> getAll() {
		return allCities.findAll();
	}

}

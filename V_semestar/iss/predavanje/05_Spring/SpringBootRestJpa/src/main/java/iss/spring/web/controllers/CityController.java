package iss.spring.web.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iss.spring.web.entities.City;
import iss.spring.web.services.interfaces.ICityService;

@RestController
@RequestMapping("/api/v2/city")
public class CityController {
	@Autowired
	ICityService service;
	
	@GetMapping
	public Collection<City> getCities() {
		return service.getAll();
	}
}

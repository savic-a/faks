package iss.spring.web.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iss.spring.web.entities.inheritance.Instrument;
import iss.spring.web.services.interfaces.IInstrumentService;

@RestController
@RequestMapping("/api/v2/instrument")
public class InstrumentController {

	@Autowired
	IInstrumentService service;

	@GetMapping
	public Collection<Instrument> getAll() {
		return service.getAll();
	}

	@PostMapping
	public void createAll() {
		service.createAll();
	}

	@DeleteMapping
	public void deleteAll() {
		service.deleteAll();
	}

}

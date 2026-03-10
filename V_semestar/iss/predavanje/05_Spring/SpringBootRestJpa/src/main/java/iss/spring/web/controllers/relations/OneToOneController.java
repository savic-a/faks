package iss.spring.web.controllers.relations;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iss.spring.web.entities.relations.onetoone.Worker;
import iss.spring.web.services.interfaces.relations.IWorkerPolicyService;

@RestController
@RequestMapping("/api/v2/workerpolicy")
public class OneToOneController {

	@Autowired
	IWorkerPolicyService service;

	@GetMapping
	public Collection<Worker> getAll() {
		return service.getAll();
	}

	@PostMapping
	public void createAll() {
		service.createWorker("Pera", "Perina polisa");
		service.createWorker("Mika", "Mikina polisa");
	}

	@DeleteMapping
	public void deleteAll() {
		service.deleteAll();
	}

}

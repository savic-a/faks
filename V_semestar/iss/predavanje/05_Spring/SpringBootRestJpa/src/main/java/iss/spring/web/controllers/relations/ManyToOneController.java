package iss.spring.web.controllers.relations;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iss.spring.web.entities.relations.manytoone.Worker;
import iss.spring.web.services.interfaces.relations.IWorkerWorkingPlaceService;

@RestController
@RequestMapping("/api/v2/workerworkingplace")
public class ManyToOneController {

	@Autowired
	IWorkerWorkingPlaceService service;

	@GetMapping
	public Collection<Worker> getAll() {
		return service.getAll();
	}
	
	@GetMapping("/{workingPlace}")
	public Collection<Worker> getAllInMagacin(@PathVariable String workingPlace) {
		return service.getAllByWorkingPlaceName(workingPlace);
	}

	@PostMapping
	public void createAll() {
		service.createWorkerAndWorkingPlace("Pera", "Magacin");
		service.createWorkerAndWorkingPlace("Mika", "Portirnica");
		service.createWorkerAndWorkingPlace("Đura", "Magacin");
	}
	
	@DeleteMapping
	public void deleteAll() {
		service.deleteAll();
	}

}

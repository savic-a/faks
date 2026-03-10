package iss.spring.web.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iss.spring.web.entities.lob.Client;
import iss.spring.web.services.interfaces.IClientService;

@RestController
@RequestMapping("/api/v2/client")
public class ClientController {

	@Autowired
	IClientService service;

	@GetMapping
	public Collection<Client> getAll() {
		return service.getAll();
	}

	@PostMapping
	public Client insert(@RequestBody Client client) {
		return service.insert(client);
	}

	@DeleteMapping
	public void deleteAll() {
		service.deleteAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Resource> getImage(@PathVariable Long id) {
		Client client = service.findClient(id);
		Resource resource = new ByteArrayResource(client.getInfo().getImageData());
		String contentType = client.getInfo().getMimeType();
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(resource);
	}
}

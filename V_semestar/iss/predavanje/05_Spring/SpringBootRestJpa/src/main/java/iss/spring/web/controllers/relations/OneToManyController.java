package iss.spring.web.controllers.relations;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iss.spring.web.dtos.BankAccountDto;
import iss.spring.web.dtos.PersonDto;
import iss.spring.web.services.interfaces.relations.IPersonService;

@RestController
@RequestMapping("/api/v2/person")
public class OneToManyController {

	@Autowired
	IPersonService service;

	@GetMapping
	public Collection<PersonDto> getAll() {
		return service.getAll();
	}

	@GetMapping("/{personId}")
	public Collection<BankAccountDto> getAccountsForPerson(@PathVariable Long personId) {
		return service.getAccounts(personId);
	}

	@PostMapping
	public void createAll() {
		PersonDto pera = service.createPersonAndAccount("Pera", 30, "Devizni racun", 200);
		service.addAccount(pera.getId(), "Dinarski racun", 10000);
		
		PersonDto mika = service.createPersonAndAccount("Mika", 25, "Dinarski racun1", 20000);
		service.addAccount(mika.getId(), "Dinarski racun2", 10000);

	}

	@DeleteMapping
	public void deleteAll() {
		service.deleteAll();
	}

}

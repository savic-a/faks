package iss.spring.web.controllers.jpaql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iss.spring.web.dtos.PersonDto;
import iss.spring.web.services.interfaces.relations.IPersonService;

@RestController
@RequestMapping("/api/v2/person2")
public class JpaQlController {

	@Autowired
	IPersonService service;

	@PostMapping
	public void createAll() {
		PersonDto pera = service.createPersonAndAccount("Pera", 30, "Devizni racun", 200);
		service.addAccount(pera.getId(), "Dinarski racun", 10000);
		
		service.createPersonAndAccount("Mika", 25, "Dinarski racun", 20000);
		
		PersonDto djura= service.createPersonAndAccount("Djura", 35, "Devizni racun", 2000);
		service.addAccount(djura.getId(), "Dinarski racun", 10000);
		
		service.createPersonAndAccount("Djokica", 15, "Dinarski racun", 20);
		
		service.createPerson("Siromasni", 15);

	}
	
	@GetMapping("/queries")
	public void executeQueries() {
		service.executeQuery(">>Listamo sve osobe iz entiteta Person",
				"from Person");
		service.executeQuery(
				">>Listamo sve osobe iz entiteta Person sortirane po godistu",
				"select p from Person p order by p.age");
		service.executeQuery(">>Nadjemo osobu cije ime pocinje sa 'Pe'",
				"select p from Person p where p.name like 'Pe%'");
		
		service.executeQuery(">>Nadjemo najmanji broj godina",
				"select min(p.age) from Person p");
		service.executeQuery(">>Subquery: Nadjemo najmladju osobu",
				"select p from Person p where p.age=(select min(p.age) from Person p)");

		service.executeQuery(">>Inner join: Sve osobe sa racunom",
				"select p.name, a.name, a.amount from Person p join p.bankAccounts a");
		
		service.executeQuery(
				">>Inner join: Sve osobe koje imaju racun sa manje od 100 novcanih jedinica",
				"select p.name, a.amount from Person p join p.bankAccounts a where a.amount < 100");
		
		service.executeQuery(">>Left outer join: Sve osobe sa ili bez racuna",
				"select p.name, a.name, a.amount from Person p left join p.bankAccounts a");
		
		service.executeQuery(
				">>Fetch join (BEZ fetch=FetchType.EAGER): Sve osobe sa ili bez racuna",
				"select distinct p from Person p left join fetch p.bankAccounts");
		
		service.executeQuery(
				">>GroupBy: Izlistati najvece racune, grupisane po vrsti (dinarski ili devizni)",
				"select b.name, max(b.amount) from BankAccount b group by b.name");
		
		service.executeQuery(
				">>GroupBy sa filterom: Izlistati starosti osoba, grupisane po godinama zivota, gde je osoba mladja od 30 godina",
				"select 'Osoba stara: ' || p.age, 'komada: ' || count(p.age) from Person p group by p.age having p.age < 30");

		service.executeNativeQuery(
				">>>NativeQuery: Nadjemo osobu cije ime pocinje sa 'Pe'",
				"select id, name, age from Persons where name like 'Pe%'");


	}

}

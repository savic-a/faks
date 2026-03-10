package iss.spring.web.services.relations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import iss.spring.web.dtos.BankAccountDto;
import iss.spring.web.dtos.PersonDto;
import iss.spring.web.entities.relations.onetomany.BankAccount;
import iss.spring.web.entities.relations.onetomany.Person;
import iss.spring.web.repositories.relations.PersonRepository;
import iss.spring.web.services.interfaces.relations.IPersonService;

@Service
public class PersonServiceImpl implements IPersonService {

	@Autowired
	PersonRepository allPersons;

	ResourceBundle bundle = ResourceBundle.getBundle("ValidationMessages", LocaleContextHolder.getLocale());

	@Override
	public Collection<PersonDto> getAll() {
		return allPersons.findAllPersons();
		/*
		 * Collection<Person> persons = allPersons.findAll(); Collection<PersonDto> ret
		 * = new ArrayList<PersonDto>(); for(Person p : persons) { ret.add(new
		 * PersonDto(p)); } return ret;
		 */
	}

	@Override
	public Collection<BankAccountDto> getAccounts(Long personId) {
		return allPersons.findAllAccounts(personId);
		/*
		 * Optional <Person> persons = allPersons.findById(personId); if
		 * (persons.isEmpty()) { // ne postoji osoba String value =
		 * bundle.getString("Person.notFound"); throw new
		 * ResponseStatusException(HttpStatus.NOT_FOUND, value); } Person p =
		 * persons.get(); Collection<BankAccountDto> ret = new
		 * ArrayList<BankAccountDto>(); for (BankAccount a : p.getBankAccounts()) {
		 * ret.add(new BankAccountDto(a)); } return ret;
		 */
	}

	@Override
	public PersonDto createPersonAndAccount(String personName, int age, String accountName, double amount) {
		Person p = new Person(personName, age);
		BankAccount acc = new BankAccount(accountName, p, amount);
		Collection<BankAccount> accounts = new ArrayList<BankAccount>();
		accounts.add(acc);
		p.setBankAccounts(accounts);

		return new PersonDto(allPersons.save(p));
	}

	@Override
	public PersonDto createPerson(String personName, int age) {
		Person p = new Person(personName, age);

		return new PersonDto(allPersons.save(p));
	}

	@Override
	public BankAccountDto addAccount(Long personId, String accountName, double amount) {
		Optional<Person> persons = allPersons.findById(personId);
		if (persons.isEmpty()) {
			// ne postoji osoba
			String value = bundle.getString("Person.notFound");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, value);
		}

		Person p = persons.get();

		BankAccount acc = new BankAccount(accountName, p, amount);
		p.getBankAccounts().add(acc);
		// snimimo izmenu
		allPersons.save(p);

		return new BankAccountDto(acc);

	}

	@Override
	public void deleteAll() {
		allPersons.deleteAll();
	}

	@Autowired
	EntityManager em;

	@Override
	public void executeQuery(String title, String qr) {
		System.err.println(title);
		System.err.println(qr);
		Query q = em.createQuery(qr);
		@SuppressWarnings("rawtypes")
		List res = q.getResultList();
		for (Object p : res) {
			if (p instanceof Object[]) {
				String acc = "";
				for (Object o : (Object[]) p) {
					if (o == null)
						acc += "<null>, ";
					else
						acc += o.toString() + ", ";
				}
				System.out.println(acc);
			} else
				System.out.println(p);
		}
		try {Thread.sleep(100);} catch (Exception ex) {}
	}

	@Override
	public void executeNativeQuery(String title, String qr) {
		System.err.println(title);
		System.err.println(qr);
		Query q = em.createNativeQuery(qr, Person.class);
		@SuppressWarnings("unchecked")
		List<Person> res = q.getResultList();
		for (Person p : res) {
			System.out.println(p);
		}
		try {Thread.sleep(100);} catch (Exception ex) {}
	}

}

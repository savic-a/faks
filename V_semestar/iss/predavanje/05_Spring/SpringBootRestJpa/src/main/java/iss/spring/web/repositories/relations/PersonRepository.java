package iss.spring.web.repositories.relations;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import iss.spring.web.dtos.BankAccountDto;
import iss.spring.web.dtos.PersonDto;
import iss.spring.web.entities.relations.onetomany.Person;

public interface PersonRepository  extends JpaRepository<Person, Long>{
	
	@Query("select new iss.spring.web.dtos.PersonDto(p) from Person p")
	public Collection<PersonDto> findAllPersons();
	
	@Query("select new iss.spring.web.dtos.BankAccountDto(a) from BankAccount a where a.holder.id=:personId")
	public Collection<BankAccountDto> findAllAccounts(Long personId);
	
	@Query("select p from Person p where p.age < 18")
	public Collection<PersonDto> findUnderAged();
}

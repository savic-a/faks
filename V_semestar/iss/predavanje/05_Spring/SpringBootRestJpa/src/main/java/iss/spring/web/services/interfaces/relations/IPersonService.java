package iss.spring.web.services.interfaces.relations;

import java.util.Collection;

import iss.spring.web.dtos.BankAccountDto;
import iss.spring.web.dtos.PersonDto;

public interface IPersonService {
	
	public Collection<PersonDto> getAll();
	
	public PersonDto createPersonAndAccount(String personName, int age, String accountName, double amount);

	public PersonDto createPerson(String personName, int age);
	
	public BankAccountDto addAccount(Long personId, String accountName, double amount);
	
	public Collection<BankAccountDto> getAccounts(Long personId);

	public void deleteAll();

	public void executeQuery(String string, String string2);

	public void executeNativeQuery(String string, String string2);
}

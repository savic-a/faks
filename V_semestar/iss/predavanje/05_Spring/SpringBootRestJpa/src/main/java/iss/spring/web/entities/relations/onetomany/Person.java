package iss.spring.web.entities.relations.onetomany;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@TableGenerator(name = "person_id_generator", table = "primary_keys", pkColumnName = "key_pk", valueColumnName = "value_pk", pkColumnValue = "person")
@Table(name = "persons")
public class Person implements java.io.Serializable {
	private static final long serialVersionUID = -3196720606791620413L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "person_id_generator")
	private Long id;
	private String name;
	private int age;
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "holder")
	private Collection<BankAccount> bankAccounts;

	public Person() {
	}

	public Person(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<BankAccount> getBankAccounts() {
		return bankAccounts;
	}

	public void setBankAccounts(Collection<BankAccount> acc) {
		this.bankAccounts = acc;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String toString() {
		String retVal = "Person id: " + id + ", name: " + name + " (age: " + age + ") has following accounts: ";
		if (bankAccounts != null)
			for (BankAccount acc : bankAccounts) {
				retVal += acc.toString();
			}
		else
			retVal += "()";
		return retVal;
	}
}

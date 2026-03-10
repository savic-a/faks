package iss.spring.web.entities.relations.onetomany;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@TableGenerator(name = "account_id_generator", table = "primary_keys", pkColumnName = "key_pk", valueColumnName = "value_pk", pkColumnValue = "account")
@Table(name = "accounts")
public class BankAccount implements java.io.Serializable {

	private static final long serialVersionUID = -4064017080322165011L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "account_id_generator")
	private Long id;
	private String name;
	private double amount;
	@ManyToOne(cascade = {})
	private Person holder;

	public BankAccount() {
	}

	public BankAccount(String name, Person holder, double amount) {
		this.name = name;
		this.holder = holder;
		this.amount = amount;
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

	public Person getHolder() {
		return holder;
	}

	public void setHolder(Person holder) {
		this.holder = holder;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String toString() {
		return "(Bank account: id: " + id + ", name: " + name + ", amount: " + amount + ")";
	}
}

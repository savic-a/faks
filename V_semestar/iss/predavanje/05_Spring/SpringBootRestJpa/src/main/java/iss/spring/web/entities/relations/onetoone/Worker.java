package iss.spring.web.entities.relations.onetoone;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@TableGenerator(name = "worker_id_generator", table = "primary_keys", pkColumnName = "key_pk", valueColumnName = "value_pk", pkColumnValue = "worker")
@Table(name = "workers")
public class Worker implements Serializable {

	private static final long serialVersionUID = -4678284446111348508L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "worker_id_generator")
	private int id;
	private String name;
	@OneToOne(cascade = { CascadeType.ALL })
	private InsurancePolicy policy;

	public Worker() {

	}

	public Worker(String name) {
		this.name = name;
		System.out.println("Created a worker with name: " + name);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public InsurancePolicy getPolicy() {
		return policy;
	}

	public void setPolicy(InsurancePolicy policy) {
		this.policy = policy;
	}

	@Override
	public String toString() {
		return "Worker: " + getId() + ", Name: " + getName() + ", Policy: " + getPolicy();
	}

}

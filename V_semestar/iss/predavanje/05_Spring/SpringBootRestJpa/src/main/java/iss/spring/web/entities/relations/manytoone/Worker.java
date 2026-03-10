package iss.spring.web.entities.relations.manytoone;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity(name = "Worker2")
@TableGenerator(name = "worker_id_generator", table = "primary_keys", pkColumnName = "key_pk", valueColumnName = "value_pk", pkColumnValue = "worker")
@Table(name = "workers2")
public class Worker implements Serializable {

	private static final long serialVersionUID = -7229466636916555121L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "worker_id_generator")
	private int id;
	private String name;
	@ManyToOne(cascade = { CascadeType.REFRESH })
	private WorkingPlace workingPlace;

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

	public WorkingPlace getWorkingPlace() {
		return workingPlace;
	}

	public void setWorkingPlace(WorkingPlace place) {
		this.workingPlace = place;
	}

	// @Override
	public String toString() {
		return "Worker2: " + getId() + ", Name: " + getName() + ", works at" + getWorkingPlace();
	}

}

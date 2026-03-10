package iss.spring.web.entities.relations.manytoone;

import java.io.Serializable;
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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@TableGenerator(name = "place_id_generator", table = "primary_keys", pkColumnName = "key_pk", valueColumnName = "value_pk", pkColumnValue = "place")
@Table(name = "working_places")
public class WorkingPlace implements Serializable {

	private static final long serialVersionUID = 8498968694350507184L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "place_id_generator")
	private int id;
	private String name;
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, mappedBy = "workingPlace")
	@JsonIgnore
	private Collection<Worker> workers;

	public WorkingPlace() {

	}

	public WorkingPlace(String name) {
		this.name = name;
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

	public Collection<Worker> getWorkers() {
		return workers;
	}

	public void setWorkers(Collection<Worker> workers) {
		this.workers = workers;
	}

	// @Override
	public String toString() {
		return "(Working place: " + getId() + ", Name: " + getName() + ")";
	}

}

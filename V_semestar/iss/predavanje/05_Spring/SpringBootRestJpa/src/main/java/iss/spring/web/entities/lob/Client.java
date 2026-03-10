package iss.spring.web.entities.lob;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Client {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@Embedded
	private ClientInfo info;

	public Client() {

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

	public ClientInfo getInfo() {
		return info;
	}

	public void setInfo(ClientInfo info) {
		this.info = info;
	}

	@Override
	public String toString() {
		return "Client [id=" + id + ", name=" + name + ", info=" + info + "]";
	}

}

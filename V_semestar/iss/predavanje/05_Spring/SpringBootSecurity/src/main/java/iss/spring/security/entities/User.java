package iss.spring.security.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Long id;
	
	private String username;
	
	private String password;
	
	private Role role;
	
	@Transient
	private String jwt;
	
	public User() {
		this("anonymousUser", "anonymousUser", Role.ANONYMOUS);
	}

	public User(String username, String password, Role role) {
		this.username = username;
		this.password = password;
		this.role = role;
	}
	
	public User(String username, String password, Role role, String jwt) {
		this(username, password, role);
		this.jwt = jwt;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role + "]";
	}

}

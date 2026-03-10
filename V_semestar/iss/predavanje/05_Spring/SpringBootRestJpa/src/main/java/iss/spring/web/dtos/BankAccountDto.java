package iss.spring.web.dtos;

import iss.spring.web.entities.relations.onetomany.BankAccount;

public class BankAccountDto {
	private Long id;
	private String name;
	private double amount;
	
	public BankAccountDto() {
	}

	public BankAccountDto(Long id, String name, double amount) {
		this();
		this.id = id;
		this.name = name;
		this.amount = amount;
	}

	public BankAccountDto(BankAccount a) {
		this.id = a.getId();
		this.name = a.getName();
		this.amount = a.getAmount();
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

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "BankAccountDto [id=" + id + ", name=" + name + ", amount=" + amount + "]";
	}
	
}

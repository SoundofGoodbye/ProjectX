package com.trader.application.rest.resources;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.trader.application.core.models.entities.Account;

public class AccountResource extends ResourceSupport {

	private String firstName;

	private String middleName;

	private String lastName;
	
	private String email;

	private String password;
	
	private String school;
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	// this will be ignored when building json object so we don't pass the
	// password around.
	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}

	public Account toAccount() {
		Account account = new Account();
		account.setFirstName(firstName);
		account.setMiddleName(middleName);
		account.setLastName(lastName);
		account.setSchool(school);
		account.setEmail(email);
		account.setPassword(password);

		return account;
	}

}

package com.example.springboot.dto;

import com.example.springboot.model.User;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

public class UserDTO {
	private Long id;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthDate;
	private ArrayList<String> roles;

	public UserDTO() {
	}

	public UserDTO(
		Long id, String email, String firstName, String lastName, Date birthDate, String[] roles, String password
	) {
		this.id = id;
		this.email = email;
		this.firstName = firstName;
		this.password = password;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.roles = new ArrayList<>(Arrays.asList(roles));
	}


	public UserDTO(User user) {
		this.id = user.getId();
		this.email = user.getEmail();
		this.password = null;
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.birthDate = user.getBirthDate();
		this.roles = user
			.getRoles()
			.stream()
			.map(role -> role.getName())
			.collect(Collectors.toCollection(ArrayList::new));
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public ArrayList<String> getRoles() {
		return roles;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public void setRoles(ArrayList<String> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "UserDTO{" +
			   "id=" + id +
			   ", email='" + email + '\'' +
			   ", password='" + password + '\'' +
			   ", firstName='" + firstName + '\'' +
			   ", lastName='" + lastName + '\'' +
			   ", birthDate=" + birthDate +
			   ", roles=" + roles +
			   '}';
	}
}

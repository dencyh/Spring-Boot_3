package com.example.springboot.model;

import com.example.springboot.dto.UserDTO;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.sql.Date;

@Entity
@Table(name = "users")
public class User implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "firstName", nullable = false)
	private String firstName;

	@Column(name = "lastName", nullable = false)
	private String lastName;

	@Column(name = "birthDate", nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthDate;

	@ManyToMany(
		fetch = FetchType.LAZY,
		cascade = {CascadeType.PERSIST, CascadeType.MERGE}
	)
	@JoinTable(
		name = "roles_users",
		joinColumns = @JoinColumn(name = "userId", referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(name = "roleId", referencedColumnName = "id")
	)
	private Collection<Role> roles;

	public User() {
	}

	public User(String email, String password, String firstName, String lastName, Date birthDate) {
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
	}

	public User(UserDTO userDTO) {
		this.id = userDTO.getId();
		this.firstName = userDTO.getFirstName();
		this.lastName = userDTO.getLastName();
		this.email = userDTO.getEmail();
		this.password = userDTO.getPassword();
		this.birthDate = new Date(userDTO.getBirthDate().getTime());
		this.roles = userDTO.getRoles().stream().map(Role::new).toList();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public String[] getRolesNames() {
		return getRoles().stream().map(Role::getName).toArray(String[]::new);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getRoles();
	}

	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String toString() {
		return "{id = " + id + "\nemail = " + email + "\nfirstName = " + firstName + "\nlastName = " + lastName +
			   "\nbirthDate = " + birthDate + "}";
	}
}

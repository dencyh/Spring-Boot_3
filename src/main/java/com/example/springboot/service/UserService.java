package com.example.springboot.service;

import com.example.springboot.exception.UserAlreadyExistsException;
import com.example.springboot.model.User;
import com.example.springboot.repository.RoleRepository;
import com.example.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserService {
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public UserService(
		UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder
	) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public User getUserById(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	@Transactional
	public void saveUser(User user) {
		User userFromDb = userRepository.findByEmail(user.getEmail());

		if (userFromDb != null) {
			throw new UserAlreadyExistsException(String.format("User with email %s already exists", user.getEmail()));
		}

		addRole(user);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}

	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Transactional
	public void deleteUserById(Long id) {
		userRepository.deleteById(id);
	}

	@Transactional
	public void updateUser(User user) {
		addRole(user);

		if (user.getPassword() != null && !Objects.equals(user.getPassword(), "******")) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
		} else {
			userRepository.findById(user.getId()).ifPresent(userFromDb -> user.setPassword(userFromDb.getPassword()));
		}

		userRepository.save(user);
	}

	public void addRole(User user) {
		// Add roles if any were selected. If no roles were selected add USER role by default
		if (user.getRoles().size() == 0) {
			user.setRoles(Collections.singleton(roleRepository.findByName("USER")));
		} else {
			user.setRoles(user
							  .getRoles()
							  .stream()
							  .map(role -> roleRepository.findByName(role.getName()))
							  .collect(Collectors.toList()));
		}
	}
}

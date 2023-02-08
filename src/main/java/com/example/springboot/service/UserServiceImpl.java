package com.example.springboot.service;

import com.example.springboot.exception.UserAlreadyExistsException;
import com.example.springboot.model.User;
import com.example.springboot.repository.RoleRepository;
import com.example.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public UserServiceImpl(
		UserRepository userRepository, RoleRepository roleRepository,
		BCryptPasswordEncoder passwordEncoder
	) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public User getUserById(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
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

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	@Transactional
	public void deleteUserById(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	@Transactional
	public void updateUser(User user) {
		addRole(user);

		// Update current user authorities
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = (User) authentication.getPrincipal();
		if (currentUser.getId().equals(user.getId())) {
			List<GrantedAuthority> updatedAuthorities = new ArrayList<>(user.getAuthorities());
			Authentication newAuth = new UsernamePasswordAuthenticationToken(
				currentUser,
				authentication.getCredentials(),
				updatedAuthorities
			);
			SecurityContextHolder.getContext().setAuthentication(newAuth);
		}

		if (user.getPassword() != null && Objects.equals(user.getPassword(), "******")) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
		} else {
			userRepository
				.findById(user.getId())
				.ifPresent(userFromDb -> user.setPassword(userFromDb.getPassword()));
		}

		userRepository.save(user);
	}

	private void addRole(User user) {
		// Add roles if any were selected. If no roles were selected add USER role by default
		if (user.getRolesArray().length == 0) {
			user.setRoles(Collections.singleton(roleRepository.findByName("USER")));
		} else {
			user.setRoles(Arrays
							  .stream(user.getRolesArray())
							  .map(roleRepository::findByName)
							  .collect(Collectors.toList()));
		}
	}
}

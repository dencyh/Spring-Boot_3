package com.example.springboot.service;

import com.example.springboot.model.User;
import com.example.springboot.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User getUserById(long id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void saveUser(User user) {
		userRepository.save(user);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	@Transactional
	public void deleteUserById(long id) {
		userRepository.deleteById(id);
	}

	@Override
	@Transactional
	public void updateUser(User user) {
		userRepository.save(user);
	}
}

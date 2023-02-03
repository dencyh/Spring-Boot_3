package com.example.springboot.service;

import com.example.springboot.dao.UserDao;
import com.example.springboot.model.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
	private final UserDao userDao;

	@Autowired
	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public User getUserById(long id) {
		return userDao.getUserById(id);
	}

	@Override
	@Transactional
	public void saveUser(User user) {
		userDao.saveUser(user);
	}

	@Override
	public List<User> getAllUsers() {
		return userDao.getAllUsers();
	}

	@Override
	@Transactional
	public void deleteUserById(long id) {
		userDao.deleteUserById(id);
	}

	@Override
	@Transactional
	public void updateUser(User user) {
		userDao.updateUser(user);
	}
}

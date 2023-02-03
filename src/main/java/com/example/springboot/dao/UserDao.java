package com.example.springboot.dao;


import com.example.springboot.model.User;

import java.util.List;

public interface UserDao {
	User getUserById(long id);

	void saveUser(User user);

	List<User> getAllUsers();


	void deleteUserById(long id);

	void updateUser(User user);
}

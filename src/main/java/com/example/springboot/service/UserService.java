package com.example.springboot.service;


import com.example.springboot.model.User;

import java.util.List;

public interface UserService {
    void saveUser(User user);

    List<User> getAllUsers();

    User getUserById(long id);

    void deleteUserById(long id);

    void updateUser( User user);
}

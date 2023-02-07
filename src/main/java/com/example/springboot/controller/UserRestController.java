package com.example.springboot.controller;

import com.example.springboot.model.User;
import com.example.springboot.service.UserService;
import com.example.springboot.utils.AppLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin/users")
public class UserRestController {

	private final UserService userService;

	@Autowired
	public UserRestController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping(value = "/{id}", produces = "application/json")
	public User getUser(@PathVariable Long id) {
		return userService.getUserById(id);
	}

	@GetMapping(produces = "application/json")
	public List<User> getAllUser() {
		return userService.getAllUsers();
	}

	@PatchMapping(value = "/{id}", consumes = "application/json")
	public ResponseEntity<HttpStatus> updateUserById(@PathVariable Long id, @RequestBody User user) {
		AppLogger.error("PATCH request");
		AppLogger.info(id);
		AppLogger.info(user.getFirstName());
		return ResponseEntity.ok(HttpStatus.OK);
	}
}

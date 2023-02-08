package com.example.springboot.controller;

import com.example.springboot.model.User;
import com.example.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin/users")
public class AdminRestController {

	private final UserService userService;

	@Autowired
	public AdminRestController(UserService userService) {
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
	public ResponseEntity<HttpStatus> updateUserById(@RequestBody User user) {
		userService.updateUser(user);
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@PostMapping(value = "/new", consumes = "application/json")
	public ResponseEntity<HttpStatus> addUser(@RequestBody User user) {
		userService.saveUser(user);
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
		userService.deleteUserById(id);
		return ResponseEntity.ok(HttpStatus.OK);
	}
}

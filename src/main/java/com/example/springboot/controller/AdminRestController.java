package com.example.springboot.controller;

import com.example.springboot.dto.UserDTO;
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
	public UserDTO getUser(@PathVariable Long id) {
		return new UserDTO(userService.getUserById(id));
	}

	@GetMapping(produces = "application/json")
	public List<UserDTO> getAllUser() {
		return userService.getAllUsers().stream().map(UserDTO::new).toList();
	}

	@PatchMapping(value = "/{id}", consumes = "application/json")
	public ResponseEntity<HttpStatus> updateUserById(@RequestBody UserDTO userDTO) {
		userService.updateUser(new User(userDTO));
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@PostMapping(value = "/new", consumes = "application/json")
	public ResponseEntity<HttpStatus> addUser(@RequestBody UserDTO userDTO) {
		userService.saveUser(new User(userDTO));
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
		userService.deleteUserById(id);
		return ResponseEntity.ok(HttpStatus.OK);
	}
}



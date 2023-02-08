package com.example.springboot.controller;

import com.example.springboot.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserRestController {
	@GetMapping(produces = "application/json")
	public User getCurrentUser(Authentication authentication) {
		User currentUser = (User) authentication.getPrincipal();
		return currentUser;
	}
}

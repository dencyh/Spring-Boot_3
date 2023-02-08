package com.example.springboot.controller;

import com.example.springboot.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
	@GetMapping
	public String userPage(ModelMap model, Authentication authentication) {
		model.addAttribute("currentUser", (User) authentication.getPrincipal());
		return "user";
	}
}

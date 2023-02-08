package com.example.springboot.controller;

import com.example.springboot.model.User;
import com.example.springboot.service.RoleService;
import com.example.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private final UserService userService;
	private final RoleService roleService;

	@Autowired
	public AdminController(UserService userService, RoleService roleService) {
		this.userService = userService;
		this.roleService = roleService;
	}

	@GetMapping
	public String getUserList(ModelMap model) {
		model.addAttribute("roles", roleService.findAll());
		return "admin";
	}

	@PatchMapping(value = "/{id}/update")
	public String updateUser(@ModelAttribute User user) {
		userService.updateUser(user);
		return "redirect:/admin";
	}
}

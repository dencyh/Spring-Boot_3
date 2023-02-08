package com.example.springboot.controller;

import com.example.springboot.model.User;
import com.example.springboot.service.RoleService;
import com.example.springboot.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
	private final UserService userService;
	private final RoleService roleService;

	public AdminController(UserService userService, RoleService roleService) {
		this.userService = userService;
		this.roleService = roleService;
	}

	@GetMapping
	public String getUserList(ModelMap model) {
		model.addAttribute("newUser", new User());
		model.addAttribute("users", userService.getAllUsers());
		model.addAttribute("roles", roleService.findAll());
		return "admin";
	}

	@PostMapping
	public String addNewUser(@ModelAttribute User user) {
		userService.saveUser(user);
		return "redirect:/admin";
	}

	@DeleteMapping(value = "/{id}")
	public String deleteUser(@PathVariable Long id) {
		userService.deleteUserById(id);
		return "redirect:/admin";
	}

	@GetMapping(value = "/{id}/edit")
	public String editUser(ModelMap model, @PathVariable Long id) {
		model.addAttribute("user", userService.getUserById(id));
		model.addAttribute("roles", roleService.findAll());
		return "edit";
	}

	@PatchMapping(value = "/{id}/update")
	public String updateUser(@ModelAttribute User user) {
		userService.updateUser(user);
		return "redirect:/admin";
	}
}

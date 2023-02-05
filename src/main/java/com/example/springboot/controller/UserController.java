package com.example.springboot.controller;

import com.example.springboot.model.Role;
import com.example.springboot.model.User;
import com.example.springboot.service.RoleService;
import com.example.springboot.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
	private final UserService userService;
	private final RoleService roleService;

	@Autowired
	public UserController(UserService userService, RoleService roleService) {
		this.userService = userService;
		this.roleService = roleService;
	}

	@GetMapping(value = "/admin")
	public String getUserList(ModelMap model) {
		model.addAttribute("newUser", new User());
		model.addAttribute("users", userService.getAllUsers());
		model.addAttribute("roles", roleService.findAll());
		return "admin";
	}

	@GetMapping(value = "/admin/new")
	public String showNewForm(Model model) {
		model.addAttribute("newUser", new User());
		return "new";
	}

	@PostMapping(value = "/admin")
	public String addNewUser(@ModelAttribute User user) {
		userService.saveUser(user);
		return "redirect:/";
	}

	@DeleteMapping(value ="/admin/{id}")
	public String deleteUser(@PathVariable long id) {
		userService.deleteUserById(id);
		return "redirect:/";
	}

	@GetMapping(value = "/admin/{id}/edit")
	public String editUser(Model model, @PathVariable long id) {
		model.addAttribute(userService.getUserById(id));
		return "edit";
	}

	@PatchMapping(value  = "/admin/{id}/update")
	public String updateUser(@ModelAttribute User user) {
		userService.updateUser(user);
		return "redirect:/";
	}
}

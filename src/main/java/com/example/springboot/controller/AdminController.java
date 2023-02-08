package com.example.springboot.controller;

import com.example.springboot.dto.UserDTO;
import com.example.springboot.model.User;
import com.example.springboot.service.RoleService;
import com.example.springboot.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
		model.addAttribute("newUser", new UserDTO());
		model.addAttribute("user", new UserDTO());
		model.addAttribute("users", userService.getAllUsers().stream().map(UserDTO::new).toList());
		model.addAttribute("roles", roleService.findAll());
		return "admin";
	}

	@PostMapping
	public String addNewUser(@ModelAttribute("newUser") UserDTO userDTO) {
		userService.saveUser(new User(userDTO));
		return "redirect:/admin";
	}

	@DeleteMapping(value = "/{id}")
	public String deleteUser(@PathVariable Long id) {
		userService.deleteUserById(id);
		return "redirect:/admin";
	}


	@PatchMapping(value = "/{id}/update")
	public String updateUser(@ModelAttribute("user") UserDTO userDTO) {
		userService.updateUser(new User(userDTO));
		return "redirect:/admin";
	}
}

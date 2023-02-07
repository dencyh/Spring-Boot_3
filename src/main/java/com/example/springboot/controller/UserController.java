package com.example.springboot.controller;

import com.example.springboot.model.User;
import com.example.springboot.service.RoleService;
import com.example.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


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

	@GetMapping(value = "/user")
	public String userPage(ModelMap model, Authentication authentication) {
		model.addAttribute("currentUser", (User) authentication.getPrincipal());
		return "user";
	}

	@PostMapping(value = "/admin")
	public String addNewUser(@ModelAttribute User user) {
		userService.saveUser(user);
		return "redirect:/admin";
	}

	@DeleteMapping(value = "/admin/{id}")
	public String deleteUser(@PathVariable Long id) {
		userService.deleteUserById(id);
		return "redirect:/admin";
	}

	@GetMapping(value = "/admin/{id}/edit")
	public String editUser(ModelMap model, @PathVariable Long id) {
		model.addAttribute("user", userService.getUserById(id));
		model.addAttribute("roles", roleService.findAll());
		return "edit";
	}

	@PatchMapping(value = "/admin/{id}/update")
	public String updateUser(@ModelAttribute User user, Authentication authentication) {
		userService.updateUser(user);
		User currentUser = (User) authentication.getPrincipal();

		if (currentUser.getId().equals(user.getId())) {
			List<GrantedAuthority> updatedAuthorities = new ArrayList<>(user.getAuthorities());
			Authentication newAuth = new UsernamePasswordAuthenticationToken(
				currentUser,
				authentication.getCredentials(),
				updatedAuthorities
			);
			SecurityContextHolder.getContext().setAuthentication(newAuth);
		}

		return "redirect:/admin";
	}
}

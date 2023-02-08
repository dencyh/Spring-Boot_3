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
	public String updateUser(@ModelAttribute User user, Authentication authentication) {
		userService.updateUser(user);
		User currentUser = (User) authentication.getPrincipal();

		if (currentUser.getId().equals(user.getId())) {
			List<GrantedAuthority> updatedAuthorities = new ArrayList<>(user.getAuthorities());
			Authentication newAuth = new UsernamePasswordAuthenticationToken(currentUser,
																			 authentication.getCredentials(),
																			 updatedAuthorities);
			SecurityContextHolder.getContext().setAuthentication(newAuth);
		}

		return "redirect:/admin";
	}
}

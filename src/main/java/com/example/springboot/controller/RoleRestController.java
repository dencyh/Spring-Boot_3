package com.example.springboot.controller;

import com.example.springboot.model.Role;
import com.example.springboot.model.User;
import com.example.springboot.service.RoleService;
import com.example.springboot.utils.AppLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/roles")
public class RoleRestController {

	private final RoleService roleService;

	@Autowired
	public RoleRestController(RoleService roleService) {
		this.roleService = roleService;
	}

	@GetMapping(produces = "application/json")
	public List<Role> getUser() {
		return roleService.findAll();
	}


	@PatchMapping(produces = "application/json")
	public ResponseEntity<HttpStatus> updateUserById() {
		AppLogger.error("PATCH request");
		return ResponseEntity.ok(HttpStatus.OK);
	}
}

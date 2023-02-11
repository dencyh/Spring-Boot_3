package com.example.springboot.config;

import com.example.springboot.model.Role;
import com.example.springboot.model.User;
import com.example.springboot.service.RoleService;
import com.example.springboot.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class AppStartupConfig {
	private final UserService userService;
	private final RoleService roleService;

	private final List<Role> initRoles = new ArrayList<>(List.of(new Role("ADMIN"), new Role("USER")));

	public AppStartupConfig(
		UserService userService, RoleService roleService
	) {
		this.userService = userService;
		this.roleService = roleService;
	}

	@PostConstruct
	public void initRolesAndAdmin() {
		List<Role> roles = roleService.findAll();
		if (roles.size() == 0) {
			roleService.saveAllRoles(initRoles);
		}
		User adminFromDb = userService.getUserByEmail("admin@mail.ru");

		if (adminFromDb == null) {
			User admin = new User("admin@mail.ru", "admin", "admin", "admin", Date.valueOf("2000-12-31"));
			admin.setRoles(Collections.singleton(roleService.findByName("ADMIN")));
			userService.saveUser(admin);
		}
	}
}

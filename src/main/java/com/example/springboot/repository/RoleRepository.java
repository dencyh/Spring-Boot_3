package com.example.springboot.repository;

import com.example.springboot.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByName(String name);
}

package com.example.springboot.service;

import com.example.springboot.model.Role;
import com.example.springboot.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
	private final RoleRepository roleRepository;

	@Autowired
	public RoleService(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	public List<Role> findAll() {
		return roleRepository.findAll();
	}

	public Role findOne(Long id) {
		return roleRepository.findById(id).orElse(null);
	}
}

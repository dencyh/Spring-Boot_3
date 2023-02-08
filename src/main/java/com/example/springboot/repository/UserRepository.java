package com.example.springboot.repository;


import com.example.springboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	@Query(value = "from User user join fetch user.roles where user.email= :email")
	User findByEmail(String email);

	@Query(value = "from User user join fetch user.roles")
	List<User> findAll();
}

package com.example.springboot.dao;

import com.example.springboot.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
	@PersistenceContext(unitName = "main")
	private EntityManager entityManager;

	@Override
	public User getUserById(long id) {
		return entityManager.find(User.class, id);
	}

	@Override
	public void saveUser(User user) {
		entityManager.persist(user);
	}

	@Override
	public List<User> getAllUsers() {
		return entityManager
			.createQuery("from User", User.class)
			.getResultList();
	}

	@Override
	public void deleteUserById(long id) {
		entityManager
			.createQuery("delete User u where u.id = :id")
			.setParameter("id", id)
			.executeUpdate();
	}

	@Override
	public void updateUser(User user) {
		entityManager.merge(user);
	}
}

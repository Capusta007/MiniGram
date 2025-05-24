package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import model.User;

public class UserRepository {
	private final EntityManager em;

	public UserRepository(EntityManager em) {
		this.em = em;
	}

	public User findById(Long id) {
		return em.find(User.class, id);
	}

	public User findByEmail(String email) {
		try {
			return em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
					.setParameter("email", email).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public User findByUsername(String username) {
		try {
			return em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
					.setParameter("username", username).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public void save(User user) {
		try {
			em.getTransaction().begin();
			em.persist(user);
			em.getTransaction().commit();
		} catch (RuntimeException e) {
			em.getTransaction().rollback();
			throw e;
		}
	}

	public void update(User user) {
		try {
			em.getTransaction().begin();
			em.merge(user);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}
	}

	public void delete(User user) {
		if (user == null) {
			return;
		}
		
		try {
			em.getTransaction().begin();
			if (!em.contains(user)) {
				user = em.merge(user);
			}
			em.remove(user);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}
	}
}

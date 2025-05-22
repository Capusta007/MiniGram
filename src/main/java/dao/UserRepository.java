package dao;

import jakarta.persistence.EntityManager;
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
		return em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
				.setParameter("email", email)
				.getSingleResult();
	}
	
	public User findByUsername(String username) {
		return em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
				.setParameter("email", username)
				.getSingleResult();
	}
	
	public void save(User user) {
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
	}
	
	public void update(User user) {
		em.getTransaction().begin();
		em.merge(user);
		em.getTransaction().commit();
	}
	
	public void delete(User user) {
		em.getTransaction().begin();
		if(!em.contains(user)) {
			user = em.merge(user);
		}
		em.remove(user);
		em.getTransaction().commit();
	}
}

package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import model.User;

/**
 * Repository class for managing {@link User} entities. Provides methods to
 * perform CRUD operations on users.
 */
public class UserRepository {
	private final EntityManager em;

	/**
	 * Constructs a new {@code UserRepository} with the given {@link EntityManager}.
	 *
	 * @param em the entity manager to be used for database operations
	 */
	public UserRepository(EntityManager em) {
		this.em = em;
	}

	/**
	 * Finds a user by their ID.
	 *
	 * @param id the ID of the user
	 * @return the user with the given ID, or {@code null} if not found
	 */
	public User findById(Long id) {
		return em.find(User.class, id);
	}

	/**
	 * Finds a user by their email address.
	 * 
	 * @param email the email address of the user.
	 * @return the user with the given email address, or {@code null} if not found
	 */
	public User findByEmail(String email) {
		try {
			return em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
					.setParameter("email", email).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * Finds a user by their username.
	 *
	 * @param username the username of the user
	 * @return the user with the given username, or {@code null} if not found
	 */
	public User findByUsername(String username) {
		try {
			return em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
					.setParameter("username", username).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * Saves a new user to the database. Wraps the operation in a transaction.
	 *
	 * @param user the user to save
	 * @throws RuntimeException if an error occurs during the transaction
	 */
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

	/**
	 * Updates an existing user in the database. Wraps the operation in a
	 * transaction.
	 *
	 * @param user the user to update
	 * @throws Exception if an error occurs during the transaction
	 */
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

	/**
	 * Deletes a user from the database. If the entity is not managed, it is first
	 * merged. Wraps the operation in a transaction.
	 *
	 * @param user the user to delete; no action is taken if {@code null}
	 * @throws Exception if an error occurs during the transaction
	 */
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

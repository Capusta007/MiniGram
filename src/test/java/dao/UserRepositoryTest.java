package dao;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.User;

class UserRepositoryTest {

	private static EntityManagerFactory emf;
	private static EntityManager em;
	private static UserRepository userRepository;

	@BeforeAll
	static void setup() {
		emf = Persistence.createEntityManagerFactory("MiniGramTestPU");
		em = emf.createEntityManager();
		userRepository = new UserRepository(em);
	}

	@BeforeEach
	void clearDB() {
		em.getTransaction().begin();
		em.createQuery("DELETE FROM User").executeUpdate();
		em.getTransaction().commit();
	}

	@AfterAll
	static void tearDown() {
		em.close();
		emf.close();
	}

	@Test
	void testSaveAndFind() {
		User user = createUser("test@example.com", "testUsername");

		userRepository.save(user);

		User found = userRepository.findByEmail("test@example.com");
		assertNotNull(found);
		assertEquals("testUsername", found.getUsername());
	}

	@Test
	void testDeleteUser() {
		User user = createUser("test@example.com", "testUsername");

		userRepository.save(user);
		userRepository.delete(user);

		User found = userRepository.findById(user.getId());
		assertNull(found);
	}

	@Test
	void testDeleteNullUser() {
		assertDoesNotThrow(() -> userRepository.delete(null));
	}

	@Test
	void testFindByEmailExists() {
		User user = createUser("test@example.com", "testUsername");

		userRepository.save(user);

		User found = userRepository.findByEmail("test@example.com");
		assertNotNull(found);
		assertEquals("testUsername", found.getUsername());
	}

	@Test
	void testFindByEmailNotExists() {
		User found = userRepository.findByEmail("not@exists.com");
		assertNull(found);
	}

	@Test
	void testFindByUsernameExists() {
		User user = createUser("test@example.com", "testUsername");

		userRepository.save(user);

		User found = userRepository.findByUsername("testUsername");
		assertNotNull(found);
		assertEquals("testUsername", found.getUsername());
	}

	@Test
	void testFindByUsernameNotExists() {
		User found = userRepository.findByUsername("not@exists.com");
		assertNull(found);
	}

	@Test
	void testUpdateExistingUser() {
		User user = createUser("test@example.com", "testUsername");
		userRepository.save(user);

		user.setUsername("edited");
		userRepository.update(user);

		User updated = userRepository.findById(user.getId());
		assertEquals("edited", updated.getUsername());
	}

	@Test
	void testUpdateNullUser() {
		assertThrows(Exception.class, () -> userRepository.update(null));
	}

	private User createUser(String email, String username) {
		User user = new User();
		user.setEmail(email);
		user.setUsername(username);
		user.setPassword("testPassword");
		return user;
	}

}

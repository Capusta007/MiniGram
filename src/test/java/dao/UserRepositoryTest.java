package dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
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
		User user = new User();
		user.setEmail("test@example.com");
		user.setUsername("testUsername");
		user.setPassword("testPassword");
		
		userRepository.save(user);
		
		User found = userRepository.findByEmail("test@example.com");
		assertNotNull(found);
		assertEquals("testUsername", found.getUsername());
	}
	
}

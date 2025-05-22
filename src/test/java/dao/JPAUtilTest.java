package dao;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;

public class JPAUtilTest {
	private static EntityManager em;

	@BeforeAll
	static void setup() {
		em = JPAUtil.getEntityManager();
	}

	@Test
	void testEntityManagerIsNotNull() {
		assertNotNull(em, "EntityManager are null");
	}

	@Test
	void testEntityManagerIsOpen() {
		assertTrue(em.isOpen(), "EntityManager are not open");
	}

	@AfterAll
	static void teardown() {
		if (em != null && em.isOpen()) {
			em.close();
		}
		JPAUtil.close();
	}
}
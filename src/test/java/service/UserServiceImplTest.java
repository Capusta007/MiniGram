package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import dao.UserRepository;
import model.User;

class UserServiceImplTest {

	private static UserRepository userRepository;
	private static UserServiceImpl userService;

	@BeforeAll
	static void setUp() {
		userRepository = mock(UserRepository.class); // create mock
		userService = new UserServiceImpl(userRepository); // transfer mock
	}

	@Test
	void testRegisterSuccess() {
		String email = "test@example.com";
		String username = "testuser";
		String password = "password123";

		// user with such email does not found
		when(userRepository.findByEmail(email)).thenReturn(null);

		// Перехватываем user, который передаётся в save, и сразу возвращаем его обратно
		doAnswer(invocation -> invocation.getArgument(0)).when(userRepository).save(any(User.class));

		User registeredUser = userService.register(username, email, password);

		// Проверяем, что register вернул не null и корректного юзера
		assertEquals(email, registeredUser.getEmail());
		assertEquals(username, registeredUser.getUsername());
		assertTrue(BCrypt.checkpw(password, registeredUser.getPassword()));

		verify(userRepository).save(any(User.class));
	}

	@Test
	void testRegisterEmailAlreadyExists() {
		String email = "test@example.com";
		String username = "testuser";
		String password = "password123";

		// simulate that email already in base
		when(userRepository.findByEmail(email)).thenReturn(new User());

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> userService.register(username, email, password));

		assertEquals("Email already exists", exception.getMessage());

		// ensure save не вызывается
		verify(userRepository, never()).save(any(User.class));
	}
}

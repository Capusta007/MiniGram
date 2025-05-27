package service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import dao.UserRepository;
import model.User;

class UserServiceImplTest {

	private static UserRepository userRepository;
	private static UserServiceImpl userService;

	@BeforeEach
	void setUp() {
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

	@Test
	void testLoginEmailExists() {
		String email = "test@example.com";
		String password = "password123";
		String hPassword = BCrypt.hashpw(password, BCrypt.gensalt());

		User mockUser = new User();
		mockUser.setEmail(email);
		mockUser.setPassword(hPassword);

		when(userRepository.findByEmail(email)).thenReturn(mockUser);

		User loggedUser = userService.login(email, password);

		assertEquals(email, loggedUser.getEmail());
		assertTrue(BCrypt.checkpw(password, loggedUser.getPassword()));
	}

	@Test
	void testLoginWrongPassword() {
		String email = "test@example.com";
		String correctPassword = "correct123";
		String wrongPassword = "wrong123";

		// Захешированный правильный пароль
		String hashedPassword = BCrypt.hashpw(correctPassword, BCrypt.gensalt());

		// Возвращаем пользователя с правильным хешем
		User mockUser = new User();
		mockUser.setEmail(email);
		mockUser.setPassword(hashedPassword);

		when(userRepository.findByEmail(email)).thenReturn(mockUser);

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> userService.login(email, wrongPassword));

		assertEquals("Wrong password", exception.getMessage());
	}

	@Test
	void testLoginUserNotRegistered() {
		String email = "notfound@example.com";
		String password = "somepassword";

		// эмулируем, что пользователь не найден
		when(userRepository.findByEmail(email)).thenReturn(null);

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> userService.login(email, password));

		assertEquals("User are not registered", exception.getMessage());
	}

	@Test
	void testChangePasswordSuccess() {
		Long id = 1L;
		String oldPassword = "old123";
		String newPassword = "new456";
		String hashedOld = BCrypt.hashpw(oldPassword, BCrypt.gensalt());

		User user = new User();
		user.setId(id);
		user.setPassword(hashedOld);

		when(userRepository.findById(id)).thenReturn(user);

		assertDoesNotThrow(() -> userService.changePassword(id, oldPassword, newPassword));

		verify(userRepository).update(argThat(updatedUser -> BCrypt.checkpw(newPassword, updatedUser.getPassword())));

	}

	@Test
	void testChangePasswordWrongOldPassword() {
		Long id = 1L;
		String correctPassword = "correctPassword";
		String wrongPassword = "1312ads23";
		String newPassword = "newPassword";
		String hashedPassword = BCrypt.hashpw(correctPassword, BCrypt.gensalt());

		User user = new User();
		user.setId(id);
		user.setPassword(hashedPassword);

		when(userRepository.findById(id)).thenReturn(user);

		IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
				() -> userService.changePassword(id, wrongPassword, newPassword));
		assertEquals("Wrong password", e.getMessage());

		verify(userRepository, never()).update(any());
	}

	@Test
	void testDeleteUserSuccess() {
		Long id = 1L;
		String password = "testPassword";
		String hPassword = BCrypt.hashpw(password, BCrypt.gensalt());

		User user = new User();
		user.setId(id);
		user.setPassword(hPassword);

		when(userRepository.findById(id)).thenReturn(user);

		assertDoesNotThrow(() -> userService.deleteUser(id, password));

		verify(userRepository).delete(user);
	}

	@Test
	void testDeleteUserWrongPassword() {
		Long id = 1L;
		String correctPassword = "correctPassword";
		String wrongPassword = "wrong123";
		String hPassword = BCrypt.hashpw(correctPassword, BCrypt.gensalt());

		User user = new User();
		user.setId(id);
		user.setPassword(hPassword);

		when(userRepository.findById(id)).thenReturn(user);

		IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
				() -> userService.deleteUser(id, wrongPassword));

		assertEquals("Wrong password", e.getMessage());

		verify(userRepository, never()).delete(any());
	}
}

package service;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dao.UserRepository;
import model.User;

public class UserServiceImpl implements UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	private UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * Registers a new user with the provided username, email, and password. The
	 * password is hashed before saving to the database.
	 *
	 * @param username the username of the new user
	 * @param email    the email of the new user
	 * @param password the plain text password to be hashed and stored
	 * @return the registered {@link User} object if successful, or {@code null} if
	 *         an error occurred during saving
	 * @throws IllegalArgumentException if any parameter is null or if a user with
	 *                                  the same email already exists
	 */
	@Override
	public User register(String username, String email, String password) {
		logger.info("Registration of user: {}", email);

		if (username == null || email == null || password == null) {
			throw new IllegalArgumentException("Username, email and password must not be null");
		}

		if (userRepository.findByEmail(email) != null) {
			logger.warn("Attempt to register with an existing email address: {}", email);
			throw new IllegalArgumentException("Email already exists");
		}

		String hPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		User user = new User();
		user.setEmail(email);
		user.setUsername(username);
		user.setPassword(hPassword);

		try {
			userRepository.save(user);
			logger.info("User {} registered successfully", email);
			return user;
		} catch (Exception e) {
			logger.error("Error while saving user {} in database", email);
			return null;
		}
	}

	/**
	 * Authenticates a user using their email and password.
	 *
	 * @param email    the email of the user
	 * @param password the plain text password to check against the stored hash
	 * @return the authenticated {@link User} object if credentials are correct
	 * @throws IllegalArgumentException if any parameter is null, the user does not
	 *                                  exist, or the password is incorrect
	 */
	@Override
	public User login(String email, String password) {
		logger.info("Login of user: {}", email);

		if (email == null || password == null) {
			throw new IllegalArgumentException("Email and password must not be null");
		}

		User user = userRepository.findByEmail(email);
		if (user == null) {
			logger.warn("Attempt to login unregistered user: {}", email);
			throw new IllegalArgumentException("User are not registered");
		}

		if (!BCrypt.checkpw(password, user.getPassword())) {
			logger.warn("User: {} entered wrong password", email);
			throw new IllegalArgumentException("Wrong password");
		}

		logger.info("User {} logged successfully", email);
		return user;
	}

	@Override
	public User getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void changePassword(Long userId, String oldPassword, String newPassword) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteUser(Long userId, String password) {
		// TODO Auto-generated method stub

	}

}

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

	/**
	 * Finds a user by their ID.
	 *
	 * @param id the ID of the user
	 * @return the user with the given ID, or {@code null} if not found
	 */
	@Override
	public User getById(Long id) {
		return userRepository.findById(id);
	}

	/**
	 * Changes the password of a user with the given {@code userId}.
	 *
	 * @param userId      the ID of the user whose password is to be changed
	 * @param oldPassword the current password provided by the user
	 * @param newPassword the new password to set
	 * @throws IllegalArgumentException if the user doesn't exist, if the old
	 *                                  password is incorrect or if one of
	 *                                  {@code String} params is null
	 */
	@Override
	public void changePassword(Long userId, String oldPassword, String newPassword) {
		User user = userRepository.findById(userId);
		if (user == null) {
			throw new IllegalArgumentException("No user with such id");
		}

		if (oldPassword == null || newPassword == null) {
			throw new IllegalArgumentException("oldPassword and newPassword must not be null");
		}

		if (!BCrypt.checkpw(oldPassword, user.getPassword())) {
			logger.warn("User: {} entered wrong password", user.getEmail());
			throw new IllegalArgumentException("Wrong password");
		}

		String hPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
		user.setPassword(hPassword);
		userRepository.update(user);
		
		logger.info("User {} changed password", user.getEmail());
	}

	/**
	 * Deletes the user with the given {@code userId}, only if the provided {@code password}
	 * matches the current password of the user.
	 *
	 * @param userId   the ID of the user to delete
	 * @param password the current password for confirmation
	 * @throws IllegalArgumentException if the password is incorrect or user does not exist
	 */
	@Override
	public void deleteUser(Long userId, String password) {
		User user = userRepository.findById(userId);
		
		if(user == null) {
			throw new IllegalArgumentException("No user with such id");
		}
		
		if(!BCrypt.checkpw(password, user.getPassword())) {
			logger.warn("Wrong password while deleting user {}", user.getEmail());
			throw new IllegalArgumentException("Wrong password");
		}

		String email = user.getEmail();
		userRepository.delete(user);
		
		logger.info("User {} has been deleted", email);
	}

}

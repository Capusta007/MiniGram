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
	
	@Override
	public void register(String username, String email, String password) {
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
		
		userRepository.save(user);
		
		logger.info("User {} registered successfully" ,email);
	}

	@Override
	public void login(String username, String email, String password) {
		// TODO Auto-generated method stub
		
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

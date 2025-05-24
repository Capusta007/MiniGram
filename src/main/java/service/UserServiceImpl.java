package service;

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
		// TODO Auto-generated method stub
		
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

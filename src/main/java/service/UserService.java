package service;

import model.User;

public interface UserService {
	public User register(String username, String email, String password);
	public User login(String email, String password);
	public User getById(Long id);
	void changePassword(Long userId, String oldPassword, String newPassword);
	void deleteUser(Long userId, String password);
}

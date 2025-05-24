package service;

import model.User;

public interface UserService {
	public void register(String username, String email, String password);
	public void login(String username, String email, String password);
	public User getById(Long id);
	void changePassword(Long userId, String oldPassword, String newPassword);
	void deleteUser(Long userId, String password);
}

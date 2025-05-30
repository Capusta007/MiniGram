package dto;

import jakarta.servlet.http.HttpServletRequest;

public class RegistrationRequest {
	private final String username;
	private final String email;
	private final String password;

	public RegistrationRequest(HttpServletRequest request) {
		this.username = request.getParameter("username");
		this.email = request.getParameter("email");
		this.password = request.getParameter("password");
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
}

package dto;

import jakarta.servlet.http.HttpServletRequest;

public class LoginRequest {
	private final String email;
	private final String password;

	public LoginRequest(HttpServletRequest req) {
		this.email = req.getParameter("email");
		this.password = req.getParameter("password");
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
}

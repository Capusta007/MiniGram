package controller.servlets;

import java.io.IOException;

import config.AppConstants;
import dto.RegistrationRequest;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import service.UserServiceImpl;
import util.RequestAttributeUtil;
import util.SessionAttributes;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("WEB-INF/views/auth/registration.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RegistrationRequest registrationInfo = new RegistrationRequest(req);

		UserServiceImpl userService = (UserServiceImpl) req.getServletContext()
				.getAttribute(AppConstants.USER_SERVICE_ATTR);
		
		// registration
		User registeredUser;
		try {
			registeredUser = userService.register(
					registrationInfo.getUsername(),
					registrationInfo.getEmail(),
					registrationInfo.getPassword());
		} catch (Exception e) {
			RequestAttributeUtil.setErrorMessage(req, e.getMessage());
			req.getRequestDispatcher("WEB-INF/views/auth/registration.jsp").forward(req, resp);
			return;
		}
	
		req.getSession().setAttribute(SessionAttributes.USER_ID, registeredUser.getId());
		resp.sendRedirect("homepage");
	}
}

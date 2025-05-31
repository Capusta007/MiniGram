package controller.servlets;

import java.io.IOException;

import config.AppConstants;
import dto.LoginRequest;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import service.UserServiceImpl;
import util.RequestAttributeUtil;
import util.SessionAttributes;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("WEB-INF/views/auth/login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LoginRequest loginInfo = new LoginRequest(req);
		
		UserServiceImpl userService = (UserServiceImpl) req.getServletContext()
				.getAttribute(AppConstants.USER_SERVICE_ATTR);
		
		User loggedUser;
		try {
			loggedUser = userService.login(loginInfo.getEmail(), loginInfo.getPassword());
		} catch (Exception e) {
			RequestAttributeUtil.setErrorMessage(req, e.getMessage());
			req.getRequestDispatcher("WEB-INF/views/auth/login.jsp").forward(req, resp);
			return;
		}
		
		req.getSession().setAttribute(SessionAttributes.USER_ID, loggedUser.getId());
		resp.sendRedirect("homepage");
	}
}

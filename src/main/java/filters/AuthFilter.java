package filters;

import java.io.IOException;
import java.util.Set;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.SessionAttributes;

@WebFilter("/*")
public class AuthFilter implements Filter {

	private static final Set<String> ALLOWED_PAGES = Set.of("/MiniGram/login", "/MiniGram/registration", "/MiniGram/");

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		String uri = req.getRequestURI();
		if (req.getSession().getAttribute(SessionAttributes.USER_ID) == null) {
			if (ALLOWED_PAGES.contains(uri)) {
				chain.doFilter(request, response);
			} else {
				resp.sendRedirect("login");
			}
			return;
		}
		
		chain.doFilter(request, response);
	}
}

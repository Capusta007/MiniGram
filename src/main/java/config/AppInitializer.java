package config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dao.JPAUtil;
import dao.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import service.UserService;
import service.UserServiceImpl;

@WebListener
public class AppInitializer implements ServletContextListener {
	private static final Logger logger = LoggerFactory.getLogger(AppInitializer.class);
	private EntityManager em;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			em = JPAUtil.getEntityManager();

			UserRepository userRepository = new UserRepository(em);
			UserService userService = new UserServiceImpl(userRepository);

			sce.getServletContext().setAttribute(AppConstants.USER_SERVICE_ATTR, userService);

		} catch (Exception e) {
			logger.error("Failed to initialize application context");
			throw new RuntimeException("Failed to initialize application context", e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		JPAUtil.close();
	}
}

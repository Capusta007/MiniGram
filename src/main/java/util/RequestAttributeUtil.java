package util;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Utility class for managing common request attributes, such as messages, in a
 * centralized and consistent way.
 *
 * Example usage:
 * 
 * <pre>
 * RequestAttributeHelper.setMessage(request, "User registered successfully.");
 * String msg = RequestAttributeHelper.getMessage(request);
 * </pre>
 *
 * This class helps to avoid hardcoding attribute names like "message"
 * throughout the codebase.
 */
public class RequestAttributeUtil {

	private static final String ATTR_ERROR_MESSAGE = "errorMessage";
	private static final String ATTR_SUCCESS_MESSAGE = "successMessage";

	/**
	 * Sets a message attribute in the request scope.
	 *
	 * @param req     the {@link HttpServletRequest} in which the attribute should
	 *                be set
	 * @param message the message to be displayed (e.g., error or info message)
	 */
	public static void setMessage(HttpServletRequest req, String message) {
		req.setAttribute(ATTR_SUCCESS_MESSAGE, message);
	}

	/**
	 * Retrieves the message attribute from the request scope.
	 *
	 * @param req the {@link HttpServletRequest} from which the message should be
	 *            retrieved
	 * @return the message string if present, otherwise {@code null}
	 */
	public static String getMessage(HttpServletRequest req) {
		return (String) req.getAttribute(ATTR_SUCCESS_MESSAGE);
	}
	
	/**
	 * Sets a error message attribute in the request scope.
	 *
	 * @param req     the {@link HttpServletRequest} in which the attribute should
	 *                be set
	 * @param message the message to be displayed (e.g., error or info message)
	 */
	public static void setErrorMessage(HttpServletRequest req, String message) {
        req.setAttribute(ATTR_ERROR_MESSAGE, message);
    }

	/**
	 * Retrieves the error message attribute from the request scope.
	 *
	 * @param req the {@link HttpServletRequest} from which the message should be
	 *            retrieved
	 * @return the message string if present, otherwise {@code null}
	 */
    public static String getErrorMessage(HttpServletRequest req) {
        return (String) req.getAttribute(ATTR_ERROR_MESSAGE);
    }
}

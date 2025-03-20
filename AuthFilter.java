package pages;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet Filter implementation class AuthFilter
 */
public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String path = httpRequest.getRequestURI();
        boolean isLoggedIn = (session != null && session.getAttribute("username") != null);

        if (!isLoggedIn && !path.endsWith("LogIn.jsp") && !path.endsWith("Loginservlet")) {
            httpResponse.sendRedirect("LogIn.jsp");
        } else if (isLoggedIn) {
            String role = (String) session.getAttribute("role");
            // Add role-based path checks here, if needed
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}
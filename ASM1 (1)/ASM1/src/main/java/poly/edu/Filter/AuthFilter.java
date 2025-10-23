package poly.edu.Filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        String uri = httpRequest.getRequestURI();

        if (uri.startsWith("/css") || uri.startsWith("/js") || uri.startsWith("/images")) {
            chain.doFilter(request, response);
            return;
        }

        if (session != null && (session.getAttribute("customer") != null || session.getAttribute("staff") != null)) {
            chain.doFilter(request, response);
            return;
        }

        if (uri.equals("/login") || uri.equals("/register") || uri.equals("/") || uri.equals("/home") || uri.equals("/api/home/products")) {
            chain.doFilter(request, response);
            return;
        }

        httpResponse.sendRedirect("/login");
    }
}
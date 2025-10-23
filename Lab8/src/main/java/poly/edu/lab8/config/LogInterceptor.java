package poly.edu.lab8.config;


import poly.edu.lab8.entity.Account;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Date;

@Component
public class LogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Account user = (Account) request.getSession().getAttribute("user");
        String name = (user != null) ? user.getFullname() : "ANONYMOUS";
        System.out.println("URI: " + request.getRequestURI() + ", Time: " + new Date() + ", User: " + name);
        return true;
    }
}

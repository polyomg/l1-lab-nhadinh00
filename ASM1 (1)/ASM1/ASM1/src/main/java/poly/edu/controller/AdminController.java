package poly.edu.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String adminDashboard(HttpSession session) {
        // Nếu nhân viên (staff) đã đăng nhập
        if (session.getAttribute("staff") != null) {
            return "admin/adminDashboard"; // templates/admin/adminDashboard.html
        }
        // Nếu chưa đăng nhập thì quay về login
        return "redirect:/login";
    }
}

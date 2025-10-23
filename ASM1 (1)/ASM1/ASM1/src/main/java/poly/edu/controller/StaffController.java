package poly.edu.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaffController {

    

    @GetMapping("/staff")
    public String staffDashboard() {
        return "/adminDashboard"; // Trả về view cho trang staff
    }
}
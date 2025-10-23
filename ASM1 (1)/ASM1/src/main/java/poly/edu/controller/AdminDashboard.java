package poly.edu.controller;

import poly.edu.model.Staff;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class AdminDashboard {

    @GetMapping("/adminDashboard")
    public String adminPage(Model model, HttpSession session) {
        Staff staff = (Staff) session.getAttribute("staff");

        if (staff != null) {
            model.addAttribute("staffName", staff.getTenNV());
            model.addAttribute("staffRole", staff.isRole());
        } else {
            return "redirect:/login";
        }

        return "admin/admin";
    }

    @PostMapping("/admin/logout") // Thay đổi đường dẫn
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
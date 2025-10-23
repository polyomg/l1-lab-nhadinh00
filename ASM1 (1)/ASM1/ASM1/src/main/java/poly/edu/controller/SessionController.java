package poly.edu.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SessionController {

    @GetMapping("/checkSession")
    public String checkSession(HttpSession session, Model model) {
        String maKH = (String) session.getAttribute("maKH");
        model.addAttribute("maKH", maKH);
        return "checkSession";
    }
}
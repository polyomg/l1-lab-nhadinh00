package poly.edu.lab8.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin/home")
    public String home() {
        return "admin/home";
    }
}


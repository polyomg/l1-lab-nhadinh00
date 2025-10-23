package poly.edu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home") // Điều hướng tới trang chủ
    public String index() {
        return "index"; // Nếu dùng Thymeleaf, để trong templates
    }
}

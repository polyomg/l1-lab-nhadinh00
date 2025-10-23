package poly.edu.lab8.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import poly.edu.lab8.entity.Account;
import poly.edu.lab8.service.AccountService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AccountService accountService;

    @GetMapping("/login")
    public String loginForm() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        org.springframework.ui.Model model) {
        Account acc = accountService.findById(username);
        if (acc == null || !acc.getPassword().equals(password)) {
            model.addAttribute("message", "Sai tên đăng nhập hoặc mật khẩu!");
            return "auth/login";
        }
        session.setAttribute("user", acc);
        model.addAttribute("message", "Đăng nhập thành công!");
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}

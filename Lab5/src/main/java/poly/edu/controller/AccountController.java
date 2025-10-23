package poly.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import poly.edu.service.CookieService;
import poly.edu.service.ParamService;
import poly.edu.service.SessionService;

@Controller
public class AccountController {

    @Autowired
    CookieService cookieService;

    @Autowired
    ParamService paramService;

    @Autowired
    SessionService sessionService;

    @GetMapping("/account/login")
    public String login1(Model model) {
        String savedUser = cookieService.getValue("user");
        model.addAttribute("username", savedUser);
        return "/account/login"; 
    }

    @PostMapping("/account/login")
    public String login2(Model model) {
        String username = paramService.getString("username", "");
        String password = paramService.getString("password", "");
        boolean remember = paramService.getBoolean("remember", false);

        if (username.equals("poly") && password.equals("123")) {
            sessionService.set("username", username);

            if (remember) {
                cookieService.add("user", username, 24 * 10); 
            } else {
                cookieService.remove("user");
            }

            model.addAttribute("message", "Đăng nhập thành công!");
        } else {
            model.addAttribute("message", "Sai tên đăng nhập hoặc mật khẩu!");
        }

        return "/account/login";
    }
}

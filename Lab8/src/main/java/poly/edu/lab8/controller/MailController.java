package poly.edu.lab8.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MailController {

    @GetMapping("/mail/form")
    public String mailForm() {
        return "mail/form"; 
    }
}

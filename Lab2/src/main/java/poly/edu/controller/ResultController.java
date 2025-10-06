package poly.edu.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ResultController {

    @RequestMapping("/a")
    public String m1() {
        return "a";
    }

    @RequestMapping("/b")
    public String m2(Model model) {
        model.addAttribute("message", "I come from b");
        return "forward:/a"; // ?1
    }

    @RequestMapping("/c")
    public String m3(RedirectAttributes params) {
        params.addAttribute("message", "I come from c");
        return "redirect:/a"; // ?2
    }

    @ResponseBody // ?3
    @RequestMapping("/d")
    public String m4() {
        return "I come from d";
    }
}


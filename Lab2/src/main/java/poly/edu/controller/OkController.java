package poly.edu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ctrl")
public class OkController {

    @RequestMapping("/ok")
    public String ok() {
        return "ok";
    }

    @PostMapping("/ok")
    public String m1(Model model) {
        model.addAttribute("message", "m1()");
        return "ok";
    }

    @GetMapping("/ok")
    public String m2(Model model) {
        model.addAttribute("message", "m2()");
        return "ok";
    }

    @RequestMapping(value = "/ok", params = "x")
    public String m3(Model model) {
        model.addAttribute("message", "m3()");
        return "ok";
    }
}

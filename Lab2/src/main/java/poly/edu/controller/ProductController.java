package poly.edu.controller;


import poly.edu.entity.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {

    @GetMapping("/product/form")
    public String form() {
        return "product/form";
    }

    @PostMapping("/product/save")
    public String save(Model model, Product p) {
        model.addAttribute("name", p.getName());
        model.addAttribute("price", p.getName());
        return "product/form";
    }
}



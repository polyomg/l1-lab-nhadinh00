package poly.edu.controller;

import poly.edu.entity.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
public class ProductControllerbai4 {

    @GetMapping("/product/form")
    public String form(Model model) {
        Product p = new Product();
        p.setName("iPhone 30");
        p.setPrice(5000.0);
        model.addAttribute("p", p); // ?1
        return "product/form";
    }

    @PostMapping("/product/save")
    public String save(Model model, @ModelAttribute("s") Product p) { // ?2
        return "product/form";
    }

    @ModelAttribute("items") // ?3
    public List<Product> getItems() {
        return Arrays.asList(
                new Product("A", 1.0),
                new Product("B", 12.0)
        );
    }

}


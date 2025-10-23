package poly.edu.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import poly.edu.DAO.ProductDAO;
import poly.edu.entity.Product;

@Controller
public class ProductController {

    @Autowired
    ProductDAO dao;

    @RequestMapping("/product/page")
    public String paginate(Model model,
                           @RequestParam("p") Optional<Integer> p) {
        Pageable pageable = PageRequest.of(p.orElse(0), 5); 
        Page<Product> page = dao.findAll(pageable);
        model.addAttribute("page", page);
        return "product/page";
    }
}

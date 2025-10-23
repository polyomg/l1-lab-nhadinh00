package poly.edu.controller;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import poly.edu.DAO.ProductDAO;
import poly.edu.entity.Product;
import poly.edu.service.SessionService;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductDAO DAO;

    @Autowired
    SessionService session;


    // Bài 2
    @RequestMapping("/search-and-page")
    public String searchAndPage(Model model,
                                @RequestParam("keywords") Optional<String> kw,
                                @RequestParam("p") Optional<Integer> p) {

        String kwords = kw.orElse(session.get("keywords", ""));
        session.set("keywords", kwords);
        int pageIndex = p.orElse(0);
        if (pageIndex < 0) {
            pageIndex = 0;
        }

        Pageable pageable = PageRequest.of(pageIndex, 5);
        Page<Product> page = DAO.findAllByNameLike("%" + kwords + "%", pageable);

        model.addAttribute("page", page);
        model.addAttribute("keywords", kwords); 
        return "product/search-and-page";
    }
    
	 // Bài 4
	    @RequestMapping("/search")
	    public String searchDSL(Model model,
	                            @RequestParam("min") Optional<Double> min,
	                            @RequestParam("max") Optional<Double> max) {
	        double minPrice = min.orElse(Double.MIN_VALUE);
	        double maxPrice = max.orElse(Double.MAX_VALUE);
	        List<Product> items = DAO.findByPriceBetween(minPrice, maxPrice);
	        model.addAttribute("items", items);
	        return "product/search";
	    }

}

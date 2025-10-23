package poly.edu.controller;

import poly.edu.DAO.ProductDAO;
import poly.edu.entity.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
public class ReportController {

    @Autowired
    ProductDAO DAO;

    @RequestMapping("/report/inventory-by-category")
    public String inventory(Model model) {
        List<Report> items = DAO.getInventoryByCategory();
        model.addAttribute("items", items);
        return "report/inventory-by-category";
    }
}

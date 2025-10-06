package poly.edu.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RectangleController {
    @Autowired
    HttpServletRequest request;

    @GetMapping("/rectangle/form")
    public String form() {
        return "rectangle";
    }

    @PostMapping("/rectangle/calc")
    public String calc(Model model) {
        try {
            double width = Double.parseDouble(request.getParameter("width"));
            double height = Double.parseDouble(request.getParameter("height"));
            double area = width * height;
            double perimeter = 2 * (width + height);

            model.addAttribute("width", width);
            model.addAttribute("height", height);
            model.addAttribute("area", area);
            model.addAttribute("perimeter", perimeter);
        } catch (Exception e) {
            model.addAttribute("message", "Vui lòng nhập số hợp lệ!");
        }
        return "rectangle";
    }
}

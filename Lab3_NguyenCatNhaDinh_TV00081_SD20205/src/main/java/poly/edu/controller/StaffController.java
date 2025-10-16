package poly.edu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import poly.edu.model.Staff;

import java.util.Date;

@Controller
public class StaffController {

    @GetMapping("/staff/detail")
    public String detail(Model model) {
        Staff staff = Staff.builder()
                .id("user@gmail.com")
                .fullname("Nguyễn Văn User")
                .gender(true)
                .birthday(new Date(124, 11, 23)) 
                .salary(12345.68)
                .level(2)
                .photo("photo.jpg")
                .build();

        model.addAttribute("staff", staff);
        return "staff-detail";
    }
}

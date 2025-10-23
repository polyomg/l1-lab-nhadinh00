package poly.edu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import poly.edu.entity.Staff;
import jakarta.validation.Valid;
import org.springframework.validation.Errors;

@Controller
@RequestMapping("/staff/create")
public class StaffController {

    @RequestMapping("/form")
    public String createForm(Model model, @ModelAttribute("staff") Staff staff) {
        model.addAttribute("message", "Vui lòng nhập thông tin nhân viên!");
        return "/demo/staff-create";
    }

    @RequestMapping("/save")
    public String createSave(Model model,
                             @RequestPart(value = "photo_file", required = false) MultipartFile photoFile,
                             @Valid @ModelAttribute("staff") Staff staff,
                             Errors errors) {
        if (photoFile != null && !photoFile.isEmpty()) {
            staff.setPhoto(photoFile.getOriginalFilename());
        }
        if (errors.hasErrors()) {
            model.addAttribute("message", "Vui lòng sửa các lỗi sau!");
        } else {
            model.addAttribute("message", "Dữ liệu đã nhập đúng!");
        }
        return "/demo/staff-create";
    }
}



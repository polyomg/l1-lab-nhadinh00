package poly.edu.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import poly.edu.model.Staff;
import poly.edu.service.StaffService;

@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private StaffService staffService;

    // Hiển thị form đăng nhập admin
    @GetMapping("/login")
    public String showAdminLoginForm() {
        return "adminLogin"; // => templates/adminLogin.html
    }

    // Xử lý đăng nhập admin
    @PostMapping("/login")
    public String login(@RequestParam("tenDN") String tenDN,
                        @RequestParam("matKhau") String matKhau,
                        HttpServletRequest request,
                        Model model) {

        Staff staff = staffService.findByUsernameAndPassword(tenDN, matKhau);

        if (staff != null) {
            HttpSession session = request.getSession();
            session.setAttribute("staff", staff);
            session.setAttribute("isAdmin", staff.isRole());

            if (staff.isRole()) {
                return "redirect:/adminDashboard"; // admin quyền cao
            } else {
                return "redirect:/home"; // nhân viên thường
            }
        }

        model.addAttribute("error", "Sai tên đăng nhập hoặc mật khẩu!");
        return "adminLogin";
    }

    // Đăng xuất
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login";
    }
}

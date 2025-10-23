package poly.edu.controller;

import poly.edu.model.Customer;
import poly.edu.service.CustomerProfileService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
public class CustomerProfileController {

    @Autowired
    private CustomerProfileService customerProfileService;

    @Autowired
    private JavaMailSender mailSender;

    // Hiển thị trang hồ sơ khách hàng
    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            return "redirect:/login";
        }
        model.addAttribute("customer", customer);
        return "customerprofile";
    }

    // Cập nhật thông tin khách hàng
    @PostMapping("/update-profile")
    public String updateProfile(@RequestParam String tenDN,
                                @RequestParam String email,
                                @RequestParam String ten,
                                @RequestParam String sdt,
                                HttpSession session,
                                Model model) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            return "redirect:/login";
        }
        customer.setTenDN(tenDN);
        customer.setEmail(email);
        customer.setTen(ten);
        customer.setSdt(sdt);
        customerProfileService.updateCustomer(customer);
        session.setAttribute("customer", customer);
        model.addAttribute("message", "Cập nhật thông tin thành công!");
        return "customerprofile";
    }

    // Hiển thị trang đổi mật khẩu
    @GetMapping("/change-password")
    public String showChangePassword(HttpSession session, Model model) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            return "redirect:/login";
        }
        model.addAttribute("customer", customer);
        return "change-password";
    }

    // Xử lý đổi mật khẩu
    @PostMapping("/change-password")
    public String changePassword(@RequestParam String oldPassword,
                                 @RequestParam String newPassword,
                                 HttpSession session,
                                 Model model) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            return "redirect:/login";
        }
        if (!customer.getMatKhau().equals(oldPassword)) {
            model.addAttribute("error", "Mật khẩu cũ không đúng!");
            return "change-password";
        }
        customerProfileService.updatePassword(customer, newPassword);
        session.setAttribute("customer", customer);
        model.addAttribute("message", "Đổi mật khẩu thành công!");
        return "change-password";
    }

    // Hiển thị trang quên mật khẩu
    @GetMapping("/forgot-password")
    public String showForgotPassword(HttpSession session, Model model) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            return "redirect:/login";
        }
        model.addAttribute("customer", customer);
        return "forgot-password";
    }

    // Xử lý quên mật khẩu (không cần nhập email)
    @PostMapping("/forgot-password")
    public String forgotPassword(HttpSession session, Model model) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            return "redirect:/login";
        }

        // Lấy email từ customer trong session
        String email = customer.getEmail();
        String newPassword = UUID.randomUUID().toString().substring(0, 8);
        customerProfileService.updatePassword(customer, newPassword);

        // Gửi email với mật khẩu mới
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Mật khẩu mới của bạn");
        message.setText("Mật khẩu mới của bạn là: " + newPassword + "\nVui lòng đổi mật khẩu sau khi đăng nhập.");
        mailSender.send(message);

        model.addAttribute("message", "Mật khẩu mới đã được gửi về email của bạn! Vui lòng kiểm tra.");
        return "forgot-password";
    }

    // Đăng xuất
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
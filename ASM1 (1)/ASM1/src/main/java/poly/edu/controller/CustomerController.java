package poly.edu.controller;

import poly.edu.model.Customer;
import poly.edu.model.Staff;
import poly.edu.service.CustomerService;
import poly.edu.service.StaffService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private StaffService staffService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String tenDN,
                        @RequestParam String matKhau,
                        HttpSession session, Model model) {

        Customer customer = customerService.findByUsernameAndPassword(tenDN, matKhau);
        if (customer != null) {
            session.setAttribute("maKH", customer.getMaKH());
            session.setAttribute("customer", customer);
            session.setAttribute("isAdmin", false); // Khách hàng không có quyền admin
            System.out.println("Customer logged in: " + customer.getMaKH()); // Log mã khách hàng
            return "redirect:/home";
        }

        Staff staff = staffService.findByUsernameAndPassword(tenDN, matKhau);
        if (staff != null) {
            session.setAttribute("staff", staff);
            session.setAttribute("isAdmin", staff.isRole()); // Lưu role của nhân viên
            System.out.println("Staff logged in: " + staff.getMaNV()); // Log mã nhân viên
            if (staff.isRole()) {
                return "redirect:/adminDashboard";
            } else {
                return "redirect:/adminDashboard";
            }
        }

        model.addAttribute("error", "Sai tài khoản hoặc mật khẩu!");
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "register";
    }

    @PostMapping("/register")
    public String register(Customer customer, Model model) {
        if (customerService.existsByUsername(customer.getTenDN())) {
            model.addAttribute("error", "Tên đăng nhập đã tồn tại!");
            return "register";
        }

        // Không cần gán ngayDangKy thủ công vì @CreationTimestamp sẽ tự xử lý
        customerService.saveCustomer(customer);
        return "redirect:/login";
    }
}
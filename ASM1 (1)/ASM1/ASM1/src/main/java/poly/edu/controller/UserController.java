package poly.edu.controller;

import poly.edu.model.Customer;
import poly.edu.model.Staff;
import poly.edu.service.CustomerService;
import poly.edu.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private StaffService staffService;

    // Luôn là admin để test, sửa lại khi có login thực tế
    private final boolean isAdmin = true;

    @GetMapping("/users")
    public String userManagement(Model model,
                                 @RequestParam(defaultValue = "0") int customerPage,
                                 @RequestParam(defaultValue = "5") int customerSize,
                                 @RequestParam(defaultValue = "0") int staffPage,
                                 @RequestParam(defaultValue = "5") int staffSize,
                                 @RequestParam(defaultValue = "") String customerKeyword,
                                 @RequestParam(defaultValue = "") String staffKeyword) {

        // Pagination and search for customers
        Page<Customer> customerPageData = customerService.getCustomers(customerPage, customerSize, customerKeyword);
        model.addAttribute("customers", customerPageData.getContent());
        model.addAttribute("customerPage", customerPageData);
        model.addAttribute("customerKeyword", customerKeyword);

        // Pagination and search for staff
        Page<Staff> staffPageData = staffService.getStaff(staffPage, staffSize, staffKeyword);
        model.addAttribute("staffs", staffPageData.getContent());
        model.addAttribute("staffPage", staffPageData);
        model.addAttribute("staffKeyword", staffKeyword);

        model.addAttribute("isAdmin", isAdmin);

        return "admin/user-management";
    }

    @PostMapping("/users/customer")
    public String saveCustomer(@ModelAttribute Customer customer, RedirectAttributes redirectAttributes) {
        try {
            customerService.saveCustomer(customer);
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/users";
    }

    @PostMapping("/users/staff")
    public String saveStaff(@ModelAttribute Staff staff, RedirectAttributes redirectAttributes) {
        try {
            staffService.saveStaff(staff);
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/users";
    }

    @GetMapping("/users/customer/delete/{maKH}")
    public String deleteCustomer(@PathVariable String maKH) {
        customerService.deleteCustomer(maKH);
        return "redirect:/users";
    }

    @GetMapping("/users/staff/delete/{maNV}")
    public String deleteStaff(@PathVariable String maNV) {
        staffService.deleteStaff(maNV);
        return "redirect:/users";
    }

    @GetMapping("/users/customer/edit/{maKH}")
    public String editCustomer(@PathVariable String maKH, Model model) {
        model.addAttribute("customer", customerService.getCustomerById(maKH));
        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("staffs", staffService.getAllStaff());
        model.addAttribute("isAdmin", isAdmin);
        return "admin/user-management";
    }

    @GetMapping("/users/staff/edit/{maNV}")
    public String editStaff(@PathVariable String maNV, Model model) {
        model.addAttribute("staff", staffService.getStaffById(maNV));
        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("staffs", staffService.getAllStaff());
        model.addAttribute("isAdmin", isAdmin);
        return "admin/user-management";
    }

    @PostMapping("/users/customer/update")
    public String updateCustomer(@ModelAttribute Customer customer, RedirectAttributes redirectAttributes) {
        try {
            Customer existingCustomer = customerService.getCustomerById(customer.getMaKH());
            if (existingCustomer != null) {
                customer.setNgayDangKy(existingCustomer.getNgayDangKy());
            }
            customerService.saveCustomer(customer);
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/users";
    }

    @PostMapping("/users/staff/update")
    public String updateStaff(@ModelAttribute Staff staff, RedirectAttributes redirectAttributes) {
        try {
            staffService.saveStaff(staff);
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/users";
    }
}

package poly.edu.service;

import poly.edu.repository.CustomerDAO;
import poly.edu.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerDAO customerDAO;

    public boolean existsByUsername(String tenDN) {
        return customerDAO.findByTenDN(tenDN) != null;
    }

    private String generateNewMaKH() {
        // Lấy mã khách hàng lớn nhất hiện có
        Customer lastCustomer = customerDAO.findTopByOrderByMaKHDesc();
        if (lastCustomer == null) {
            return "KH001"; // Nếu chưa có khách hàng nào, bắt đầu từ KH001
        }
        // Lấy số từ mã khách hàng cuối cùng
        String lastMaKH = lastCustomer.getMaKH();
        int number = Integer.parseInt(lastMaKH.replace("KH", "")) + 1;
        return "KH" + String.format("%03d", number); // Định dạng lại mã KH001, KH002,...
    }

    public void saveCustomer(Customer customer) {
        if (customer.getMaKH() == null) {
            // Nếu mã khách hàng chưa có, sinh mã mới
            customer.setMaKH(generateNewMaKH());
        }
        customerDAO.save(customer);
    }

    public Customer findByUsernameAndPassword(String tenDN, String matKhau) {
        return customerDAO.findByTenDNAndMatKhau(tenDN, matKhau);
    }

    public List<Customer> getAllCustomers() {
        return customerDAO.findAll();
    }

    public Customer getCustomerById(String id) {
        return customerDAO.findById(id).orElse(null);
    }

    public void deleteCustomer(String id) {
        customerDAO.deleteById(id);
    }

    // Phương thức kiểm tra mã khách hàng có tồn tại không
    public boolean isMaKHExists(String maKH) {
        return customerDAO.findById(maKH).isPresent();
    }

    // Phương thức mới: Lấy thông tin khách hàng hiện tại dựa trên tên đăng nhập
    public Customer getCurrentCustomer(String tenDN) {
        if (tenDN == null || tenDN.isEmpty()) {
            return null; // Trả về null nếu không có tên đăng nhập
        }
        return customerDAO.findByTenDN(tenDN); // Tìm khách hàng theo tên đăng nhập
    }

    // New method for pagination and search
    public Page<Customer> getCustomers(int page, int size, String keyword) {
        Pageable pageable = PageRequest.of(page, size);
        if (keyword == null || keyword.trim().isEmpty()) {
            return customerDAO.findAll(pageable);
        } else {
            return customerDAO.findByMaKHOrTenContaining(keyword, keyword, pageable);
        }
    }
    
 // Cập nhật mật khẩu mới
    public void updatePassword(Customer customer, String newPassword) {
        customer.setMatKhau(newPassword);
        customerDAO.save(customer);
    }

    // Tìm khách hàng theo email
    public Customer findByEmail(String email) {
        return customerDAO.findByEmail(email).orElse(null);
    }
}
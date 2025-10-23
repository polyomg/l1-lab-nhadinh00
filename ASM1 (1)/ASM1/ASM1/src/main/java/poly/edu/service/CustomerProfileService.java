package poly.edu.service;

import poly.edu.model.Customer;
import poly.edu.repository.CustomerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerProfileService {

    @Autowired
    private CustomerDAO customerDAO;

    // Cập nhật thông tin khách hàng
    public void updateCustomer(Customer customer) {
        customerDAO.save(customer);
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
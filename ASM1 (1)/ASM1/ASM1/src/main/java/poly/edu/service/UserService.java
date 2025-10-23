package poly.edu.service;

import poly.edu.repository.CustomerDAO;
import poly.edu.repository.StaffDAO;
import poly.edu.model.Customer;
import poly.edu.model.Staff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private StaffDAO staffRepo;

    @Autowired
    private CustomerDAO customerRepo;

    // Lấy danh sách tất cả nhân viên
    public List<Staff> getAllStaff() {
        return staffRepo.findAll();
    }

    // Lấy danh sách tất cả khách hàng
    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    // Lưu hoặc cập nhật nhân viên
    public void saveStaff(Staff staff) {
        staffRepo.save(staff);
    }

    // Lưu hoặc cập nhật khách hàng
    public void saveCustomer(Customer customer) {
        customerRepo.save(customer);
    }

    // Xóa nhân viên
    public void deleteStaff(String maNV) {
        staffRepo.deleteById(maNV);
    }

    // Xóa khách hàng
    public void deleteCustomer(String maKH) {
        customerRepo.deleteById(maKH);
    }

    // Pagination and search for customers
    public Page<Customer> getCustomers(int page, int size, String keyword) {
        Pageable pageable = PageRequest.of(page, size);
        if (keyword == null || keyword.trim().isEmpty()) {
            return customerRepo.findAll(pageable);
        } else {
            return customerRepo.findByMaKHOrTenContaining(keyword, keyword, pageable);
        }
    }
    // Pagination and search for staff
    public Page<Staff> getStaff(int page, int size, String keyword) {
        Pageable pageable = PageRequest.of(page, size);
        if (keyword == null || keyword.trim().isEmpty()) {
            return staffRepo.findAll(pageable);
        } else {
            return staffRepo.findByMaNVOrTenNVContaining(keyword, keyword, pageable);
        }
    }

    // Existing methods preserved
    public Customer getCustomerById(String maKH) {
        return customerRepo.findById(maKH).orElse(null);
    }

    public Staff getStaffById(String maNV) {
        return staffRepo.findById(maNV).orElse(null);
    }
}
package poly.edu.service;

import poly.edu.repository.StaffDAO;
import poly.edu.model.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {

    @Autowired
    private StaffDAO staffDAO;

    public Staff findByUsernameAndPassword(String tenDN, String matKhau) {
        Staff staff = staffDAO.findByTenDNAndMatKhau(tenDN, matKhau);
        System.out.println("Staff from database: " + staff); // Debug
        return staff;
    }

    public List<Staff> getAllStaff() {
        return staffDAO.findAll();
    }

    public Staff getStaffById(String id) {
        return staffDAO.findById(id).orElse(null);
    }

    // Tự động sinh mã nhân viên mới
    private String generateNewMaNV() {
        // Lấy mã nhân viên lớn nhất hiện có
        Staff lastStaff = staffDAO.findTopByOrderByMaNVDesc();
        if (lastStaff == null) {
            return "NV001"; // Nếu chưa có nhân viên nào, bắt đầu từ NV001
        }
        // Lấy số từ mã nhân viên cuối cùng
        String lastMaNV = lastStaff.getMaNV();
        int number = Integer.parseInt(lastMaNV.replace("NV", "")) + 1;
        return "NV" + String.format("%03d", number); // Định dạng lại mã NV001, NV002,...
    }

    public void saveStaff(Staff staff) {
        if (staff.getMaNV() == null) {
            // Nếu mã nhân viên chưa có, sinh mã mới
            staff.setMaNV(generateNewMaNV());
        }
        staffDAO.save(staff);
    }

    public void deleteStaff(String id) {
        staffDAO.deleteById(id);
    }

    // Phương thức kiểm tra mã nhân viên có tồn tại không
    public boolean isMaNVExists(String maNV) {
        return staffDAO.findById(maNV).isPresent();
    }

    // New method for pagination and search
    public Page<Staff> getStaff(int page, int size, String keyword) {
        Pageable pageable = PageRequest.of(page, size);
        if (keyword == null || keyword.trim().isEmpty()) {
            return staffDAO.findAll(pageable);
        } else {
            return staffDAO.findByMaNVOrTenNVContaining(keyword, keyword, pageable);
        }
    }
}
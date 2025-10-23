package poly.edu.service;

import poly.edu.model.Customer;
import poly.edu.repository.CustomerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ThongKeTaiKhoanService {

    @Autowired
    private CustomerDAO customerDAO;

    public Map<String, Integer> thongKeTaiKhoanTheoNgay(String startDate, String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate + "T00:00:00");
        LocalDateTime end = LocalDateTime.parse(endDate + "T23:59:59");
        List<Customer> customers = customerDAO.findByNgayDangKyBetween(start, end);
        Map<String, Integer> taiKhoanTheoNgay = new HashMap<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (Customer customer : customers) {
            String ngayDangKy = customer.getNgayDangKy().format(formatter);
            taiKhoanTheoNgay.put(ngayDangKy, taiKhoanTheoNgay.getOrDefault(ngayDangKy, 0) + 1);
        }

        return taiKhoanTheoNgay;
    }

    public int tinhTongTaiKhoan(String startDate, String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate + "T00:00:00");
        LocalDateTime end = LocalDateTime.parse(endDate + "T23:59:59");
        List<Customer> customers = customerDAO.findByNgayDangKyBetween(start, end);
        return customers.size();
    }
}
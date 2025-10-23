package poly.edu.controller;

import poly.edu.service.ThongKeTaiKhoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class ThongKeTaiKhoanController {

    @Autowired
    private ThongKeTaiKhoanService thongKeTaiKhoanService;

    @GetMapping("/thongke-taikhoan")
    public String thongKeTaiKhoan(
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            Model model) {

        if (startDate != null && endDate != null) {
            Map<String, Integer> taiKhoanTheoNgay = thongKeTaiKhoanService.thongKeTaiKhoanTheoNgay(startDate, endDate);
            int tongTaiKhoan = thongKeTaiKhoanService.tinhTongTaiKhoan(startDate, endDate);

            model.addAttribute("taiKhoanTheoNgay", taiKhoanTheoNgay);
            model.addAttribute("tongTaiKhoan", tongTaiKhoan);
            model.addAttribute("startDate", startDate);
            model.addAttribute("endDate", endDate);
        }

        return "admin/thong_ke_tai_khoan";
    }
}
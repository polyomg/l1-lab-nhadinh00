// ThongKeController.java
package poly.edu.controller;

import poly.edu.service.ThongKeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class ThongKeController {

    @Autowired
    private ThongKeService thongKeService;

    @GetMapping("/thongke")
    public String thongKe(
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            Model model) {

        if (startDate != null && endDate != null) {
            Map<String, Integer> doanhThuTheoNgay = thongKeService.thongKeDoanhThuTheoNgay(startDate, endDate);
            int tongDoanhThu = thongKeService.tinhTongDoanhThu(startDate, endDate);
            Map<String, Integer> doanhThuTheoSanPham = thongKeService.thongKeDoanhThuTheoSanPham(startDate, endDate);

            model.addAttribute("doanhThuTheoNgay", doanhThuTheoNgay);
            model.addAttribute("tongDoanhThu", tongDoanhThu);
            model.addAttribute("doanhThuTheoSanPham", doanhThuTheoSanPham);
            model.addAttribute("startDate", startDate);
            model.addAttribute("endDate", endDate);
        }

        return "admin/thong_ke";
    }
}
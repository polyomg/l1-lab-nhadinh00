package poly.edu.controller;

import poly.edu.service.ThongKeLoaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class ThongKeLoaiController {

    @Autowired
    private ThongKeLoaiService thongKeLoaiService;

    @GetMapping("/thongke-loai")
    public String thongKeDoanhThuTheoLoai(
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            Model model) {

        if (startDate != null && endDate != null) {
            Map<String, Integer> doanhThuTheoLoai = thongKeLoaiService.thongKeDoanhThuTheoLoai(startDate, endDate);
            int tongDoanhThu = thongKeLoaiService.tinhTongDoanhThu(startDate, endDate);

            model.addAttribute("doanhThuTheoLoai", doanhThuTheoLoai);
            model.addAttribute("tongDoanhThu", tongDoanhThu);
            model.addAttribute("startDate", startDate);
            model.addAttribute("endDate", endDate);
        }

        return "admin/thong_ke_doanh_thu_theo_loai";
    }
}
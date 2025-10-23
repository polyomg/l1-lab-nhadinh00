package poly.edu.service;

import poly.edu.repository.HoaDonChiTietDAO;
import poly.edu.model.HoaDonChiTiet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ThongKeLoaiService {

    @Autowired
    private HoaDonChiTietDAO hoaDonChiTietDAO;

    public Map<String, Integer> thongKeDoanhThuTheoLoai(String startDate, String endDate) {
        List<HoaDonChiTiet> chiTietList = hoaDonChiTietDAO.findByHoaDon_NgayXuatBetween(startDate, endDate);
        Map<String, Integer> doanhThuTheoLoai = new HashMap<>();

        for (HoaDonChiTiet chiTiet : chiTietList) {
            if (chiTiet.getSanPham() != null && chiTiet.getSanPham().getLoaiSP() != null) {
                String tenLoai = chiTiet.getSanPham().getLoaiSP().getTenLoai();
                int tong = chiTiet.getTong() != null ? chiTiet.getTong() : 0; // Kiểm tra null cho tong
                doanhThuTheoLoai.put(tenLoai, doanhThuTheoLoai.getOrDefault(tenLoai, 0) + tong);
            }
        }

        return doanhThuTheoLoai;
    }

    public int tinhTongDoanhThu(String startDate, String endDate) {
        List<HoaDonChiTiet> chiTietList = hoaDonChiTietDAO.findByHoaDon_NgayXuatBetween(startDate, endDate);
        int tongDoanhThu = 0;
        for (HoaDonChiTiet chiTiet : chiTietList) {
            tongDoanhThu += chiTiet.getTong() != null ? chiTiet.getTong() : 0; // Kiểm tra null cho tong
        }
        return tongDoanhThu;
    }
}
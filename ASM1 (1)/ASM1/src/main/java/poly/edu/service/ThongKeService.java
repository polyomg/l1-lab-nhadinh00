// ThongKeService.java
package poly.edu.service;

import poly.edu.model.HoaDon;
import poly.edu.model.HoaDonChiTiet;
import poly.edu.repository.HoaDonChiTietDAO;
import poly.edu.repository.HoaDonDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ThongKeService {

    @Autowired
    private HoaDonDAO hoaDonDAO;

    @Autowired
    private HoaDonChiTietDAO hoaDonChiTietDAO;

    public Map<String, Integer> thongKeDoanhThuTheoNgay(String startDate, String endDate) {
        List<HoaDonChiTiet> chiTietList = hoaDonChiTietDAO.findByHoaDon_NgayXuatBetween(startDate, endDate);
        Map<String, Integer> doanhThuTheoNgay = new HashMap<>();

        for (HoaDonChiTiet chiTiet : chiTietList) {
            String ngayXuat = chiTiet.getHoaDon().getNgayXuat();
            int tong = chiTiet.getTong();
            doanhThuTheoNgay.put(ngayXuat, doanhThuTheoNgay.getOrDefault(ngayXuat, 0) + tong);
        }

        return doanhThuTheoNgay;
    }

    public int tinhTongDoanhThu(String startDate, String endDate) {
        List<HoaDonChiTiet> chiTietList = hoaDonChiTietDAO.findByHoaDon_NgayXuatBetween(startDate, endDate);
        int tongDoanhThu = 0;
        for (HoaDonChiTiet chiTiet : chiTietList) {
            tongDoanhThu += chiTiet.getTong();
        }
        return tongDoanhThu;
    }

    public Map<String, Integer> thongKeDoanhThuTheoSanPham(String startDate, String endDate) {
        List<HoaDonChiTiet> chiTietList = hoaDonChiTietDAO.findByHoaDon_NgayXuatBetween(startDate, endDate);
        Map<String, Integer> doanhThuTheoSanPham = new HashMap<>();

        for (HoaDonChiTiet chiTiet : chiTietList) {
            String tenSP = chiTiet.getTenSP();
            int tong = chiTiet.getTong();
            doanhThuTheoSanPham.put(tenSP, doanhThuTheoSanPham.getOrDefault(tenSP, 0) + tong);
        }

        return doanhThuTheoSanPham;
    }
}
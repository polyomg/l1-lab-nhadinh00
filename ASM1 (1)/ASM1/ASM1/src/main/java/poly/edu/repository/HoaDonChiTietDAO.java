package poly.edu.repository;

import poly.edu.model.HoaDonChiTiet;
import poly.edu.model.HoaDonChiTietId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HoaDonChiTietDAO extends JpaRepository<HoaDonChiTiet, HoaDonChiTietId> {
    List<HoaDonChiTiet> findByCustomer_MaKH(String maKH);
    
    @Query("SELECT hdct FROM HoaDonChiTiet hdct JOIN hdct.hoaDon hd WHERE hd.ngayXuat >= :startDate AND hd.ngayXuat <= :endDate")
    List<HoaDonChiTiet> findByNgayXuatBetween(@Param("startDate") String startDate, @Param("endDate") String endDate);

    @Query("SELECT SUM(hdct.tong) FROM HoaDonChiTiet hdct JOIN hdct.hoaDon hd WHERE hd.ngayXuat >= :startDate AND hd.ngayXuat <= :endDate")
    Integer calculateTotalRevenue(@Param("startDate") String startDate, @Param("endDate") String endDate);
    
    @Query("SELECT hct FROM HoaDonChiTiet hct WHERE hct.hoaDon.ngayXuat >= :startDate AND hct.hoaDon.ngayXuat <= :endDate")
    List<HoaDonChiTiet> findByHoaDon_NgayXuatBetween(@Param("startDate") String startDate, @Param("endDate") String endDate);
    
//    List<HoaDonChiTiet> findByHoaDon_NgayXuatBetween(String startDate, String endDate);
    
    @Query("SELECT h.sanPham.maSP, SUM(h.soLuong) as totalQuantity " +
            "FROM HoaDonChiTiet h " +
            "GROUP BY h.sanPham.maSP " +
            "ORDER BY totalQuantity DESC")
     List<Object[]> findTop10ByTotalQuantity(); // Returns [MaSP, totalQuantity]
 
}
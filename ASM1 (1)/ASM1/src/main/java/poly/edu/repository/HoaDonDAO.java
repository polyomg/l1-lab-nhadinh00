package poly.edu.repository;

import poly.edu.model.HoaDon;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HoaDonDAO extends JpaRepository<HoaDon, String> {
    @Query("SELECT MAX(h.maHD) FROM HoaDon h")
    String findLastMaHD();
    List<HoaDon> findByNgayXuatBetween(String startDate, String endDate);
	List<HoaDon> findByNgayXuatContaining(String format);
	
}
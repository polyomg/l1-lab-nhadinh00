package poly.edu.repository;

import poly.edu.model.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffDAO extends JpaRepository<Staff, String> {
    Staff findByTenDNAndMatKhau(String tenDN, String matKhau);

    Staff findTopByOrderByMaNVDesc();

    @Query("SELECT s FROM Staff s WHERE s.maNV LIKE %:keyword% OR s.tenNV LIKE %:keyword%")
    Page<Staff> findByMaNVOrTenNVContaining(String keyword, String keywordDuplicate, Pageable pageable);
}
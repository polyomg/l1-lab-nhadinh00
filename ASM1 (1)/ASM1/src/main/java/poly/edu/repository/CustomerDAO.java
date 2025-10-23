// CustomerDAO.java
package poly.edu.repository;

import poly.edu.model.Customer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerDAO extends JpaRepository<Customer, String> {
    Customer findByTenDN(String tenDN);
    Customer findByTenDNAndMatKhau(String tenDN, String matKhau);
	Customer findTopByOrderByMaKHDesc();
	List<Customer> findByNgayDangKyBetween(LocalDateTime start, LocalDateTime end);
	Page<Customer> findByMaKHContainingOrTenContaining(String keyword, String keyword2, Pageable pageable);
	
	@Query("SELECT c FROM Customer c WHERE c.maKH LIKE %:keyword% OR c.ten LIKE %:keyword%")
    Page<Customer> findByMaKHOrTenContaining(String keyword, String keywordDuplicate, Pageable pageable);
	
	Optional<Customer> findByEmail(String email);
}
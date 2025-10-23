package poly.edu.repository;

import poly.edu.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductDAO extends JpaRepository<Product, String> {
    List<Product> findTop5ByOrderByMaSPAsc();
    List<Product> findByTenSPContainingIgnoreCase(String keyword);
    List<Product> findByLoaiSP_MaLoai(int maLoai);
    List<Product> findByGiaSPBetween(int minPrice, int maxPrice);
    List<Product> findAllByOrderByMaSPAsc();
    
    @Query("SELECT p FROM Product p WHERE p.loaiSP.maLoai = :categoryId AND p.giaSP BETWEEN :minPrice AND :maxPrice")
    List<Product> findByCategoryIdAndPriceRange(@Param("categoryId") int categoryId, @Param("minPrice") int minPrice, @Param("maxPrice") int maxPrice);

    @Query("SELECT p FROM Product p WHERE p.tenSP LIKE %:keyword% OR p.maSP LIKE %:keyword%")
    List<Product> searchByKeyword(@Param("keyword") String keyword);

    @Query("SELECT p FROM Product p WHERE p.tenSP LIKE %:keyword% OR p.maSP LIKE %:keyword%")
    List<Product> searchByKeyword(@Param("keyword") String keyword, org.springframework.data.domain.Pageable pageable);

    List<Product> findAllByOrderByMaSPAsc(org.springframework.data.domain.Pageable pageable);
    
    List<Product> findByMaSPIn(List<String> maSPs);
}
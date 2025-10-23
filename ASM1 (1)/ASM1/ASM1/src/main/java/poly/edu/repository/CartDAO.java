package poly.edu.repository;

import poly.edu.model.Cart;
import poly.edu.model.CartId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartDAO extends JpaRepository<Cart, CartId> {
    List<Cart> findByMaKH(String maKH);
    Optional<Cart> findByMaKHAndMaSP(String maKH, String maSP);
    void deleteByMaKHAndMaSP(String maKH, String maSP);
	void deleteByMaKH(String maKH);
}
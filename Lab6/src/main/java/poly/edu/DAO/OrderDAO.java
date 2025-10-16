package poly.edu.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import poly.edu.entity.Order;

public interface OrderDAO extends JpaRepository<Order, Long> {}

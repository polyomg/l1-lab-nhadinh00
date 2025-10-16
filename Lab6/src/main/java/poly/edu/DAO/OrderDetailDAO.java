package poly.edu.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import poly.edu.entity.OrderDetail;

public interface OrderDetailDAO extends JpaRepository<OrderDetail, Long> {}

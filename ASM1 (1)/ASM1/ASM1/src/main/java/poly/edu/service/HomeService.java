package poly.edu.service;

import poly.edu.model.Product;
import poly.edu.repository.ProductDAO;
import poly.edu.repository.HoaDonChiTietDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HomeService {

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private HoaDonChiTietDAO hoaDonChiTietDAO;

    public List<Product> getTop10BestSellingProducts() {
        // Get top 10 products by total quantity sold
        List<Object[]> topProducts = hoaDonChiTietDAO.findTop10ByTotalQuantity();
        List<String> productIds = topProducts.stream()
                .map(row -> (String) row[0]) // MaSP is the first column
                .collect(Collectors.toList());

        // Fetch Product entities for these IDs
        return productDAO.findByMaSPIn(productIds);
    }

    // New method to get total sold quantities as a map
    public Map<String, Integer> getTotalSoldForProducts() {
        List<Object[]> totalSoldData = hoaDonChiTietDAO.findTop10ByTotalQuantity();
        return totalSoldData.stream()
                .collect(Collectors.toMap(
                        row -> (String) row[0], // MaSP
                        row -> ((Number) row[1]).intValue() // Total quantity sold
                ));
    }
}
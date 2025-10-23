package poly.edu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poly.edu.repository.ProductDAO;
import poly.edu.model.Product;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductDAO productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getTop5Products() {
        return productRepository.findTop5ByOrderByMaSPAsc();
    }

    public Optional<Product> getProductById(String maSP) {
        return productRepository.findById(maSP);
    }

    @Transactional
    public Product saveProduct(Product product) {
        if (product.getMaSP() == null || product.getMaSP().trim().isEmpty()) {
            String newProductCode = generateNextProductCode();
            product.setMaSP(newProductCode);
        }
        return productRepository.save(product);
    }

    public Product updateProduct(String maSP, Product updatedProduct) {
        return productRepository.findById(maSP).map(product -> {
            product.setTenSP(updatedProduct.getTenSP());
            product.setGiaSP(updatedProduct.getGiaSP());
            product.setLoaiSP(updatedProduct.getLoaiSP());
            product.setHinhSP(updatedProduct.getHinhSP());
            product.setHinhNenSP(updatedProduct.getHinhNenSP());
            product.setChiTietSP(updatedProduct.getChiTietSP());
            return productRepository.save(product);
        }).orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với mã: " + maSP));
    }

    @Transactional
    public void deleteProduct(String maSP) {
        productRepository.deleteById(maSP);
    }

    private String generateNextProductCode() {
        List<Product> products = productRepository.findAllByOrderByMaSPAsc();
        if (products.isEmpty()) {
            return "SP001";
        }
        int maxNumber = 0;
        for (Product product : products) {
            String code = product.getMaSP().replaceAll("[^0-9]", "");
            if (!code.isEmpty()) {
                int number = Integer.parseInt(code);
                maxNumber = Math.max(maxNumber, number);
            }
        }
        return String.format("SP%03d", maxNumber + 1);
    }

    public List<Product> searchProductsByName(String keyword) {
        return productRepository.findByTenSPContainingIgnoreCase(keyword);
    }

    public List<Product> getProductsByCategoryId(int categoryId) {
        return productRepository.findByLoaiSP_MaLoai(categoryId);
    }

    public List<Product> getProductsByPriceRange(int minPrice, int maxPrice) {
        return productRepository.findByGiaSPBetween(minPrice, maxPrice);
    }
    
    public List<Product> getProductsByCategoryAndPrice(int categoryId, int minPrice, int maxPrice) {
        return productRepository.findByCategoryIdAndPriceRange(categoryId, minPrice, maxPrice);
    }

    public List<Product> getProductsByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAllByOrderByMaSPAsc(pageable);
    }

    public List<Product> searchProductsByKeyword(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        if (keyword == null || keyword.trim().isEmpty()) {
            return productRepository.findAllByOrderByMaSPAsc(pageable);
        }
        return productRepository.searchByKeyword(keyword, pageable);
    }

    public long getTotalProducts() {
        return productRepository.count();
    }
}
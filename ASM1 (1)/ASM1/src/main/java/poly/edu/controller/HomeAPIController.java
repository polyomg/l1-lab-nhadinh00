package poly.edu.controller;

import poly.edu.dto.LoaiSPDTO;
import poly.edu.dto.ProductDTO;
import poly.edu.model.Product;
import poly.edu.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/home")
@CrossOrigin(origins = "*")	
public class HomeAPIController {

    @Autowired
    private HomeService homeService;

    @GetMapping("/products")
    public List<ProductDTO> getProducts() {
        // Get the top 10 best-selling products
        List<Product> products = homeService.getTop10BestSellingProducts();

        // Get total sold quantities for all products in one query
        Map<String, Integer> totalSoldMap = homeService.getTotalSoldForProducts();

        return products.stream()
                .map(product -> {
                    ProductDTO productDTO = new ProductDTO();
                    productDTO.setMaSP(product.getMaSP());
                    productDTO.setTenSP(product.getTenSP());
                    productDTO.setGiaSP(product.getGiaSP());
                    productDTO.setHinhSP(product.getHinhSP());
                    productDTO.setHinhNenSP(product.getHinhNenSP());

                    // Set total sold from the aggregated map
                    Integer totalSold = totalSoldMap.getOrDefault(product.getMaSP(), 0);
                    productDTO.setTotalSold(totalSold);

                    LoaiSPDTO loaiSPDTO = new LoaiSPDTO();
                    if (product.getLoaiSP() != null) {
                        loaiSPDTO.setMaLoai(product.getLoaiSP().getMaLoai());
                        loaiSPDTO.setTenLoai(product.getLoaiSP().getTenLoai());
                    }
                    productDTO.setLoaiSP(loaiSPDTO);

                    return productDTO;
                })
                .collect(Collectors.toList());
    }
}
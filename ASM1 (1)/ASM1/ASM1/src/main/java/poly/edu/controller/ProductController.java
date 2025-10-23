package poly.edu.controller;

import poly.edu.model.Product;
import poly.edu.service.ProductService;
import poly.edu.repository.LoaiSPDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/admin/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private LoaiSPDAO loaiSPDAO;

    // 🔧 Đường dẫn thật để lưu file (tự động map ra /images/**)
    private static final String UPLOAD_DIR = "src/main/resources/static/images/";
    private static final int PAGE_SIZE = 10;

    @GetMapping
    public String showProductAdminPage(Model model,
                                       @RequestParam(value = "page", defaultValue = "0") int page,
                                       @RequestParam(value = "keyword", required = false) String keyword) {
        model.addAttribute("product", new Product());
        List<Product> products = productService.searchProductsByKeyword(keyword, page, PAGE_SIZE);
        model.addAttribute("products", products);
        long totalProducts = productService.getTotalProducts();
        int totalPages = (int) Math.ceil((double) totalProducts / PAGE_SIZE);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        model.addAttribute("loaiSPs", loaiSPDAO.findAll());
        return "admin/product-admin";
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute Product product,
                              @RequestParam("fileHinhSP") MultipartFile fileHinhSP,
                              @RequestParam("fileHinhNenSP") MultipartFile fileHinhNenSP) {
        try {
            // 🔧 Tạo thư mục nếu chưa có
            File uploadFolder = new File(UPLOAD_DIR);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }

            // 🔸 Xử lý ảnh sản phẩm chính
            if (!fileHinhSP.isEmpty()) {
                String fileNameHinhSP = UUID.randomUUID() + "_" + fileHinhSP.getOriginalFilename();
                Path pathHinhSP = Paths.get(UPLOAD_DIR + fileNameHinhSP);
                Files.write(pathHinhSP, fileHinhSP.getBytes());
                // Đường dẫn để hiển thị trong trình duyệt
                product.setHinhSP("/images/" + fileNameHinhSP);
            } else {
                // Giữ lại hình cũ khi edit
                if (product.getMaSP() != null) {
                    Product existingProduct = productService.getProductById(product.getMaSP()).orElse(null);
                    if (existingProduct != null) {
                        product.setHinhSP(existingProduct.getHinhSP());
                    }
                }
            }

            // 🔸 Xử lý ảnh nền sản phẩm
            if (!fileHinhNenSP.isEmpty()) {
                String fileNameHinhNenSP = UUID.randomUUID() + "_" + fileHinhNenSP.getOriginalFilename();
                Path pathHinhNenSP = Paths.get(UPLOAD_DIR + fileNameHinhNenSP);
                Files.write(pathHinhNenSP, fileHinhNenSP.getBytes());
                product.setHinhNenSP("/images/" + fileNameHinhNenSP);
            } else {
                // Giữ lại ảnh nền cũ
                if (product.getMaSP() != null) {
                    Product existingProduct = productService.getProductById(product.getMaSP()).orElse(null);
                    if (existingProduct != null) {
                        product.setHinhNenSP(existingProduct.getHinhNenSP());
                    }
                }
            }

            // 🔸 Lưu sản phẩm
            productService.saveProduct(product);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/admin/products";
    }

    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable String id, Model model) {
        Optional<Product> product = productService.getProductById(id);
        model.addAttribute("product", product.orElse(new Product()));
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("loaiSPs", loaiSPDAO.findAll());
        return "admin/product-admin";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }
}

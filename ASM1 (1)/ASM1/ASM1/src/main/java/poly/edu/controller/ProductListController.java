package poly.edu.controller;

import poly.edu.model.Customer;
import poly.edu.model.LoaiSP;
import poly.edu.model.Product;
import poly.edu.service.CustomerService;
import poly.edu.service.LoaiSPService;
import poly.edu.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductListController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private LoaiSPService loaiSPService;

    @GetMapping
    public String listProducts(
            @RequestParam(value = "minPrice", required = false, defaultValue = "0") int minPrice,
            @RequestParam(value = "maxPrice", required = false, defaultValue = "2147483647") int maxPrice,
            @RequestParam(value = "categoryId", required = false, defaultValue = "-1") int categoryId,
            Model model,
            HttpSession session) {

        // Lấy danh sách sản phẩm dựa trên cả hai tiêu chí lọc
        List<Product> products;
        if (categoryId == -1) {
            // Không lọc theo category, chỉ lọc theo giá
            products = productService.getProductsByPriceRange(minPrice, maxPrice);
        } else {
            // Lọc theo cả category và giá
            products = productService.getProductsByCategoryAndPrice(categoryId, minPrice, maxPrice);
        }

        List<LoaiSP> categories = loaiSPService.getAllLoaiSP();
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);

        // Xác định loại sản phẩm được chọn (nếu có)
        String selectedCategory = null;
        if (categoryId != -1) {
            LoaiSP selectedLoaiSP = loaiSPService.getLoaiSPById(categoryId);
            if (selectedLoaiSP != null) {
                selectedCategory = selectedLoaiSP.getTenLoai();
            }
        }
        model.addAttribute("selectedCategory", selectedCategory);

        // Lưu giá trị minPrice và maxPrice để hiển thị lại trên giao diện
        model.addAttribute("minPrice", minPrice == 0 ? "" : minPrice);
        model.addAttribute("maxPrice", maxPrice == 2147483647 ? "" : maxPrice);

        // Thông tin khách hàng
        String maKH = (String) session.getAttribute("maKH");
        if (maKH != null) {
            Customer currentCustomer = customerService.getCustomerById(maKH);
            model.addAttribute("currentCustomer", currentCustomer);
        }

        return "product/list";
    }

    @GetMapping("/category/{categoryId}")
    public String filterProductsByCategory(
            @PathVariable("categoryId") int categoryId,
            @RequestParam(value = "minPrice", required = false, defaultValue = "0") int minPrice,
            @RequestParam(value = "maxPrice", required = false, defaultValue = "2147483647") int maxPrice,
            Model model,
            HttpSession session) {
        return listProducts(minPrice, maxPrice, categoryId, model, session);
    }

    @GetMapping("/filter")
    public String filterProductsByPrice(
            @RequestParam("minPrice") int minPrice,
            @RequestParam("maxPrice") int maxPrice,
            @RequestParam(value = "categoryId", required = false, defaultValue = "-1") int categoryId,
            Model model,
            HttpSession session) {
        return listProducts(minPrice, maxPrice, categoryId, model, session);
    }

    @GetMapping("/detail/{maSP}")
    public String productDetail(@PathVariable("maSP") String maSP, Model model, HttpSession session) {
        Product product = productService.getProductById(maSP)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với mã: " + maSP));
        model.addAttribute("product", product);

        System.out.println("Hình nền sản phẩm: " + product.getHinhNenSP());
        String maKH = (String) session.getAttribute("maKH");
        if (maKH != null) {
            Customer currentCustomer = customerService.getCustomerById(maKH);
            model.addAttribute("currentCustomer", currentCustomer);
        }

        return "product/detail";
    }

    @GetMapping("/search")
    public String searchProducts(@RequestParam("keyword") String keyword, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        List<Product> products = productService.searchProductsByName(keyword);
        List<LoaiSP> categories = loaiSPService.getAllLoaiSP();
        model.addAttribute("categories", categories);

        String maKH = (String) session.getAttribute("maKH");
        if (maKH != null) {
            Customer currentCustomer = customerService.getCustomerById(maKH);
            model.addAttribute("currentCustomer", currentCustomer);
        }

        if (products.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Không có sản phẩm khớp với từ khóa bạn tìm kiếm.");
            return "redirect:/products";
        }

        model.addAttribute("products", products);
        return "product/list";
    }
}
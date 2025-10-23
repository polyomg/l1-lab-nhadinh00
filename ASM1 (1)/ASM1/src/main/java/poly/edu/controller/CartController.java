package poly.edu.controller;

import poly.edu.repository.CartDAO;
import poly.edu.repository.CustomerDAO;
import poly.edu.repository.HoaDonChiTietDAO;
import poly.edu.repository.HoaDonDAO;
import poly.edu.repository.ProductDAO;
import poly.edu.model.*;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
public class CartController {

    @Autowired
    private CartDAO cartDAO;

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private CustomerDAO customerDAO;

    @Autowired
    private HoaDonDAO hoaDonDAO;

    @Autowired
    private HoaDonChiTietDAO hoaDonChiTietDAO;

    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam String maKH, @RequestParam String maSP, @RequestParam String tenSP,
                           @RequestParam String hinhSP, @RequestParam int soLuong,
                           @RequestParam int giaSP, HttpSession session, RedirectAttributes redirectAttributes) {
        // Kiểm tra khách hàng có tồn tại không
        Customer customer = customerDAO.findById(maKH).orElse(null);
        if (customer == null) {
            redirectAttributes.addFlashAttribute("message", "Vui lòng đăng nhập để thêm sản phẩm vào giỏ hàng.");
            return "redirect:/login";
        }

        // Kiểm tra sản phẩm có tồn tại không
        Product product = productDAO.findById(maSP).orElse(null);
        if (product == null) {
            redirectAttributes.addFlashAttribute("message", "Sản phẩm không tồn tại.");
            return "redirect:/products";
        }

        // Kiểm tra sản phẩm đã có trong giỏ hàng chưa
        Optional<Cart> existingCartItem = cartDAO.findByMaKHAndMaSP(maKH, maSP);
        if (existingCartItem.isPresent()) {
            Cart cart = existingCartItem.get();
            redirectAttributes.addFlashAttribute("message", 
                "Sản phẩm " + tenSP + " đã được thêm vào giỏ hàng của bạn trước đó! Với số lượng là " + cart.getSoLuong());
            return "redirect:/products";
        }

        // Thêm sản phẩm mới vào giỏ hàng
        Cart cart = new Cart();
        cart.setMaKH(maKH);
        cart.setMaSP(maSP);
        cart.setTenSP(tenSP);
        cart.setHinhSP(hinhSP);
        cart.setSoLuong(soLuong);
        cart.setGiaSP(giaSP);
        cart.setTong(soLuong * giaSP);
        cart.setCustomer(customer);
        cart.setSanPham(product);

        cartDAO.save(cart);

        redirectAttributes.addFlashAttribute("message", "Sản phẩm " + tenSP + " đã được thêm vào giỏ hàng thành công!");
        return "redirect:/products"; // Không chuyển hướng đến giỏ hàng, mà quay lại trang sản phẩm
    }

    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {
        String maKH = (String) session.getAttribute("maKH");
        if (maKH == null) {
            return "redirect:/login";
        }

        List<Cart> cartItems = cartDAO.findByMaKH(maKH);
        model.addAttribute("cartItems", cartItems);

        int total = cartItems.stream().mapToInt(Cart::getTong).sum();
        model.addAttribute("total", total);

        return "cart";
    }

    @PostMapping("/remove-from-cart")
    @Transactional
    public String removeFromCart(@RequestParam String maKH, @RequestParam String maSP) {
        cartDAO.deleteByMaKHAndMaSP(maKH, maSP);
        return "redirect:/cart";
    }

    @PostMapping("/update-cart-quantity")
    @Transactional
    public String updateCartQuantity(@RequestParam String maKH, @RequestParam String maSP, @RequestParam int soLuong, Model model, RedirectAttributes redirectAttributes) {
        Optional<Cart> optionalCart = cartDAO.findByMaKHAndMaSP(maKH, maSP);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            Product product = productDAO.findById(maSP).orElse(null);

            if (product != null && soLuong > product.getSoLuong()) {
                // Thêm thông báo lỗi vào redirectAttributes
                redirectAttributes.addFlashAttribute("errorMessage", "Sản phẩm " + product.getTenSP() + " chỉ còn lại " + product.getSoLuong() + " sản phẩm! Vui lòng không đặt mua số lượng lớn hơn " + product.getSoLuong() +" sản phẩm!");
                return "redirect:/cart";
            }

            cart.setSoLuong(soLuong);
            cart.setTong(soLuong * cart.getGiaSP());
            cartDAO.save(cart);
        }
        return "redirect:/cart";
    }

    @PostMapping("/checkout-item")
    @Transactional
    public String checkoutItem(@RequestParam String maKH, @RequestParam String maSP, RedirectAttributes redirectAttributes) {
        Optional<Cart> optionalCart = cartDAO.findByMaKHAndMaSP(maKH, maSP);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            Product product = productDAO.findById(maSP).orElse(null);

            if (product != null) {
                // Kiểm tra số lượng sản phẩm còn lại
                if (cart.getSoLuong() > product.getSoLuong()) {
                    redirectAttributes.addFlashAttribute("errorMessage", "Sản phẩm " + product.getTenSP() + " chỉ còn lại " + product.getSoLuong() + " sản phẩm! Vui lòng không đặt mua số lượng lớn hơn " + product.getSoLuong() + " sản phẩm!");
                    return "redirect:/cart";
                }

                // Giảm số lượng sản phẩm trong bảng san_pham
                product.setSoLuong(product.getSoLuong() - cart.getSoLuong());
                productDAO.save(product);

                // Tạo hóa đơn và chi tiết hóa đơn
                createHoaDonAndChiTiet(cart);

                // Xóa sản phẩm khỏi giỏ hàng
                cartDAO.delete(cart);
            }
        }
        return "redirect:/cart";
    }

    @PostMapping("/checkout-all")
    @Transactional
    public String checkoutAll(HttpSession session, RedirectAttributes redirectAttributes) {
        String maKH = (String) session.getAttribute("maKH");
        if (maKH != null) {
            List<Cart> cartItems = cartDAO.findByMaKH(maKH);
            for (Cart cart : cartItems) {
                Product product = productDAO.findById(cart.getMaSP()).orElse(null);

                if (product != null) {
                    // Kiểm tra số lượng sản phẩm còn lại
                    if (cart.getSoLuong() > product.getSoLuong()) {
                        redirectAttributes.addFlashAttribute("errorMessage", "Sản phẩm " + product.getTenSP() + " chỉ còn lại " + product.getSoLuong() + " sản phẩm! Vui lòng không đặt mua số lượng lớn hơn " + product.getSoLuong() + " sản phẩm!");
                        return "redirect:/cart";
                    }

                    // Giảm số lượng sản phẩm trong bảng san_pham
                    product.setSoLuong(product.getSoLuong() - cart.getSoLuong());
                    productDAO.save(product);

                    // Tạo hóa đơn và chi tiết hóa đơn
                    createHoaDonAndChiTiet(cart);
                }
            }
            // Xóa toàn bộ giỏ hàng sau khi thanh toán
            cartDAO.deleteByMaKH(maKH);
        }
        return "redirect:/cart";
    }

    private void createHoaDonAndChiTiet(Cart cart) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String ngayXuat = now.format(dateFormatter);
        String gioXuat = now.format(timeFormatter);

        String lastMaHD = hoaDonDAO.findLastMaHD();
        int nextNumber = 1;
        if (lastMaHD != null) {
            try {
                nextNumber = Integer.parseInt(lastMaHD) + 1;
            } catch (NumberFormatException e) {
                // Xử lý lỗi nếu chuỗi không phải là số
            }
        }
        String newMaHD = String.format("%03d", nextNumber); // Định dạng mã hóa đơn thành 3 chữ số

        HoaDon hoaDon = new HoaDon();
        hoaDon.setMaHD(newMaHD);
        hoaDon.setNgayXuat(ngayXuat);
        hoaDon.setGioXuat(gioXuat);
        hoaDon.setCustomer(cart.getCustomer());
        hoaDonDAO.save(hoaDon);

        HoaDonChiTietId chiTietId = new HoaDonChiTietId();
        chiTietId.setMaHD(hoaDon.getMaHD());
        chiTietId.setMaSP(cart.getMaSP());

        HoaDonChiTiet chiTiet = new HoaDonChiTiet();
        chiTiet.setId(chiTietId);
        chiTiet.setHoaDon(hoaDon);
        chiTiet.setSanPham(cart.getSanPham());
        chiTiet.setCustomer(cart.getCustomer());
        chiTiet.setTenSP(cart.getTenSP());
        chiTiet.setSoLuong(cart.getSoLuong());
        chiTiet.setGiaSP(cart.getGiaSP());
        chiTiet.setTong(cart.getTong());
        hoaDonChiTietDAO.save(chiTiet);
    }

    @GetMapping("/buy-history")
    public String buyHistory(HttpSession session, Model model) {
        String maKH = (String) session.getAttribute("maKH");
        if (maKH != null) {
            List<HoaDonChiTiet> history = hoaDonChiTietDAO.findByCustomer_MaKH(maKH);
            model.addAttribute("history", history);
        }
        return "buy-history";
    }
}
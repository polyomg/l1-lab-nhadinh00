package poly.edu.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import poly.edu.model.*;
import poly.edu.repository.*;

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

    // ================== THÊM GIỎ HÀNG ===================
    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam String maKH,
                            @RequestParam String maSP,
                            @RequestParam String tenSP,
                            @RequestParam String hinhSP,
                            @RequestParam int soLuong,
                            @RequestParam int giaSP,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {

        Customer customer = customerDAO.findById(maKH).orElse(null);
        if (customer == null) {
            redirectAttributes.addFlashAttribute("message", "Vui lòng đăng nhập để thêm sản phẩm vào giỏ hàng.");
            return "redirect:/login";
        }

        Product product = productDAO.findById(maSP).orElse(null);
        if (product == null) {
            redirectAttributes.addFlashAttribute("message", "Sản phẩm không tồn tại.");
            return "redirect:/products";
        }

        Optional<Cart> existingCart = cartDAO.findByMaKHAndMaSP(maKH, maSP);
        if (existingCart.isPresent()) {
            redirectAttributes.addFlashAttribute("message",
                    "Sản phẩm " + tenSP + " đã có trong giỏ hàng! Số lượng hiện tại: " + existingCart.get().getSoLuong());
            return "redirect:/products";
        }

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

        redirectAttributes.addFlashAttribute("message", "Đã thêm " + tenSP + " vào giỏ hàng!");
        return "redirect:/products";
    }

    // ================== XEM GIỎ HÀNG ===================
    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {
        String maKH = (String) session.getAttribute("maKH");
        if (maKH == null) return "redirect:/login";

        List<Cart> cartItems = cartDAO.findByMaKH(maKH);
        int total = cartItems.stream().mapToInt(Cart::getTong).sum();

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);
        return "cart";
    }

    // ================== CẬP NHẬT SỐ LƯỢNG ===================
    @PostMapping("/update-cart-quantity")
    @Transactional
    public String updateCartQuantity(@RequestParam String maKH,
                                     @RequestParam String maSP,
                                     @RequestParam int soLuong,
                                     RedirectAttributes redirectAttributes) {
        Optional<Cart> opt = cartDAO.findByMaKHAndMaSP(maKH, maSP);
        if (opt.isPresent()) {
            Cart cart = opt.get();
            Product product = productDAO.findById(maSP).orElse(null);

            if (product != null && soLuong > product.getSoLuong()) {
                redirectAttributes.addFlashAttribute("errorMessage",
                        "Sản phẩm " + product.getTenSP() + " chỉ còn " + product.getSoLuong() + " sản phẩm!");
                return "redirect:/cart";
            }

            cart.setSoLuong(soLuong);
            cart.setTong(soLuong * cart.getGiaSP());
            cartDAO.save(cart);
        }
        return "redirect:/cart";
    }

    // ================== XOÁ KHỎI GIỎ ===================
    @PostMapping("/remove-from-cart")
    @Transactional
    public String removeFromCart(@RequestParam String maKH, @RequestParam String maSP) {
        cartDAO.deleteByMaKHAndMaSP(maKH, maSP);
        return "redirect:/cart";
    }

    // ================== CHUYỂN SANG TRANG THANH TOÁN ===================
    @PostMapping("/checkout-item")
    public String checkoutItem(@RequestParam String maKH,
                               @RequestParam String maSP,
                               RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("maKH", maKH);
        redirectAttributes.addAttribute("maSP", maSP);
        return "redirect:/checkout";
    }

    @PostMapping("/checkout-all")
    public String checkoutAll(HttpSession session, RedirectAttributes redirectAttributes) {
        String maKH = (String) session.getAttribute("maKH");
        redirectAttributes.addAttribute("maKH", maKH);
        return "redirect:/checkout";
    }

    // ================== HIỂN THỊ FORM THANH TOÁN ===================
    @GetMapping("/checkout")
    public String showCheckout(@RequestParam String maKH,
                               @RequestParam(required = false) String maSP,
                               Model model) {
        List<Cart> cartItems;
        if (maSP != null) {
            cartItems = List.of(cartDAO.findByMaKHAndMaSP(maKH, maSP).orElseThrow());
        } else {
            cartItems = cartDAO.findByMaKH(maKH);
        }

        Customer customer = customerDAO.findById(maKH).orElse(null);
        int total = cartItems.stream().mapToInt(Cart::getTong).sum();

        model.addAttribute("customer", customer);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);
        return "checkout";
    }

    // ================== XÁC NHẬN THANH TOÁN ===================
    @PostMapping("/confirm-checkout")
    @Transactional
    public String confirmCheckout(@RequestParam String maKH, RedirectAttributes redirectAttributes) {
        List<Cart> cartItems = cartDAO.findByMaKH(maKH);

        for (Cart cart : cartItems) {
            Product product = productDAO.findById(cart.getMaSP()).orElse(null);
            if (product == null) continue;

            if (cart.getSoLuong() > product.getSoLuong()) {
                redirectAttributes.addFlashAttribute("errorMessage",
                        "Sản phẩm " + product.getTenSP() + " chỉ còn " + product.getSoLuong());
                return "redirect:/checkout?maKH=" + maKH;
            }

            product.setSoLuong(product.getSoLuong() - cart.getSoLuong());
            productDAO.save(product);
            createHoaDonAndChiTiet(cart);
        }

        cartDAO.deleteByMaKH(maKH);
        return "redirect:/buy-history";
    }

    // ================== HÓA ĐƠN ===================
    private void createHoaDonAndChiTiet(Cart cart) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dfDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter dfTime = DateTimeFormatter.ofPattern("HH:mm:ss");

        String ngayXuat = now.format(dfDate);
        String gioXuat = now.format(dfTime);

        String lastMaHD = hoaDonDAO.findLastMaHD();
        int nextNumber = 1;
        if (lastMaHD != null) {
            try {
                nextNumber = Integer.parseInt(lastMaHD) + 1;
            } catch (NumberFormatException ignored) {}
        }

        String newMaHD = String.format("%03d", nextNumber);

        HoaDon hoaDon = new HoaDon();
        hoaDon.setMaHD(newMaHD);
        hoaDon.setNgayXuat(ngayXuat);
        hoaDon.setGioXuat(gioXuat);
        hoaDon.setCustomer(cart.getCustomer());
        hoaDonDAO.save(hoaDon);

        HoaDonChiTietId chiTietId = new HoaDonChiTietId();
        chiTietId.setMaHD(newMaHD);
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

    // ================== LỊCH SỬ MUA HÀNG ===================
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

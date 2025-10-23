package poly.edu.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Table(name = "customer")
@Entity
@Getter
@Setter
public class Customer {

    @Id
    @Column(name = "MaKH", length = 50)
    private String maKH;  // Mã khách hàng

    @Column(name = "TenKH", length = 100)
    private String ten;  // Tên khách hàng

    @Column(name = "GioiTinh")
    private Boolean gioiTinh;  // Giới tính (TRUE = Nam, FALSE = Nữ)

    @Column(name = "NgaySinh", length = 20)
    private String ngaySinh;  // Ngày sinh

    @Column(name = "SDT", length = 15)
    private String sdt;  // Số điện thoại

    @Column(name = "Email", length = 100)
    private String email;  // Email

    @Column(name = "TenDN", length = 50)
    private String tenDN;  // Username

    @Column(name = "MatKhau", length = 255)
    private String matKhau;  // Mật khẩu

    @Column(name = "Avatar")
    private String avatar;  // Avatar

    @Column(name = "NgayDangKy")
    @CreationTimestamp
    private LocalDateTime ngayDangKy;  // Ngày đăng ký
}

package poly.edu.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "HoaDonChiTiet")
@Getter
@Setter
@NoArgsConstructor
public class HoaDonChiTiet {

    @EmbeddedId
    private HoaDonChiTietId id;   // Khóa chính gồm MaHD + MaSP

    @ManyToOne
    @MapsId("maHD")   // Ánh xạ MaHD từ EmbeddedId
    @JoinColumn(name = "MaHD")
    private HoaDon hoaDon;

    @ManyToOne
    @MapsId("maSP")   // Ánh xạ MaSP từ EmbeddedId
    @JoinColumn(name = "MaSP")
    private Product sanPham;

    @ManyToOne
    @JoinColumn(name = "MaKH")
    private Customer customer;

    @Column(name = "TenSP")
    private String tenSP;

    @Column(name = "SoLuong")
    private Integer soLuong;

    @Column(name = "GiaSP")
    private Integer giaSP;

    @Column(name = "Tong")
    private Integer tong;
}

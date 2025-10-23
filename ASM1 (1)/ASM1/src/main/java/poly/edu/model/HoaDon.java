package poly.edu.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "HoaDon")
public class HoaDon {

    @Id
    @Column(name = "MaHD", length = 50)
    private String maHD;

    @Column(name = "NgayXuat")
    private String ngayXuat;

    @Column(name = "GioXuat")
    private String gioXuat;

    @ManyToOne
    @JoinColumn(name = "MaKH")
    private Customer customer;

    @OneToMany(mappedBy = "hoaDon", cascade = CascadeType.ALL)
    private List<HoaDonChiTiet> chiTietList;

    // Getter & Setter
    public String getMaHD() { return maHD; }
    public void setMaHD(String maHD) { this.maHD = maHD; }

    public String getNgayXuat() { return ngayXuat; }
    public void setNgayXuat(String ngayXuat) { this.ngayXuat = ngayXuat; }

    public String getGioXuat() { return gioXuat; }
    public void setGioXuat(String gioXuat) { this.gioXuat = gioXuat; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public List<HoaDonChiTiet> getChiTietList() { return chiTietList; }
    public void setChiTietList(List<HoaDonChiTiet> chiTietList) { this.chiTietList = chiTietList; }
}

package poly.edu.model;

import jakarta.persistence.*;

@Entity
@IdClass(CartId.class)
@Table(name = "Cart")
public class Cart {

    @Id
    @Column(name = "MaKH", length = 50)
    private String maKH;

    @Id
    @Column(name = "MaSP", length = 50)
    private String maSP;

    @Column(name = "TenSP", length = 255)
    private String tenSP;

    @Column(name = "HinhSP")
    private String hinhSP;

    @Column(name = "SoLuong")
    private int soLuong;

    @Column(name = "GiaSP")
    private int giaSP;

    @Column(name = "Tong")
    private int tong;

    @ManyToOne
    @JoinColumn(name = "MaKH", insertable = false, updatable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "MaSP", insertable = false, updatable = false)
    private Product sanPham;

    // Getters & Setters
    public String getMaKH() { return maKH; }
    public void setMaKH(String maKH) { this.maKH = maKH; }

    public String getMaSP() { return maSP; }
    public void setMaSP(String maSP) { this.maSP = maSP; }

    public String getTenSP() { return tenSP; }
    public void setTenSP(String tenSP) { this.tenSP = tenSP; }

    public String getHinhSP() { return hinhSP; }
    public void setHinhSP(String hinhSP) { this.hinhSP = hinhSP; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }

    public int getGiaSP() { return giaSP; }
    public void setGiaSP(int giaSP) { this.giaSP = giaSP; }

    public int getTong() { return tong; }
    public void setTong(int tong) { this.tong = tong; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public Product getSanPham() { return sanPham; }
    public void setSanPham(Product sanPham) { this.sanPham = sanPham; }
}

package poly.edu.model;

import jakarta.persistence.*;

@Entity
@Table(name = "san_pham")
public class Product {

    @Id
    @Column(name = "MaSP", length = 50, nullable = false)
    private String maSP;

    @Column(name = "TenSP", columnDefinition = "NVARCHAR(MAX)", nullable = false)
    private String tenSP;

    @Column(name = "GiaSP", nullable = false)
    private int giaSP;

    @ManyToOne
    @JoinColumn(name = "MaLoaiSP", referencedColumnName = "MaLoai", nullable = false,
            foreignKey = @ForeignKey(name = "FK_LoaiSP"))
    private LoaiSP loaiSP;

    @Column(name = "HinhSP", columnDefinition = "NVARCHAR(MAX)")
    private String hinhSP;

    @Column(name = "HinhNenSP", columnDefinition = "NVARCHAR(MAX)")
    private String hinhNenSP;

    @Column(name = "ChiTietSP", columnDefinition = "NVARCHAR(MAX)")
    private String chiTietSP;

    @Column(name = "SoLuong", nullable = false)
    private int soLuong;

    // Getter & Setter
    public String getMaSP() { return maSP; }
    public void setMaSP(String maSP) { this.maSP = maSP; }

    public String getTenSP() { return tenSP; }
    public void setTenSP(String tenSP) { this.tenSP = tenSP; }

    public int getGiaSP() { return giaSP; }
    public void setGiaSP(int giaSP) { this.giaSP = giaSP; }

    public LoaiSP getLoaiSP() { return loaiSP; }
    public void setLoaiSP(LoaiSP loaiSP) { this.loaiSP = loaiSP; }

    public String getHinhSP() { return hinhSP; }
    public void setHinhSP(String hinhSP) { this.hinhSP = hinhSP; }

    public String getHinhNenSP() { return hinhNenSP; }
    public void setHinhNenSP(String hinhNenSP) { this.hinhNenSP = hinhNenSP; }

    public String getChiTietSP() { return chiTietSP; }
    public void setChiTietSP(String chiTietSP) { this.chiTietSP = chiTietSP; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
}

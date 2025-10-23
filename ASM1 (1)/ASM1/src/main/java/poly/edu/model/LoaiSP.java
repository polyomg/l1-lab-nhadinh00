package poly.edu.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "LoaiSP")
public class LoaiSP {

    @Id
    @Column(name = "MaLoai", nullable = false)
    private int maLoai;

    @Column(name = "TenLoai", columnDefinition = "NVARCHAR(255)", nullable = false)
    private String tenLoai;

    @OneToMany(mappedBy = "loaiSP", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Product> Products;

    public LoaiSP() {}

    public LoaiSP(int maLoai, String tenLoai, List<Product> products) {
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
        this.Products = products;
    }

    public int getMaLoai() { return maLoai; }
    public void setMaLoai(int maLoai) { this.maLoai = maLoai; }

    public String getTenLoai() { return tenLoai; }
    public void setTenLoai(String tenLoai) { this.tenLoai = tenLoai; }

    public List<Product> getProducts() { return Products; }
    public void setProducts(List<Product> products) { this.Products = products; }
}

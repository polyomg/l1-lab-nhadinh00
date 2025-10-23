package poly.edu.model;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Column;

public class CartId implements Serializable {

    @Column(name = "MaKH", length = 50)
    private String maKH;

    @Column(name = "MaSP", length = 50)
    private String maSP;

    // Getters & Setters
    public String getMaKH() { return maKH; }
    public void setMaKH(String maKH) { this.maKH = maKH; }

    public String getMaSP() { return maSP; }
    public void setMaSP(String maSP) { this.maSP = maSP; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartId that)) return false;
        return Objects.equals(maKH, that.maKH) &&
               Objects.equals(maSP, that.maSP);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maKH, maSP);
    }
}

package poly.edu.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable  // 🟢 bắt buộc cho Composite Key
public class HoaDonChiTietId implements Serializable {

    @Column(name = "MaHD", length = 50, nullable = false)
    private String maHD;

    @Column(name = "MaSP", length = 50, nullable = false)
    private String maSP;

    public HoaDonChiTietId() {}

    public HoaDonChiTietId(String maHD, String maSP) {
        this.maHD = maHD;
        this.maSP = maSP;
    }

    public String getMaHD() { return maHD; }
    public void setMaHD(String maHD) { this.maHD = maHD; }

    public String getMaSP() { return maSP; }
    public void setMaSP(String maSP) { this.maSP = maSP; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HoaDonChiTietId)) return false;
        HoaDonChiTietId that = (HoaDonChiTietId) o;
        return Objects.equals(maHD, that.maHD) &&
               Objects.equals(maSP, that.maSP);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maHD, maSP);
    }
}

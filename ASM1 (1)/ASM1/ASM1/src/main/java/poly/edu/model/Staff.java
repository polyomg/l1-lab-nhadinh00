package poly.edu.model;

import jakarta.persistence.*;

@Entity
@Table(name = "NhanVien")
public class Staff {

    @Id
    @Column(name = "MaNV")
    private String maNV;

    @Column(name = "TenNV")
    private String tenNV;

    @Column(name = "GioiTinh")
    private boolean gioiTinh;

    @Column(name = "NgaySinh")
    private String ngaySinh;

    @Column(name = "Email")
    private String email;

    @Column(name = "TenDN")
    private String tenDN;

    @Column(name = "MatKhau")
    private String matKhau;

    @Column(name = "CCCD")
    private String cccd;

    @Column(name = "Avatar")
    private String avatar;

    @Column(name = "Role")
    private boolean role;

    public Staff() {}

    public Staff(String maNV, String tenNV, boolean gioiTinh, String ngaySinh,
                 String email, String tenDN, String matKhau, String cccd,
                 String avatar, boolean role) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.email = email;
        this.tenDN = tenDN;
        this.matKhau = matKhau;
        this.cccd = cccd;
        this.avatar = avatar;
        this.role = role;
    }

    // Getters & Setters
    public String getMaNV() { return maNV; }
    public void setMaNV(String maNV) { this.maNV = maNV; }

    public String getTenNV() { return tenNV; }
    public void setTenNV(String tenNV) { this.tenNV = tenNV; }

    public boolean isGioiTinh() { return gioiTinh; }
    public void setGioiTinh(boolean gioiTinh) { this.gioiTinh = gioiTinh; }

    public String getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(String ngaySinh) { this.ngaySinh = ngaySinh; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTenDN() { return tenDN; }
    public void setTenDN(String tenDN) { this.tenDN = tenDN; }

    public String getMatKhau() { return matKhau; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }

    public String getCccd() { return cccd; }
    public void setCccd(String cccd) { this.cccd = cccd; }

    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }

    public boolean isRole() { return role; }
    public void setRole(boolean role) { this.role = role; }

    @Override
    public String toString() {
        return "Staff{" +
                "maNV='" + maNV + '\'' +
                ", tenNV='" + tenNV + '\'' +
                ", role=" + role +
                '}';
    }
}

package poly.edu.model;

public class ThongKeDoanhThu {
    private int maHD;
    private String ngayXuat;
    private String gioXuat;
    private String maKH;
    private int tongDoanhThu;

    public ThongKeDoanhThu() {}

    public ThongKeDoanhThu(int maHD, String ngayXuat, String gioXuat, String maKH, int tongDoanhThu) {
        this.maHD = maHD;
        this.ngayXuat = ngayXuat;
        this.gioXuat = gioXuat;
        this.maKH = maKH;
        this.tongDoanhThu = tongDoanhThu;
    }

    public int getMaHD() { return maHD; }
    public void setMaHD(int maHD) { this.maHD = maHD; }

    public String getNgayXuat() { return ngayXuat; }
    public void setNgayXuat(String ngayXuat) { this.ngayXuat = ngayXuat; }

    public String getGioXuat() { return gioXuat; }
    public void setGioXuat(String gioXuat) { this.gioXuat = gioXuat; }

    public String getMaKH() { return maKH; }
    public void setMaKH(String maKH) { this.maKH = maKH; }

    public int getTongDoanhThu() { return tongDoanhThu; }
    public void setTongDoanhThu(int tongDoanhThu) { this.tongDoanhThu = tongDoanhThu; }

    @Override
    public String toString() {
        return "ThongKeDoanhThu{" +
                "maHD='" + maHD + '\'' +
                ", ngayXuat='" + ngayXuat + '\'' +
                ", gioXuat='" + gioXuat + '\'' +
                ", maKH='" + maKH + '\'' +
                ", tongDoanhThu=" + tongDoanhThu +
                '}';
    }
}

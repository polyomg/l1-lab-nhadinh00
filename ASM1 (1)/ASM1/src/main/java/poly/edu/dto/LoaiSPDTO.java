package poly.edu.dto;

public class LoaiSPDTO {
    private int maLoai;
    private String tenLoai;

    // Constructors, getters, setters
    public LoaiSPDTO() {
    }

    public LoaiSPDTO(int maLoai, String tenLoai) {
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }
}
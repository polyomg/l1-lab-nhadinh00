package poly.edu.dto;

import poly.edu.dto.LoaiSPDTO;

public class ProductDTO {
    private String maSP;
    private String tenSP;
    private int giaSP;
    private LoaiSPDTO loaiSP;
    private String hinhSP;
    private String hinhNenSP;
    private Integer totalSold;

    // Constructors, getters, setters
    public ProductDTO() {
    }

    public ProductDTO(String maSP, String tenSP, int giaSP, LoaiSPDTO loaiSP, String hinhSP, String hinhNenSP) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.giaSP = giaSP;
        this.loaiSP = loaiSP;
        this.hinhSP = hinhSP;
        this.hinhNenSP = hinhNenSP;

    }

    public Integer getTotalSold() {
		return totalSold;
	}

	public void setTotalSold(Integer totalSold) {
		this.totalSold = totalSold;
	}

	public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getGiaSP() {
        return giaSP;
    }

    public void setGiaSP(int giaSP) {
        this.giaSP = giaSP;
    }

    public LoaiSPDTO getLoaiSP() {
        return loaiSP;
    }

    public void setLoaiSP(LoaiSPDTO loaiSP) {
        this.loaiSP = loaiSP;
    }

    public String getHinhSP() {
        return hinhSP;
    }

    public void setHinhSP(String hinhSP) {
        this.hinhSP = hinhSP;
    }

    public String getHinhNenSP() {
        return hinhNenSP;
    }

    public void setHinhNenSP(String hinhNenSP) {
        this.hinhNenSP = hinhNenSP;
    }
}
package poly.edu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import java.util.List;
import poly.edu.model.LoaiSP;
import poly.edu.repository.LoaiSPDAO;;

@Service
public class LoaiSPService {
    @Autowired
    private LoaiSPDAO loaiSPDAO; // Thay đổi kiểu dữ liệu

    public List<LoaiSP> getAllLoaiSP() {
        return loaiSPDAO.findAll();
    }

    public LoaiSP getLoaiSPById(int id) {
        return loaiSPDAO.findById(id).orElse(null);
    }

    public void saveLoaiSP(LoaiSP loaiSP) {
        try {
            loaiSPDAO.save(loaiSP);
        } catch (OptimisticLockingFailureException e) {
            System.err.println("Lỗi: Bản ghi đã bị thay đổi bởi người dùng khác.");
            throw e; // Re-throw exception để controller xử lý
        }
    }

    public void deleteLoaiSP(int id) {
        loaiSPDAO.deleteById(id);
    }
}
package poly.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import poly.edu.model.LoaiSP;
import poly.edu.service.LoaiSPService;

@Controller
@RequestMapping("/admin/loaisp")
public class LoaiSPController {
    @Autowired
    private LoaiSPService loaiSPService;

    @GetMapping
    public String listLoaiSP(Model model) {
        model.addAttribute("listLoaiSP", loaiSPService.getAllLoaiSP());
        model.addAttribute("loaiSP", new LoaiSP()); // Dùng để bind form
        return "admin/loaisp"; // Trang Thymeleaf
    }

    @PostMapping("/save")
    public String saveLoaiSP(@ModelAttribute("loaiSP") LoaiSP loaiSP) {
        loaiSPService.saveLoaiSP(loaiSP);
        return "redirect:/admin/loaisp";
    }

    @GetMapping("/edit/{id}")
    public String editLoaiSP(@PathVariable int id, Model model) {
        model.addAttribute("listLoaiSP", loaiSPService.getAllLoaiSP());
        model.addAttribute("loaiSP", loaiSPService.getLoaiSPById(id));
        return "admin/loaisp";
    }

    @GetMapping("/delete/{id}")
    public String deleteLoaiSP(@PathVariable int id) {
        loaiSPService.deleteLoaiSP(id);
        return "redirect:/admin/loaisp";
    }
}

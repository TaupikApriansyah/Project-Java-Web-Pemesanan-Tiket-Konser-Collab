package com.example.tiket_booking.controller;

import com.example.tiket_booking.model.Tiket;
import com.example.tiket_booking.service.BookingService;
import com.example.tiket_booking.service.DashboardService;
import com.example.tiket_booking.service.TiketService;
import com.example.tiket_booking.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final TiketService tiketService;
    private final UserService userService;
    private final BookingService bookingService;
    private final DashboardService dashboardService;

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    public AdminController(TiketService tiketService, UserService userService, BookingService bookingService, DashboardService dashboardService) {
        this.tiketService = tiketService;
        this.userService = userService;
        this.bookingService = bookingService;
        this.dashboardService = dashboardService;
    }

    @GetMapping({"", "/dashboard"})
    public String dashboard(Model model) {
        model.addAttribute("summary", dashboardService.summary());
        model.addAttribute("chart", dashboardService.chartData());
        model.addAttribute("bookingList", bookingService.findAll());
        return "admin/dashboard";
    }

    @GetMapping("/tiket")
    public String tiketList(Model model) {
        model.addAttribute("tiketList", tiketService.findAll());
        return "admin/tiket-list";
    }

    @GetMapping("/tiket/add")
    public String addForm(Model model) {
        model.addAttribute("tiket", new Tiket());
        return "admin/tiket-form";
    }

    @PostMapping("/tiket/save")
    public String save(@ModelAttribute Tiket tiket,
                       @RequestParam(value = "gambarFile", required = false) MultipartFile gambarFile,
                       @RequestParam(value = "gambarUrl", required = false) String gambarUrl) throws IOException {
        if (gambarFile != null && !gambarFile.isEmpty()) {
            tiket.setGambar(saveUploadedImage(gambarFile));
        } else if (gambarUrl != null && !gambarUrl.isBlank()) {
            tiket.setGambar(gambarUrl.trim());
        } else if (tiket.getId() != null) {
            Tiket existing = tiketService.findById(tiket.getId());
            tiket.setGambar(existing.getGambar());
        }
        tiketService.save(tiket);
        return "redirect:/admin/tiket";
    }

    private String saveUploadedImage(MultipartFile file) throws IOException {
        String originalName = StringUtils.cleanPath(file.getOriginalFilename() == null ? "event.jpg" : file.getOriginalFilename());
        String extension = "";
        int dotIndex = originalName.lastIndexOf('.');
        if (dotIndex >= 0) {
            extension = originalName.substring(dotIndex).toLowerCase();
        }
        if (!extension.matches("\\.(jpg|jpeg|png|webp)")) {
            extension = ".jpg";
        }
        String filename = UUID.randomUUID() + extension;
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(uploadPath);
        Files.copy(file.getInputStream(), uploadPath.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
        return "/uploads/" + filename;
    }

    @GetMapping("/tiket/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("tiket", tiketService.findById(id));
        return "admin/tiket-form";
    }

    @GetMapping("/tiket/delete/{id}")
    public String delete(@PathVariable Long id) {
        tiketService.delete(id);
        return "redirect:/admin/tiket";
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/users";
    }

    @PostMapping("/users/{id}/toggle")
    public String toggle(@PathVariable Long id) {
        userService.toggleActive(id);
        return "redirect:/admin/users";
    }

    @PostMapping("/users/{id}/role")
    public String role(@PathVariable Long id, @RequestParam String role) {
        userService.changeRole(id, role);
        return "redirect:/admin/users";
    }

    @GetMapping("/scan")
    public String scan() {
        return "admin/scan-tiket";
    }

    @PostMapping("/scan")
    public String validate(@RequestParam String kodeBooking, Model model) {
        try {
            model.addAttribute("booking", bookingService.validateKode(kodeBooking));
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "admin/scan-result";
    }
}

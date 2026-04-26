package com.example.tiket_booking.controller;

import com.example.tiket_booking.service.TiketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TiketController {
    private final TiketService tiketService;
    public TiketController(TiketService tiketService){ this.tiketService = tiketService; }

    @GetMapping({"/", "/tiket"})
    public String list(@RequestParam(required = false) String keyword, Model model) {
        model.addAttribute("tiketList", tiketService.search(keyword));
        model.addAttribute("keyword", keyword);
        return "user/tiket-list";
    }

    @GetMapping("/tiket/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("tiket", tiketService.findById(id));
        return "user/tiket-detail";
    }
}
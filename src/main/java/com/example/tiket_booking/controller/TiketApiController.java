package com.example.tiket_booking.controller;

import com.example.tiket_booking.model.Tiket;
import com.example.tiket_booking.service.TiketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tiket")
public class TiketApiController {

    private final TiketService tiketService;

    public TiketApiController(TiketService tiketService) {
        this.tiketService = tiketService;
    }

    @GetMapping
    public List<Tiket> getAll(@RequestParam(required = false) String keyword) {
        System.out.println("=== API TIKET DIPANGGIL ===");
        return tiketService.search(keyword);
    }
}
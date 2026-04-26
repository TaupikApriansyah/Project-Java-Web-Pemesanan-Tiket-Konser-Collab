package com.example.tiket_booking.controller;

import com.example.tiket_booking.model.Tiket;
import com.example.tiket_booking.service.TiketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Penting: Ini yang membuat return menjadi JSON
@RequestMapping("/api/tiket") // Path berbeda agar tidak bentrok dengan Web Controller
public class TiketApiController {

    private final TiketService tiketService;

    public TiketApiController(TiketService tiketService) {
        this.tiketService = tiketService;
    }

    @GetMapping
    public List<Tiket> getAll(@RequestParam(required = false) String keyword) {
        // Mengembalikan data mentah (List) yang otomatis jadi JSON Array []
        return tiketService.search(keyword);
    }
}
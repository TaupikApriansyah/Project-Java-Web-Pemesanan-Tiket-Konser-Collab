package com.example.tiket_booking.controller;

import com.example.tiket_booking.model.Booking;
import com.example.tiket_booking.model.User;
import com.example.tiket_booking.service.BookingService;
import com.example.tiket_booking.service.TiketService;
import com.example.tiket_booking.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookingController {
    private final BookingService bookingService;
    private final UserService userService;
    private final TiketService tiketService;

    public BookingController(BookingService bookingService, UserService userService, TiketService tiketService) {
        this.bookingService = bookingService; this.userService = userService; this.tiketService = tiketService;
    }

    @GetMapping("/booking/confirm/{tiketId}")
    public String confirm(@PathVariable Long tiketId, Model model) {
        model.addAttribute("tiket", tiketService.findById(tiketId));
        return "user/booking-confirm";
    }

    @PostMapping("/booking/create/{tiketId}")
    public String create(@PathVariable Long tiketId, @RequestParam int jumlah, @RequestParam String metodePembayaran,
                         Authentication auth, Model model) {
        try {
            User user = userService.findByEmail(auth.getName());
            Booking booking = bookingService.createBooking(user, tiketId, jumlah, metodePembayaran);
            return "redirect:/booking/success/" + booking.getId();
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("tiket", tiketService.findById(tiketId));
            return "user/booking-confirm";
        }
    }

    @GetMapping("/booking/success/{id}")
    public String success(@PathVariable Long id, Model model) {
        model.addAttribute("booking", bookingService.findById(id));
        return "user/booking-success";
    }

    @GetMapping("/riwayat")
    public String riwayat(Authentication auth, Model model) {
        User user = userService.findByEmail(auth.getName());
        model.addAttribute("bookingList", bookingService.findByUser(user));
        return "user/riwayat-booking";
    }

    @GetMapping("/booking/print/{id}")
    public String print(@PathVariable Long id, Model model) {
        model.addAttribute("booking", bookingService.findById(id));
        return "user/print-ticket";
    }
}

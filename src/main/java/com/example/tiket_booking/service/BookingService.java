package com.example.tiket_booking.service;

import com.example.tiket_booking.model.Booking;
import com.example.tiket_booking.model.Tiket;
import com.example.tiket_booking.model.User;
import com.example.tiket_booking.repository.BookingRepository;
import com.example.tiket_booking.repository.TiketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final TiketRepository tiketRepository;

    public BookingService(BookingRepository bookingRepository, TiketRepository tiketRepository) {
        this.bookingRepository = bookingRepository;
        this.tiketRepository = tiketRepository;
    }

    @Transactional
    public Booking createBooking(User user, Long tiketId, int jumlah, String metodePembayaran) {
        if (jumlah <= 0) throw new RuntimeException("Jumlah tiket harus lebih dari 0");
        Tiket tiket = tiketRepository.findById(tiketId).orElseThrow(() -> new RuntimeException("Tiket tidak ditemukan"));
        if (tiket.getStok() < jumlah) throw new RuntimeException("Stok tiket tidak mencukupi");

        tiket.setStok(tiket.getStok() - jumlah);
        tiketRepository.save(tiket);

        Booking booking = new Booking();
        booking.setKodeBooking("BK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        booking.setUser(user);
        booking.setTiket(tiket);
        booking.setJumlah(jumlah);
        booking.setTotalHarga(tiket.getHarga().multiply(BigDecimal.valueOf(jumlah)));
        booking.setMetodePembayaran(metodePembayaran);
        booking.setStatus("PAID");
        return bookingRepository.save(booking);
    }

    public List<Booking> findByUser(User user) { return bookingRepository.findByUserOrderByBookedAtDesc(user); }
    public List<Booking> findAll() { return bookingRepository.findAllByOrderByBookedAtDesc(); }
    public Booking findById(Long id) { return bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking tidak ditemukan")); }
    public Booking findByKode(String kode) { return bookingRepository.findByKodeBooking(kode).orElseThrow(() -> new RuntimeException("Kode booking tidak ditemukan")); }

    public Booking validateKode(String kode) {
        Booking booking = findByKode(kode);
        booking.setStatus("VALIDATED");
        return bookingRepository.save(booking);
    }
}

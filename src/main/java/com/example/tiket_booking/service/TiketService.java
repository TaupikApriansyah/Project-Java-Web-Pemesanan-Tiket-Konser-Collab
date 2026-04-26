package com.example.tiket_booking.service;

import com.example.tiket_booking.model.Tiket;
import com.example.tiket_booking.repository.BookingRepository;
import com.example.tiket_booking.repository.TiketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class TiketService {
    private final TiketRepository tiketRepository;
    private final BookingRepository bookingRepository;

    public TiketService(TiketRepository tiketRepository, BookingRepository bookingRepository){
        this.tiketRepository = tiketRepository;
        this.bookingRepository = bookingRepository;
    }

    public List<Tiket> findAll(){ return tiketRepository.findAll(); }
    public Tiket findById(Long id){ return tiketRepository.findById(id).orElseThrow(() -> new RuntimeException("Tiket tidak ditemukan")); }
    public Tiket save(Tiket tiket){ return tiketRepository.save(tiket); }

    @Transactional
    public void delete(Long id){
        bookingRepository.deleteByTiketId(id);
        tiketRepository.deleteById(id);
    }

    public List<Tiket> search(String keyword) {
        if (keyword == null || keyword.isBlank()) return findAll();
        return tiketRepository.findByKategoriContainingIgnoreCaseOrNamaContainingIgnoreCaseOrLokasiContainingIgnoreCase(keyword, keyword, keyword);
    }
}

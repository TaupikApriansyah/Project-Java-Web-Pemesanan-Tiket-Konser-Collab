package com.example.tiket_booking.repository;

import com.example.tiket_booking.model.Tiket;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TiketRepository extends JpaRepository<Tiket, Long> {
    List<Tiket> findByKategoriContainingIgnoreCaseOrNamaContainingIgnoreCaseOrLokasiContainingIgnoreCase(String kategori, String nama, String lokasi);
}

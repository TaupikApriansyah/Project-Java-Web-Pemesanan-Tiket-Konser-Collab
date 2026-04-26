package com.example.tiket_booking.config;

import com.example.tiket_booking.model.Tiket;
import com.example.tiket_booking.model.User;
import com.example.tiket_booking.repository.TiketRepository;
import com.example.tiket_booking.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class DataSeeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final TiketRepository tiketRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository, TiketRepository tiketRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tiketRepository = tiketRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (!userRepository.existsByEmail("admin@tiket.com")) {
            User admin = new User();
            admin.setNama("Administrator");
            admin.setEmail("admin@tiket.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");
            userRepository.save(admin);
        }
        if (!userRepository.existsByEmail("user@tiket.com")) {
            User user = new User();
            user.setNama("User Demo");
            user.setEmail("user@tiket.com");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setRole("USER");
            userRepository.save(user);
        }
        if (tiketRepository.count() == 0) {
            add("ECLIPSE: Resonance World Tour", "Konser", LocalDate.now().plusDays(14), "ICE BSD City, Tangerang", 2450000, 100,
                    "Konser musik besar dengan tata panggung modern, lighting megah, dan pengalaman live performance premium.",
                    "https://images.unsplash.com/photo-1540039155733-5bb30b53aa14?auto=format&fit=crop&q=80&w=1200");
            add("Neon Beats Jakarta Festival", "Festival", LocalDate.now().plusDays(28), "Carnaval Beach Ancol, Jakarta", 850000, 80,
                    "Festival musik malam dengan konsep neon, line-up DJ nasional dan internasional, serta area kuliner.",
                    "https://images.unsplash.com/photo-1470225620780-dba8ba36b745?auto=format&fit=crop&q=80&w=1200");
            add("Rockfest Indonesia", "Rock", LocalDate.now().plusDays(35), "Stadion Madya, Jakarta", 450000, 150,
                    "Festival band rock dengan banyak penampil, area standing, dan pengalaman panggung outdoor.",
                    "https://images.unsplash.com/photo-1501281668745-f7f57925c3b4?auto=format&fit=crop&q=80&w=1200");
        }
    }

    private void add(String nama, String kategori, LocalDate tanggal, String lokasi, int harga, int stok, String deskripsi, String gambar) {
        Tiket tiket = new Tiket();
        tiket.setNama(nama);
        tiket.setKategori(kategori);
        tiket.setTanggal(tanggal);
        tiket.setLokasi(lokasi);
        tiket.setHarga(BigDecimal.valueOf(harga));
        tiket.setStok(stok);
        tiket.setDeskripsi(deskripsi);
        tiket.setGambar(gambar);
        tiketRepository.save(tiket);
    }
}

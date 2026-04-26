package com.example.tiket_booking.repository;

import com.example.tiket_booking.model.Booking;
import com.example.tiket_booking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserOrderByBookedAtDesc(User user);
    List<Booking> findAllByOrderByBookedAtDesc();
    Optional<Booking> findByKodeBooking(String kodeBooking);
    long countByStatus(String status);

    @Modifying
    @Query("delete from Booking b where b.tiket.id = :tiketId")
    void deleteByTiketId(Long tiketId);

    @Query("select coalesce(sum(b.totalHarga), 0) from Booking b where b.status <> 'CANCELLED'")
    BigDecimal sumPendapatan();

    @Query("select b.status, count(b) from Booking b group by b.status order by b.status")
    List<Object[]> countBookingByStatus();

    @Query("select function('date', b.bookedAt), count(b), coalesce(sum(b.totalHarga), 0) " +
           "from Booking b group by function('date', b.bookedAt) order by function('date', b.bookedAt)")
    List<Object[]> dailyBookingStats();

    @Query("select b.tiket.nama, coalesce(sum(b.jumlah), 0), coalesce(sum(b.totalHarga), 0) " +
           "from Booking b group by b.tiket.id, b.tiket.nama order by coalesce(sum(b.jumlah), 0) desc")
    List<Object[]> topEventStats();
}

package com.example.tiket_booking.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class Booking {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String kodeBooking;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tiket_id")
    private Tiket tiket;

    private int jumlah;
    private BigDecimal totalHarga;

    @Column(length = 20)
    private String status = "PENDING";

    @Column(length = 40)
    private String metodePembayaran;

    private LocalDateTime bookedAt = LocalDateTime.now();

    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getKodeBooking(){return kodeBooking;} public void setKodeBooking(String kodeBooking){this.kodeBooking=kodeBooking;}
    public User getUser(){return user;} public void setUser(User user){this.user=user;}
    public Tiket getTiket(){return tiket;} public void setTiket(Tiket tiket){this.tiket=tiket;}
    public int getJumlah(){return jumlah;} public void setJumlah(int jumlah){this.jumlah=jumlah;}
    public BigDecimal getTotalHarga(){return totalHarga;} public void setTotalHarga(BigDecimal totalHarga){this.totalHarga=totalHarga;}
    public String getStatus(){return status;} public void setStatus(String status){this.status=status;}
    public String getMetodePembayaran(){return metodePembayaran;} public void setMetodePembayaran(String metodePembayaran){this.metodePembayaran=metodePembayaran;}
    public LocalDateTime getBookedAt(){return bookedAt;} public void setBookedAt(LocalDateTime bookedAt){this.bookedAt=bookedAt;}
}

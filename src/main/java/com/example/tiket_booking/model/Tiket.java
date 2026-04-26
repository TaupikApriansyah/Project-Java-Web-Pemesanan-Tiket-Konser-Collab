package com.example.tiket_booking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tiket")
public class Tiket {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @Size(max = 100)
    private String nama;

    @NotBlank @Size(max = 50)
    private String kategori;

    @NotNull
    private LocalDate tanggal;

    @NotNull @DecimalMin("0.0")
    private BigDecimal harga;

    @Min(0)
    private int stok;

    @Size(max = 200)
    private String lokasi;

    @Column(columnDefinition = "TEXT")
    private String deskripsi;

    private String gambar;
    private LocalDateTime createdAt = LocalDateTime.now();

    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getNama(){return nama;} public void setNama(String nama){this.nama=nama;}
    public String getKategori(){return kategori;} public void setKategori(String kategori){this.kategori=kategori;}
    public LocalDate getTanggal(){return tanggal;} public void setTanggal(LocalDate tanggal){this.tanggal=tanggal;}
    public BigDecimal getHarga(){return harga;} public void setHarga(BigDecimal harga){this.harga=harga;}
    public int getStok(){return stok;} public void setStok(int stok){this.stok=stok;}
    public String getLokasi(){return lokasi;} public void setLokasi(String lokasi){this.lokasi=lokasi;}
    public String getDeskripsi(){return deskripsi;} public void setDeskripsi(String deskripsi){this.deskripsi=deskripsi;}
    public String getGambar(){return gambar;} public void setGambar(String gambar){this.gambar=gambar;}
    public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime createdAt){this.createdAt=createdAt;}
}

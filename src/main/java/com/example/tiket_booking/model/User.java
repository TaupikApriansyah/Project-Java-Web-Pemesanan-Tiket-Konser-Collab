package com.example.tiket_booking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @Column(nullable = false, length = 100)
    private String nama;

    @Email @NotBlank @Column(nullable = false, unique = true, length = 120)
    private String email;

    @NotBlank @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 20)
    private String role = "USER";

    @Column(nullable = false)
    private boolean active = true;

    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "user")
    private List<Booking> bookings = new ArrayList<>();

    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getNama(){return nama;} public void setNama(String nama){this.nama=nama;}
    public String getEmail(){return email;} public void setEmail(String email){this.email=email;}
    public String getPassword(){return password;} public void setPassword(String password){this.password=password;}
    public String getRole(){return role;} public void setRole(String role){this.role=role;}
    public boolean isActive(){return active;} public void setActive(boolean active){this.active=active;}
    public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime createdAt){this.createdAt=createdAt;}
}

package com.example.tiket_booking.service;

import com.example.tiket_booking.model.User;
import com.example.tiket_booking.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) throw new RuntimeException("Email sudah digunakan");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User tidak ditemukan"));
    }

    public List<User> findAll(){ return userRepository.findAll(); }

    public void updateProfile(String email, String nama, String newPassword) {
        User user = findByEmail(email);
        user.setNama(nama);
        if (newPassword != null && !newPassword.isBlank()) user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public void toggleActive(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User tidak ditemukan"));
        user.setActive(!user.isActive());
        userRepository.save(user);
    }

    public void changeRole(Long id, String role) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User tidak ditemukan"));
        user.setRole(role);
        userRepository.save(user);
    }
}

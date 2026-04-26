package com.example.tiket_booking.service;

import com.example.tiket_booking.repository.BookingRepository;
import com.example.tiket_booking.repository.TiketRepository;
import com.example.tiket_booking.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {
    private final UserRepository userRepository;
    private final TiketRepository tiketRepository;
    private final BookingRepository bookingRepository;

    public DashboardService(UserRepository userRepository, TiketRepository tiketRepository, BookingRepository bookingRepository) {
        this.userRepository = userRepository;
        this.tiketRepository = tiketRepository;
        this.bookingRepository = bookingRepository;
    }

    public Map<String, Object> summary() {
        Map<String, Object> data = new HashMap<>();
        data.put("totalUser", userRepository.count());
        data.put("totalTiket", tiketRepository.count());
        data.put("totalBooking", bookingRepository.count());
        BigDecimal pendapatan = bookingRepository.sumPendapatan();
        data.put("pendapatan", pendapatan);
        data.put("paid", bookingRepository.countByStatus("PAID"));
        data.put("validated", bookingRepository.countByStatus("VALIDATED"));
        data.put("pending", bookingRepository.countByStatus("PENDING"));
        return data;
    }

    public Map<String, Object> chartData() {
        Map<String, Object> data = new LinkedHashMap<>();

        List<String> dailyLabels = new ArrayList<>();
        List<Long> dailyCounts = new ArrayList<>();
        List<BigDecimal> dailyRevenue = new ArrayList<>();
        for (Object[] row : bookingRepository.dailyBookingStats()) {
            dailyLabels.add(String.valueOf(row[0]));
            dailyCounts.add(((Number) row[1]).longValue());
            dailyRevenue.add((BigDecimal) row[2]);
        }

        List<String> statusLabels = new ArrayList<>();
        List<Long> statusCounts = new ArrayList<>();
        for (Object[] row : bookingRepository.countBookingByStatus()) {
            statusLabels.add(String.valueOf(row[0]));
            statusCounts.add(((Number) row[1]).longValue());
        }

        List<String> eventLabels = new ArrayList<>();
        List<Long> eventTickets = new ArrayList<>();
        List<BigDecimal> eventRevenue = new ArrayList<>();
        for (Object[] row : bookingRepository.topEventStats()) {
            eventLabels.add(String.valueOf(row[0]));
            eventTickets.add(((Number) row[1]).longValue());
            eventRevenue.add((BigDecimal) row[2]);
        }

        data.put("dailyLabels", dailyLabels);
        data.put("dailyCounts", dailyCounts);
        data.put("dailyRevenue", dailyRevenue);
        data.put("statusLabels", statusLabels);
        data.put("statusCounts", statusCounts);
        data.put("eventLabels", eventLabels);
        data.put("eventTickets", eventTickets);
        data.put("eventRevenue", eventRevenue);
        return data;
    }
}

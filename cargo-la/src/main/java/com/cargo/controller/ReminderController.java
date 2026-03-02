package com.cargola.controller;

import com.cargola.entity.Rental;
import com.cargola.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/reminders")
public class ReminderController {
    
    @Autowired
    private RentalRepository rentalRepository;
    
    // Get rentals expiring soon (for admin to view)
    @GetMapping("/upcoming")
    public List<Rental> getUpcomingRentals(@RequestParam(defaultValue = "7") int days) {
        LocalDate today = LocalDate.now();
        LocalDate targetDate = today.plusDays(days);
        return rentalRepository.findByEndDateBetween(today, targetDate);
    }
    
    // Check and return reminder info (can be called by cron)
    @GetMapping("/check")
    public String checkReminders() {
        LocalDate today = LocalDate.now();
        LocalDate in3Days = today.plusDays(3);
        LocalDate in1Day = today.plusDays(1);
        
        List<Rental> expiringIn3Days = rentalRepository.findByEndDate(in3Days);
        List<Rental> expiringIn1Day = rentalRepository.findByEndDate(in1Day);
        
        StringBuilder sb = new StringBuilder();
        sb.append("Reminders:\n");
        sb.append("Expiring in 3 days: ").append(expiringIn3Days.size()).append(" rentals\n");
        sb.append("Expiring in 1 day: ").append(expiringIn1Day.size()).append(" rentals\n");
        
        return sb.toString();
    }
}

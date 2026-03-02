package com.cargola.repository;

import com.cargola.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Integer> {
    List<Rental> findByEndDate(LocalDate endDate);
    List<Rental> findByEndDateBetween(LocalDate start, LocalDate end);
    List<Rental> findByStatusAndEndDate(String status, LocalDate endDate);
}

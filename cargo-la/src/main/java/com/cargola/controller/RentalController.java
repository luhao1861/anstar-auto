package com.cargola.controller;

import com.cargola.entity.Rental;
import com.cargola.repository.RentalRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rentals")
@Tag(name = "租赁管理", description = "租赁记录相关接口")
public class RentalController {

    @Autowired
    private RentalRepository rentalRepository;

    @GetMapping
    @Operation(summary = "获取所有租赁记录")
    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取租赁记录")
    public Rental getRentalById(@PathVariable Integer id) {
        return rentalRepository.findById(id).orElse(null);
    }

    @PostMapping
    @Operation(summary = "创建租赁记录")
    @Transactional
    public Rental addRental(@RequestBody Rental rental) {
        return rentalRepository.save(rental);
    }

    @PutMapping
    @Operation(summary = "更新租赁记录")
    @Transactional
    public Rental updateRental(@RequestBody Rental rental) {
        return rentalRepository.save(rental);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除租赁记录")
    public String deleteRental(@PathVariable Integer id) {
        rentalRepository.deleteById(id);
        return "租赁记录删除成功";
    }

    @GetMapping("/vehicle/{plateNumber}")
    @Operation(summary = "获取某车辆的租赁记录")
    public List<Rental> getRentalsByVehicle(@PathVariable String plateNumber) {
        return rentalRepository.findAll().stream()
            .filter(r -> plateNumber.equals(r.getPlateNumber()))
            .toList();
    }

    @GetMapping("/customer/{customerId}")
    @Operation(summary = "获取某客户的租赁记录")
    public List<Rental> getRentalsByCustomer(@PathVariable Integer customerId) {
        return rentalRepository.findAll().stream()
            .filter(r -> customerId.equals(r.getCustomerId()))
            .toList();
    }

    @GetMapping("/active")
    @Operation(summary = "获取当前进行中的租赁")
    public List<Rental> getActiveRentals() {
        return rentalRepository.findAll().stream()
            .filter(r -> "IN_PROGRESS".equals(r.getStatus()))
            .toList();
    }
}

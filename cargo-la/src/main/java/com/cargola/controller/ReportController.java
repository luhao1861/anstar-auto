package com.cargola.controller;

import com.cargola.entity.Rental;
import com.cargola.entity.Vehicle;
import com.cargola.repository.RentalRepository;
import com.cargola.repository.VehicleRepository;
import com.cargola.repository.CustomerRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reports")
@Tag(name = "报表统计", description = "数据统计报表接口")
public class ReportController {

    @Autowired
    private RentalRepository rentalRepository;
    
    @Autowired
    private VehicleRepository vehicleRepository;
    
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/revenue")
    @Operation(summary = "营收报表")
    public Map<String, Object> getRevenueReport(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        
        List<Rental> rentals = rentalRepository.findAll();
        
        // 按状态过滤
        if (startDate != null && endDate != null) {
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);
            rentals = rentals.stream()
                .filter(r -> r.getStartDate() != null)
                .filter(r -> !r.getStartDate().isBefore(start) && !r.getStartDate().isAfter(end))
                .collect(Collectors.toList());
        }
        
        // 计算总收入
        BigDecimal totalRevenue = rentals.stream()
            .filter(r -> "COMPLETED".equals(r.getStatus()))
            .map(Rental::getRentPrice)
            .filter(Objects::nonNull)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        // 按月统计
        Map<String, BigDecimal> monthlyRevenue = new HashMap<>();
        rentals.stream()
            .filter(r -> "COMPLETED".equals(r.getStatus()) && r.getStartDate() != null)
            .forEach(r -> {
                String month = r.getStartDate().toString().substring(0, 7);
                monthlyRevenue.put(month, monthlyRevenue.getOrDefault(month, BigDecimal.ZERO).add(r.getRentPrice()));
            });
        
        // 按支付方式统计
        Map<String, BigDecimal> paymentRevenue = new HashMap<>();
        rentals.stream()
            .filter(r -> "COMPLETED".equals(r.getStatus()) && r.getPaymentMethod() != null)
            .forEach(r -> {
                paymentRevenue.put(r.getPaymentMethod(), 
                    paymentRevenue.getOrDefault(r.getPaymentMethod(), BigDecimal.ZERO).add(r.getRentPrice()));
            });
        
        Map<String, Object> result = new HashMap<>();
        result.put("totalRevenue", totalRevenue);
        result.put("totalOrders", rentals.size());
        result.put("completedOrders", rentals.stream().filter(r -> "COMPLETED".equals(r.getStatus())).count());
        result.put("monthlyRevenue", monthlyRevenue);
        result.put("paymentRevenue", paymentRevenue);
        
        return result;
    }

    @GetMapping("/vehicle-utilization")
    @Operation(summary = "车辆利用率")
    public Map<String, Object> getVehicleUtilization() {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        List<Rental> rentals = rentalRepository.findAll();
        
        Map<String, Long> vehicleRentCount = rentals.stream()
            .filter(r -> "COMPLETED".equals(r.getStatus()))
            .collect(Collectors.groupingBy(Rental::getPlateNumber, Collectors.counting()));
        
        List<Map<String, Object>> vehicleStats = new ArrayList<>();
        for (Vehicle v : vehicles) {
            Map<String, Object> stat = new HashMap<>();
            stat.put("plateNumber", v.getPlateNumber());
            stat.put("model", v.getModel());
            stat.put("brand", v.getBrand());
            stat.put("status", v.getStatus());
            stat.put("rentCount", vehicleRentCount.getOrDefault(v.getPlateNumber(), 0L));
            vehicleStats.add(stat);
        }
        
        // 按租车次数排序
        vehicleStats.sort((a, b) -> Long.compare((Long) b.get("rentCount"), (Long) a.get("rentCount")));
        
        Map<String, Object> result = new HashMap<>();
        result.put("totalVehicles", vehicles.size());
        result.put("availableVehicles", vehicles.stream().filter(v -> "AVAILABLE".equals(v.getStatus())).count());
        result.put("rentedVehicles", vehicles.stream().filter(v -> "RENTED".equals(v.getStatus())).count());
        result.put("maintenanceVehicles", vehicles.stream().filter(v -> "MAINTENANCE".equals(v.getStatus())).count());
        result.put("vehicleStats", vehicleStats);
        
        return result;
    }

    @GetMapping("/popular-cars")
    @Operation(summary = "热门车型")
    public Map<String, Object> getPopularCars() {
        List<Rental> rentals = rentalRepository.findAll();
        
        // 按车型统计
        Map<String, Long> modelCount = new HashMap<>();
        Map<String, BigDecimal> modelRevenue = new HashMap<>();
        
        for (Rental r : rentals) {
            if (!"COMPLETED".equals(r.getStatus())) continue;
            
            Optional<Vehicle> vehicle = vehicleRepository.findById(r.getPlateNumber());
            if (vehicle.isPresent()) {
                String model = vehicle.get().getModel();
                modelCount.put(model, modelCount.getOrDefault(model, 0L) + 1);
                modelRevenue.put(model, modelRevenue.getOrDefault(model, BigDecimal.ZERO).add(r.getRentPrice()));
            }
        }
        
        List<Map<String, Object>> popularCars = new ArrayList<>();
        for (String model : modelCount.keySet()) {
            Map<String, Object> car = new HashMap<>();
            car.put("model", model);
            car.put("rentCount", modelCount.get(model));
            car.put("totalRevenue", modelRevenue.get(model));
            popularCars.add(car);
        }
        
        popularCars.sort((a, b) -> Long.compare((Long) b.get("rentCount"), (Long) a.get("rentCount")));
        
        Map<String, Object> result = new HashMap<>();
        result.put("popularCars", popularCars);
        
        return result;
    }

    @GetMapping("/customer-analysis")
    @Operation(summary = "客户分析")
    public Map<String, Object> getCustomerAnalysis() {
        List<Rental> rentals = rentalRepository.findAll();
        List<Map<String, Object>> customerStats = new ArrayList<>();
        
        // 按客户统计
        Map<Integer, List<Rental>> customerRentals = rentals.stream()
            .collect(Collectors.groupingBy(Rental::getCustomerId));
        
        for (Integer customerId : customerRentals.keySet()) {
            List<Rental> customerRents = customerRentals.get(customerId);
            BigDecimal totalSpent = customerRents.stream()
                .filter(r -> r.getRentPrice() != null)
                .map(Rental::getRentPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            
            Map<String, Object> stat = new HashMap<>();
            stat.put("customerId", customerId);
            stat.put("totalRentals", customerRents.size());
            stat.put("totalSpent", totalSpent);
            stat.put("completedRentals", customerRents.stream().filter(r -> "COMPLETED".equals(r.getStatus())).count());
            customerStats.add(stat);
        }
        
        // 按消费金额排序
        customerStats.sort((a, b) -> ((BigDecimal) b.get("totalSpent")).compareTo((BigDecimal) a.get("totalSpent")));
        
        Map<String, Object> result = new HashMap<>();
        result.put("totalCustomers", customerRentals.size());
        result.put("customerStats", customerStats);
        
        return result;
    }

    @GetMapping("/maintenance-cost")
    @Operation(summary = "维修成本分析")
    public Map<String, Object> getMaintenanceCost() {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        
        Map<String, Object> result = new HashMap<>();
        result.put("totalVehicles", vehicles.size());
        result.put("maintenanceVehicles", vehicles.stream().filter(v -> "MAINTENANCE".equals(v.getStatus())).count());
        result.put("status", "维修成本报表功能开发中");
        
        return result;
    }

    @GetMapping("/dashboard")
    @Operation(summary = "仪表盘概览")
    public Map<String, Object> getDashboard() {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        List<Rental> rentals = rentalRepository.findAll();
        
        BigDecimal totalRevenue = rentals.stream()
            .filter(r -> "COMPLETED".equals(r.getStatus()))
            .map(Rental::getRentPrice)
            .filter(Objects::nonNull)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        long activeRentals = rentals.stream()
            .filter(r -> "IN_PROGRESS".equals(r.getStatus()))
            .count();
        
        Map<String, Object> dashboard = new HashMap<>();
        dashboard.put("totalVehicles", vehicles.size());
        dashboard.put("availableVehicles", vehicles.stream().filter(v -> "AVAILABLE".equals(v.getStatus())).count());
        dashboard.put("totalCustomers", customerRepository.count());
        dashboard.put("totalRentals", rentals.size());
        dashboard.put("activeRentals", activeRentals);
        dashboard.put("totalRevenue", totalRevenue);
        dashboard.put("completedRentals", rentals.stream().filter(r -> "COMPLETED".equals(r.getStatus())).count());
        
        return dashboard;
    }
}

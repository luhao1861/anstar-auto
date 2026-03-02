package com.cargola.controller;

import com.cargola.entity.Vehicle;
import com.cargola.repository.VehicleRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@Tag(name = "车辆管理", description = "车辆相关接口")
public class VehicleController {

    @Autowired
    private VehicleRepository vehicleRepository;

    @GetMapping
    @Operation(summary = "获取所有车辆")
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    @GetMapping("/{plateNumber}")
    @Operation(summary = "根据车牌号获取车辆")
    public Vehicle getVehicleByPlateNumber(@PathVariable String plateNumber) {
        return vehicleRepository.findById(plateNumber).orElse(null);
    }

    @PostMapping
    @Operation(summary = "添加车辆")
    @Transactional
    public Vehicle addVehicle(@RequestBody Vehicle vehicle) {
        if (vehicleRepository.existsById(vehicle.getPlateNumber())) {
            throw new RuntimeException("车辆已存在: " + vehicle.getPlateNumber());
        }
        return vehicleRepository.save(vehicle);
    }

    @PutMapping
    @Operation(summary = "更新车辆")
    @Transactional
    public Vehicle updateVehicle(@RequestBody Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    @DeleteMapping("/{plateNumber}")
    @Operation(summary = "删除车辆")
    public String deleteVehicle(@PathVariable String plateNumber) {
        vehicleRepository.deleteById(plateNumber);
        return "车辆删除成功";
    }

    @GetMapping("/available")
    @Operation(summary = "获取可用车辆")
    public List<Vehicle> getAvailableVehicles() {
        return vehicleRepository.findByStatus("AVAILABLE");
    }
}

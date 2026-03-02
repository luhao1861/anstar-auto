package com.cargola.controller;

import com.cargola.entity.Maintenance;
import com.cargola.repository.MaintenanceRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maintenance")
@Tag(name = "维修保养", description = "维修保养相关接口")
public class MaintenanceController {

    @Autowired
    private MaintenanceRepository maintenanceRepository;

    @GetMapping
    @Operation(summary = "获取所有维修保养记录")
    public List<Maintenance> getAllMaintenance() {
        return maintenanceRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取维修记录")
    public Maintenance getMaintenanceById(@PathVariable Integer id) {
        return maintenanceRepository.findById(id).orElse(null);
    }

    @PostMapping
    @Operation(summary = "添加维修记录")
    @Transactional
    public Maintenance addMaintenance(@RequestBody Maintenance maintenance) {
        return maintenanceRepository.save(maintenance);
    }

    @PutMapping
    @Operation(summary = "更新维修记录")
    @Transactional
    public Maintenance updateMaintenance(@RequestBody Maintenance maintenance) {
        return maintenanceRepository.save(maintenance);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除维修记录")
    public String deleteMaintenance(@PathVariable Integer id) {
        maintenanceRepository.deleteById(id);
        return "维修记录删除成功";
    }

    @GetMapping("/vehicle/{plateNumber}")
    @Operation(summary = "获取某车辆的维修记录")
    public List<Maintenance> getMaintenanceByVehicle(@PathVariable String plateNumber) {
        return maintenanceRepository.findAll().stream()
            .filter(m -> plateNumber.equals(m.getPlateNumber()))
            .toList();
    }
}

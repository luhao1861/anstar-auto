package com.cargola.controller;

import com.cargola.entity.Store;
import com.cargola.repository.StoreRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
@Tag(name = "门店管理", description = "门店相关接口")
public class StoreController {

    @Autowired
    private StoreRepository storeRepository;

    @GetMapping
    @Operation(summary = "获取所有门店")
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取门店")
    public Store getStoreById(@PathVariable Integer id) {
        return storeRepository.findById(id).orElse(null);
    }

    @PostMapping
    @Operation(summary = "添加门店")
    @Transactional
    public Store addStore(@RequestBody Store store) {
        return storeRepository.save(store);
    }

    @PutMapping
    @Operation(summary = "更新门店")
    @Transactional
    public Store updateStore(@RequestBody Store store) {
        return storeRepository.save(store);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除门店")
    public String deleteStore(@PathVariable Integer id) {
        storeRepository.deleteById(id);
        return "门店删除成功";
    }
}

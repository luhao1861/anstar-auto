package com.cargola.controller;

import com.cargola.entity.Customer;
import com.cargola.repository.CustomerRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@Tag(name = "客户管理", description = "客户相关接口")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    @Operation(summary = "获取所有客户")
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取客户")
    public Customer getCustomerById(@PathVariable Integer id) {
        return customerRepository.findById(id).orElse(null);
    }

    @PostMapping
    @Operation(summary = "添加客户")
    @Transactional
    public Customer addCustomer(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    @PutMapping
    @Operation(summary = "更新客户")
    @Transactional
    public Customer updateCustomer(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除客户")
    public String deleteCustomer(@PathVariable Integer id) {
        customerRepository.deleteById(id);
        return "客户删除成功";
    }

    @GetMapping("/search")
    @Operation(summary = "搜索客户（按姓名/电话/微信）")
    public List<Customer> searchCustomers(@RequestParam String keyword) {
        return customerRepository.findAll().stream()
            .filter(c -> (c.getName() != null && c.getName().contains(keyword)) ||
                        (c.getPhone() != null && c.getPhone().contains(keyword)) ||
                        (c.getWechatId() != null && c.getWechatId().contains(keyword)))
            .toList();
    }
}

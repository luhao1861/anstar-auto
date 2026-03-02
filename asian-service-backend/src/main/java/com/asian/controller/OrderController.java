package com.asian.controller;

import com.asian.entity.Order;
import com.asian.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        // Generate order number
        String orderNumber = "AS" + System.currentTimeMillis();
        order.setOrderNumber(orderNumber);
        order.setStatus("SUBMITTED");
        order.setPaymentStatus("UNPAID");
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        return orderRepository.save(order);
    }
    
    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public Optional<Order> getOrder(@PathVariable Long id) {
        return orderRepository.findById(id);
    }
    
    @GetMapping("/user/{userId}")
    public List<Order> getUserOrders(@PathVariable Long userId) {
        return orderRepository.findByUserId(userId);
    }
    
    @GetMapping("/number/{orderNumber}")
    public Optional<Order> getOrderByNumber(@PathVariable String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber);
    }
    
    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable Long id, @RequestBody Order order) {
        order.setId(id);
        order.setUpdatedAt(LocalDateTime.now());
        return orderRepository.save(order);
    }
    
    @PutMapping("/{id}/quote")
    public Order quoteOrder(@PathVariable Long id, @RequestBody Map<String, Object> data) {
        Optional<Order> opt = orderRepository.findById(id);
        if (opt.isPresent()) {
            Order order = opt.get();
            Integer price = (Integer) data.get("price");
            order.setQuotedPrice(price);
            
            // Auto-add mail fee if mail delivery
            if ("MAIL".equals(order.getDeliveryMethod())) {
                order.setMailFee(30);
            } else {
                order.setMailFee(0);
            }
            
            // Override mail fee if explicitly set
            if (data.get("mailFee") != null) {
                order.setMailFee((Integer) data.get("mailFee"));
            }
            
            order.setStatus("QUOTED");
            order.setUpdatedAt(LocalDateTime.now());
            return orderRepository.save(order);
        }
        return null;
    }
    
    @PutMapping("/{id}/pay")
    public Order markPaid(@PathVariable Long id, @RequestBody Map<String, Object> data) {
        Optional<Order> opt = orderRepository.findById(id);
        if (opt.isPresent()) {
            Order order = opt.get();
            order.setPaymentStatus("PAID");
            order.setPaymentMethod((String) data.get("method"));
            if (data.get("proof") != null) {
                order.setPaymentProof((String) data.get("proof"));
            }
            order.setStatus("PAID");
            order.setUpdatedAt(LocalDateTime.now());
            return orderRepository.save(order);
        }
        return null;
    }
    
    @PutMapping("/{id}/complete")
    public Order completeOrder(@PathVariable Long id) {
        Optional<Order> opt = orderRepository.findById(id);
        if (opt.isPresent()) {
            Order order = opt.get();
            order.setStatus("COMPLETED");
            order.setUpdatedAt(LocalDateTime.now());
            return orderRepository.save(order);
        }
        return null;
    }
    
    @PutMapping("/{id}/mail")
    public Order mailOrder(@PathVariable Long id, @RequestBody Map<String, Object> data) {
        Optional<Order> opt = orderRepository.findById(id);
        if (opt.isPresent()) {
            Order order = opt.get();
            order.setStatus("MAILED");
            order.setTrackingNumber((String) data.get("tracking"));
            order.setUpdatedAt(LocalDateTime.now());
            return orderRepository.save(order);
        }
        return null;
    }
    
    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderRepository.deleteById(id);
    }
}

package com.asian.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String orderNumber; // 订单号
    
    private Long userId;
    private String serviceType; // NOTARY, TRANSLATION, APOSTILLE, IMMIGRATION, INSURANCE
    
    private String status; // SUBMITTED, QUOTED, PAID, PROCESSING, COMPLETED, MAILED, CANCELLED
    
    private String description;
    private Integer quotedPrice;
    private Integer mailFee; // 邮寄费，0=电子版
    private Integer deposit; // 定金
    
    private String paymentMethod; // STRIPE, ZELLE, CASH
    private String paymentStatus; // UNPAID, PAID, REFUNDED
    private String paymentProof; // 支付截图
    
    private String deliveryMethod; // ELECTRONIC, MAIL
    private String recipientName;
    private String recipientAddress;
    private String recipientPhone;
    private String trackingNumber;
    
    // Customer contact (for guest orders)
    private String customerName;
    private String customerPhone;
    private String customerEmail;
    
    // File descriptions
    private String fileDescriptions;
    
    private String adminNotes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getOrderNumber() { return orderNumber; }
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getServiceType() { return serviceType; }
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getQuotedPrice() { return quotedPrice; }
    public void setQuotedPrice(Integer quotedPrice) { this.quotedPrice = quotedPrice; }
    public Integer getMailFee() { return mailFee; }
    public void setMailFee(Integer mailFee) { this.mailFee = mailFee; }
    public Integer getDeposit() { return deposit; }
    public void setDeposit(Integer deposit) { this.deposit = deposit; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
    public String getPaymentProof() { return paymentProof; }
    public void setPaymentProof(String paymentProof) { this.paymentProof = paymentProof; }
    public String getDeliveryMethod() { return deliveryMethod; }
    public void setDeliveryMethod(String deliveryMethod) { this.deliveryMethod = deliveryMethod; }
    public String getRecipientName() { return recipientName; }
    public void setRecipientName(String recipientName) { this.recipientName = recipientName; }
    public String getRecipientAddress() { return recipientAddress; }
    public void setRecipientAddress(String recipientAddress) { this.recipientAddress = recipientAddress; }
    public String getRecipientPhone() { return recipientPhone; }
    public void setRecipientPhone(String recipientPhone) { this.recipientPhone = recipientPhone; }
    public String getTrackingNumber() { return trackingNumber; }
    public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getCustomerPhone() { return customerPhone; }
    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }
    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }
    public String getFileDescriptions() { return fileDescriptions; }
    public void setFileDescriptions(String fileDescriptions) { this.fileDescriptions = fileDescriptions; }
    public String getAdminNotes() { return adminNotes; }
    public void setAdminNotes(String adminNotes) { this.adminNotes = adminNotes; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}

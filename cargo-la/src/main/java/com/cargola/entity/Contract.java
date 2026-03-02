package com.cargola.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "contracts")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer contractId;
    
    private Integer rentalId;
    private String plateNumber;
    private Integer customerId;
    
    // 客户信息
    @Column(name = "customer_name")
    private String customerName;
    
    @Column(name = "customer_id_number")
    private String customerIdNumber;
    
    @Column(name = "customer_phone")
    private String customerPhone;
    
    @Column(name = "customer_wechat")
    private String customerWechat;
    
    @Column(name = "customer_email")
    private String customerEmail;
    
    @Column(name = "customer_address")
    private String customerAddress;
    
    // 车辆信息
    @Column(name = "vehicle_info")
    private String vehicleInfo;
    
    // 租赁信息
    @Column(name = "start_date")
    private LocalDate startDate;
    
    @Column(name = "end_date")
    private LocalDate endDate;
    
    @Column(name = "monthly_price")
    private BigDecimal monthlyPrice;
    
    private BigDecimal deposit;
    
    @Column(name = "odometer_start")
    private Integer odometerStart;
    
    @Column(name = "fuel_level")
    private String fuelLevel;
    
    // 签名
    @Column(name = "borrower_signature")
    private String borrowerSignature;
    
    @Column(name = "lender_signature")
    private String lenderSignature;
    
    @Column(name = "sign_date")
    private LocalDate signDate;
    
    @Column(name = "status")
    private String status;  // DRAFT, SIGNED, COMPLETED
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Getters and Setters
    public Integer getContractId() { return contractId; }
    public void setContractId(Integer contractId) { this.contractId = contractId; }
    public Integer getRentalId() { return rentalId; }
    public void setRentalId(Integer rentalId) { this.rentalId = rentalId; }
    public String getPlateNumber() { return plateNumber; }
    public void setPlateNumber(String plateNumber) { this.plateNumber = plateNumber; }
    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getCustomerIdNumber() { return customerIdNumber; }
    public void setCustomerIdNumber(String customerIdNumber) { this.customerIdNumber = customerIdNumber; }
    public String getCustomerPhone() { return customerPhone; }
    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }
    public String getCustomerWechat() { return customerWechat; }
    public void setCustomerWechat(String customerWechat) { this.customerWechat = customerWechat; }
    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }
    public String getCustomerAddress() { return customerAddress; }
    public void setCustomerAddress(String customerAddress) { this.customerAddress = customerAddress; }
    public String getVehicleInfo() { return vehicleInfo; }
    public void setVehicleInfo(String vehicleInfo) { this.vehicleInfo = vehicleInfo; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public BigDecimal getMonthlyPrice() { return monthlyPrice; }
    public void setMonthlyPrice(BigDecimal monthlyPrice) { this.monthlyPrice = monthlyPrice; }
    public BigDecimal getDeposit() { return deposit; }
    public void setDeposit(BigDecimal deposit) { this.deposit = deposit; }
    public Integer getOdometerStart() { return odometerStart; }
    public void setOdometerStart(Integer odometerStart) { this.odometerStart = odometerStart; }
    public String getFuelLevel() { return fuelLevel; }
    public void setFuelLevel(String fuelLevel) { this.fuelLevel = fuelLevel; }
    public String getBorrowerSignature() { return borrowerSignature; }
    public void setBorrowerSignature(String borrowerSignature) { this.borrowerSignature = borrowerSignature; }
    public String getLenderSignature() { return lenderSignature; }
    public void setLenderSignature(String lenderSignature) { this.lenderSignature = lenderSignature; }
    public LocalDate getSignDate() { return signDate; }
    public void setSignDate(LocalDate signDate) { this.signDate = signDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}

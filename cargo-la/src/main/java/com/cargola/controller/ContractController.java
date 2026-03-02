package com.cargola.controller;

import com.cargola.entity.Contract;
import com.cargola.entity.Customer;
import com.cargola.entity.Rental;
import com.cargola.entity.Vehicle;
import com.cargola.repository.ContractRepository;
import com.cargola.repository.CustomerRepository;
import com.cargola.repository.RentalRepository;
import com.cargola.repository.VehicleRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contracts")
@Tag(name = "合同管理", description = "租车合同相关接口")
public class ContractController {

    @Autowired
    private ContractRepository contractRepository;
    
    @Autowired
    private RentalRepository rentalRepository;
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private VehicleRepository vehicleRepository;

    @GetMapping
    @Operation(summary = "获取所有合同")
    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取合同")
    public Contract getContractById(@PathVariable Integer id) {
        return contractRepository.findById(id).orElse(null);
    }

    @GetMapping("/rental/{rentalId}")
    @Operation(summary = "根据租赁ID获取合同")
    public List<Contract> getContractsByRental(@PathVariable Integer rentalId) {
        return contractRepository.findByRentalId(rentalId);
    }

    @PostMapping("/generate")
    @Operation(summary = "自动生成合同")
    @Transactional
    public Contract generateContract(@RequestParam Integer rentalId) {
        Optional<Rental> rentalOpt = rentalRepository.findById(rentalId);
        if (rentalOpt.isEmpty()) {
            throw new RuntimeException("租赁记录不存在: " + rentalId);
        }
        
        Rental rental = rentalOpt.get();
        Optional<Customer> customerOpt = customerRepository.findById(rental.getCustomerId());
        Customer customer = customerOpt.orElse(null);
        Optional<Vehicle> vehicleOpt = vehicleRepository.findById(rental.getPlateNumber());
        Vehicle vehicle = vehicleOpt.orElse(null);
        
        Contract contract = new Contract();
        contract.setRentalId(rentalId);
        contract.setPlateNumber(rental.getPlateNumber());
        contract.setCustomerId(rental.getCustomerId());
        
        if (customer != null) {
            contract.setCustomerName(customer.getName());
            contract.setCustomerIdNumber(customer.getDriverLicense());
            contract.setCustomerPhone(customer.getPhone());
            contract.setCustomerWechat(customer.getWechatId());
            contract.setCustomerEmail(customer.getEmail());
            contract.setCustomerAddress(customer.getAddress());
        }
        
        if (vehicle != null) {
            contract.setVehicleInfo(vehicle.getYear() + " " + vehicle.getBrand() + " " + vehicle.getModel() + " " + vehicle.getColor());
        }
        
        contract.setStartDate(rental.getStartDate());
        contract.setEndDate(rental.getEndDate());
        contract.setMonthlyPrice(rental.getRentPrice());
        contract.setDeposit(rental.getDeposit());
        contract.setOdometerStart(vehicle != null ? vehicle.getMileage() : 0);
        contract.setFuelLevel("Full");
        contract.setStatus("DRAFT");
        contract.setSignDate(LocalDate.now());
        contract.setCreatedAt(LocalDateTime.now());
        contract.setUpdatedAt(LocalDateTime.now());
        
        return contractRepository.save(contract);
    }

    @PostMapping("/generate-all")
    @Operation(summary = "为所有租赁自动生成合同")
    @Transactional
    public String generateAllContracts() {
        List<Rental> rentals = rentalRepository.findAll();
        int count = 0;
        
        for (Rental rental : rentals) {
            List<Contract> existing = contractRepository.findByRentalId(rental.getRentalId());
            if (existing.isEmpty()) {
                try {
                    generateContract(rental.getRentalId());
                    count++;
                } catch (Exception e) {
                    // skip failed ones
                }
            }
        }
        
        return "已生成 " + count + " 份合同";
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新合同")
    @Transactional
    public Contract updateContract(@PathVariable Integer id, @RequestBody Contract contract) {
        contract.setContractId(id);
        contract.setUpdatedAt(LocalDateTime.now());
        return contractRepository.save(contract);
    }

    @PostMapping("/{id}/sign")
    @Operation(summary = "签署合同")
    @Transactional
    public Contract signContract(@PathVariable Integer id, @RequestBody SignRequest request) {
        Optional<Contract> opt = contractRepository.findById(id);
        if (opt.isEmpty()) {
            throw new RuntimeException("合同不存在");
        }
        
        Contract contract = opt.get();
        contract.setBorrowerSignature(request.getSignature());
        contract.setLenderSignature("Anstar Auto");
        contract.setStatus("SIGNED");
        contract.setUpdatedAt(LocalDateTime.now());
        
        return contractRepository.save(contract);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除合同")
    public String deleteContract(@PathVariable Integer id) {
        contractRepository.deleteById(id);
        return "合同删除成功";
    }
    
    // Sign request class
    public static class SignRequest {
        private String signature;
        
        public String getSignature() { return signature; }
        public void setSignature(String signature) { this.signature = signature; }
    }
}

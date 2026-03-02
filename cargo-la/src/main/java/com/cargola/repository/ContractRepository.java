package com.cargola.repository;

import com.cargola.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Integer> {
    List<Contract> findByRentalId(Integer rentalId);
    List<Contract> findByCustomerId(Integer customerId);
    List<Contract> findByPlateNumber(String plateNumber);
}

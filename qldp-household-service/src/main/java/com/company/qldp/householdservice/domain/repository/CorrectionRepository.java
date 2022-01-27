package com.company.qldp.householdservice.domain.repository;

import com.company.qldp.domain.Correction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CorrectionRepository extends JpaRepository<Correction, Integer> {

    List<Correction> findAllByHousehold_Id(Integer id);
    
    Correction findByHousehold_IdAndId(Integer householdId, Integer id);
}

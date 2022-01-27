package com.company.qldp.householdservice.api.repository;

import com.company.qldp.domain.HouseholdHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HouseholdHistoryRepository extends JpaRepository<HouseholdHistory, Integer> {
    
    List<HouseholdHistory> findAllByHousehold_Id(Integer id);
    
    HouseholdHistory findByHousehold_IdAndId(Integer householdId, Integer id);
}

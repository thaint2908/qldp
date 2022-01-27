package com.company.qldp.householdservice.domain.repository;

import com.company.qldp.domain.Household;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseholdRepository extends JpaRepository<Household, Integer> {

    Household findByHouseholdCode(String code);
}

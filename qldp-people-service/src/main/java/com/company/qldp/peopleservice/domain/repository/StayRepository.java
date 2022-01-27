package com.company.qldp.peopleservice.domain.repository;

import com.company.qldp.domain.Stay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StayRepository extends JpaRepository<Stay, Integer>, JpaSpecificationExecutor<Stay> {
    
    Stay findByTempResidenceCode(String code);
}

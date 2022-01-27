package com.company.qldp.peopleservice.domain.repository;

import com.company.qldp.domain.Death;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DeathRepository extends JpaRepository<Death, Integer>, JpaSpecificationExecutor<Death> {
    
    Death findByDeathCertNumber(String deathCertNumber);
}

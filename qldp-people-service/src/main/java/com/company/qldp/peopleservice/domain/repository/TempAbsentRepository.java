package com.company.qldp.peopleservice.domain.repository;

import com.company.qldp.domain.TempAbsent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TempAbsentRepository extends JpaRepository<TempAbsent, Integer>, JpaSpecificationExecutor<TempAbsent> {

    TempAbsent findByTempAbsentCode(String code);
}

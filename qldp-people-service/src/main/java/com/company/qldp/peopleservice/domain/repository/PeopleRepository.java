package com.company.qldp.peopleservice.domain.repository;

import com.company.qldp.domain.People;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeopleRepository extends JpaRepository<People, Integer> {
    
    People findByPeopleCode(String peopleCode);
}

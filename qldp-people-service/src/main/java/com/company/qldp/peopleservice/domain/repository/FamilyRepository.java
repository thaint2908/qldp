package com.company.qldp.peopleservice.domain.repository;

import com.company.qldp.domain.Family;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FamilyRepository extends JpaRepository<Family, Integer> {

    List<Family> findAllByPerson_Id(Integer id);
    
    Family findByPerson_IdAndId(Integer personId, Integer id);
}

package com.company.qldp.peopleservice.domain.repository;

import com.company.qldp.domain.IDCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDCardRepository extends JpaRepository<IDCard, Integer> {
    
    IDCard findByIdCardNumber(String idCardNumber);
    
    IDCard findByPerson_Id(Integer id);
}

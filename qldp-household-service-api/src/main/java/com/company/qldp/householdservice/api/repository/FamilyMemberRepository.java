package com.company.qldp.householdservice.api.repository;

import com.company.qldp.domain.FamilyMember;
import com.company.qldp.domain.HouseholdPeopleId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FamilyMemberRepository extends JpaRepository<FamilyMember, HouseholdPeopleId> {

    List<FamilyMember> findAllByHousehold_Id(Integer id);
    
    FamilyMember findByHousehold_IdAndPerson_Id(Integer id, Integer personId);
    
    FamilyMember findByPerson_Id(Integer personId);
}

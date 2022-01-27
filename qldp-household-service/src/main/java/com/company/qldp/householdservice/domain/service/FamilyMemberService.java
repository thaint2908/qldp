package com.company.qldp.householdservice.domain.service;

import com.company.qldp.common.Event;
import com.company.qldp.domain.FamilyMember;
import com.company.qldp.domain.Household;
import com.company.qldp.domain.HouseholdHistory;
import com.company.qldp.domain.People;
import com.company.qldp.householdservice.api.repository.HouseholdHistoryRepository;
import com.company.qldp.householdservice.domain.dto.FamilyMemberDto;
import com.company.qldp.householdservice.domain.exception.HouseholdNotFoundException;
import com.company.qldp.householdservice.api.repository.FamilyMemberRepository;
import com.company.qldp.householdservice.domain.repository.HouseholdRepository;
import com.company.qldp.peopleservice.domain.exception.PersonNotFoundException;
import com.company.qldp.peopleservice.domain.repository.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.company.qldp.householdservice.domain.dto.FamilyMemberDto.*;

@Service
public class FamilyMemberService {
    
    private FamilyMemberRepository familyMemberRepository;
    private HouseholdRepository householdRepository;
    private PeopleRepository peopleRepository;
    private HouseholdHistoryRepository householdHistoryRepository;
    
    @Autowired
    public FamilyMemberService(
        FamilyMemberRepository familyMemberRepository,
        HouseholdRepository householdRepository,
        PeopleRepository peopleRepository,
        HouseholdHistoryRepository householdHistoryRepository
    ) {
        this.familyMemberRepository = familyMemberRepository;
        this.householdRepository = householdRepository;
        this.peopleRepository = peopleRepository;
        this.householdHistoryRepository = householdHistoryRepository;
    }
    
    @Transactional
    public List<FamilyMember> addFamilyMembers(Integer householdId, FamilyMemberDto familyMemberDto) {
        List<Member> members = familyMemberDto.getMembers();
        
        Household household = householdRepository.findById(householdId)
            .orElseThrow(HouseholdNotFoundException::new);
        
        List<FamilyMember> familyMembers = new ArrayList<>();
        
        for (Member member : members) {
            People person = peopleRepository.findById(member.getId())
                .orElseThrow(PersonNotFoundException::new);
            
            FamilyMember familyMember = FamilyMember.builder()
                .person(person)
                .household(household)
                .hostRelation(member.getHostRelation())
                .build();
            FamilyMember savedMember = familyMemberRepository.save(familyMember);
            familyMembers.add(savedMember);
    
            HouseholdHistory householdHistory = HouseholdHistory.builder()
                .household(savedMember.getHousehold())
                .affectPerson(savedMember.getPerson())
                .date(savedMember.getPerson().getCreatedDate())
                .event(Event.NEW)
                .build();
            householdHistoryRepository.save(householdHistory);
        }
        
        return familyMembers;
    }
    
    @Transactional
    public FamilyMember getFamilyMember(Integer id, Integer memberId) {
        FamilyMember member = familyMemberRepository.findByHousehold_IdAndPerson_Id(id, memberId);
        getMemberInfo(member);
        
        return member;
    }
    
    @Transactional
    public List<FamilyMember> getFamilyMembers(Integer id) {
        List<FamilyMember> familyMembers = familyMemberRepository.findAllByHousehold_Id(id);
        familyMembers.forEach(this::getMemberInfo);
        
        return familyMembers;
    }
    
    private void getMemberInfo(FamilyMember member) {
        member.getPerson().hashCode();
    }
}

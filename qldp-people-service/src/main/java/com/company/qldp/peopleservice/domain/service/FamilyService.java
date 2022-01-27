package com.company.qldp.peopleservice.domain.service;

import com.company.qldp.domain.Family;
import com.company.qldp.domain.People;
import com.company.qldp.peopleservice.domain.dto.FamilyDto;
import com.company.qldp.peopleservice.domain.exception.PersonNotFoundException;
import com.company.qldp.peopleservice.domain.repository.FamilyRepository;
import com.company.qldp.peopleservice.domain.repository.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.company.qldp.peopleservice.domain.dto.FamilyDto.*;

@Service
public class FamilyService {
    
    private FamilyRepository familyRepository;
    private PeopleRepository peopleRepository;
    
    @Autowired
    public FamilyService(
        FamilyRepository familyRepository,
        PeopleRepository peopleRepository
    ) {
        this.familyRepository = familyRepository;
        this.peopleRepository = peopleRepository;
    }
    
    public List<Family> addFamilyRelationToPeople(Integer id, FamilyDto familyDto) {
        People person = peopleRepository.findById(id)
            .orElseThrow(PersonNotFoundException::new);
        
        List<MemberRelation> relations = familyDto.getRelations();
        List<Family> familyList = new ArrayList<>();
        
        for (MemberRelation relation : relations) {
            People member = peopleRepository.findById(relation.getMemberId())
                .orElseThrow(PersonNotFoundException::new);
            
            Family family = Family.builder()
                .person(person)
                .info(member.getInfo())
                .personRelation(relation.getMemberRelation())
                .build();
            
            Family savedFamily = familyRepository.save(family);
            familyList.add(savedFamily);
        }
        
        return familyList;
    }
    
    public Family getFamilyByPeopleId(Integer id, Integer familyId) {
        return familyRepository.findByPerson_IdAndId(id, familyId);
    }
    
    public List<Family> getFamiliesByPeopleId(Integer id) {
        return familyRepository.findAllByPerson_Id(id);
    }
}

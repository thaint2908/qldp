package com.company.qldp.householdservice.domain.service;

import com.company.qldp.common.Event;
import com.company.qldp.common.util.RandomCodeGenerator;
import com.company.qldp.domain.*;
import com.company.qldp.elasticsearchservice.domain.entity.HouseholdSearch;
import com.company.qldp.elasticsearchservice.domain.repository.HouseholdSearchRepository;
import com.company.qldp.elasticsearchservice.domain.repository.PeopleSearchRepository;
import com.company.qldp.householdservice.api.repository.HouseholdHistoryRepository;
import com.company.qldp.householdservice.domain.dto.HouseholdDto;
import com.company.qldp.householdservice.domain.dto.LeaveHouseholdDto;
import com.company.qldp.householdservice.domain.dto.SeparateHouseholdDto;
import com.company.qldp.householdservice.domain.exception.HouseholdNotFoundException;
import com.company.qldp.householdservice.api.repository.FamilyMemberRepository;
import com.company.qldp.householdservice.domain.repository.HouseholdRepository;
import com.company.qldp.peopleservice.domain.exception.PersonNotFoundException;
import com.company.qldp.peopleservice.domain.repository.PeopleRepository;
import com.company.qldp.userservice.domain.exception.UserNotFoundException;
import com.company.qldp.userservice.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HouseholdService {
    
    private HouseholdRepository householdRepository;
    private PeopleRepository peopleRepository;
    private HouseholdSearchRepository householdSearchRepository;
    private PeopleSearchRepository peopleSearchRepository;
    private FamilyMemberRepository familyMemberRepository;
    private UserRepository userRepository;
    private HouseholdHistoryRepository householdHistoryRepository;
    
    @Autowired
    public HouseholdService(
        HouseholdRepository householdRepository,
        PeopleRepository peopleRepository,
        HouseholdSearchRepository householdSearchRepository,
        PeopleSearchRepository peopleSearchRepository,
        FamilyMemberRepository familyMemberRepository,
        UserRepository userRepository,
        HouseholdHistoryRepository householdHistoryRepository
    ) {
        this.householdRepository = householdRepository;
        this.peopleRepository = peopleRepository;
        this.householdSearchRepository = householdSearchRepository;
        this.peopleSearchRepository = peopleSearchRepository;
        this.familyMemberRepository = familyMemberRepository;
        this.userRepository = userRepository;
        this.householdHistoryRepository = householdHistoryRepository;
    }
    
    public Household createHousehold(HouseholdDto householdDto) {
        People host = peopleRepository.findById(householdDto.getHostPersonId())
            .orElseThrow(HouseholdNotFoundException::new);
        User performer = userRepository.findByUsername(householdDto.getPerformerName());
        
        if (performer == null) {
            throw new UserNotFoundException();
        }
        
        String code = generateHouseholdCode();
        
        Household household = Household.builder()
            .householdCode(code)
            .host(host)
            .performer(performer)
            .areaCode(householdDto.getAreaCode())
            .address(householdDto.getAddress())
            .createdDay(Date.from(Instant.parse(householdDto.getCreatedDay())))
            .build();
        
        Household savedHousehold = householdRepository.save(household);
    
        peopleSearchRepository.findById(householdDto.getHostPersonId())
            .map(peopleSearch -> {
                HouseholdSearch householdSearch = HouseholdSearch.builder()
                    .id(savedHousehold.getId())
                    .householdCode(savedHousehold.getHouseholdCode())
                    .host(peopleSearch)
                    .address(savedHousehold.getAddress())
                    .createdDay(savedHousehold.getCreatedDay())
                    .build();
                
                return householdSearchRepository.save(householdSearch);
            }).subscribe(Mono::subscribe);
            
        return savedHousehold;
    }
    
    @Transactional
    public Household getHousehold(Integer id) {
        Household household = householdRepository.findById(id)
            .orElseThrow(HouseholdNotFoundException::new);
        getHouseholdExtraInfo(household);
        
        return household;
    }
    
    private void getHouseholdExtraInfo(Household household) {
        household.getHost().hashCode();
        household.getPerformer().hashCode();
    }
    
    @Transactional
    public Household leaveHousehold(Integer id, LeaveHouseholdDto leaveHouseholdDto) {
        Household household = householdRepository.findById(id)
            .orElseThrow(HouseholdNotFoundException::new);
        User performer = userRepository.findByUsername(leaveHouseholdDto.getPerformerName());
        
        if (performer == null) {
            throw new UserNotFoundException();
        }
        
        household.setLeaveDay(Date.from(Instant.parse(leaveHouseholdDto.getLeaveDate())));
        household.setLeaveReason(leaveHouseholdDto.getLeaveReason());
        household.setPerformer(performer);
        household.setAddress(leaveHouseholdDto.getNewAddress());
        
        Household savedHousehold = householdRepository.save(household);
        getHouseholdExtraInfo(savedHousehold);
        
        return savedHousehold;
    }
    
    @Transactional
    public Household separateHousehold(Integer id, SeparateHouseholdDto separateHouseholdDto) {
        Household household = householdRepository.findById(id)
            .orElseThrow(HouseholdNotFoundException::new);
        People host = peopleRepository.findById(separateHouseholdDto.getHostId())
            .orElseThrow(PersonNotFoundException::new);
        User performer = userRepository.findByUsername(separateHouseholdDto.getPerformerName());
        
        if (performer == null) {
            throw new UserNotFoundException();
        }
        
        List<FamilyMember> members = familyMemberRepository.findAllByHousehold_Id(id);
        List<FamilyMember> deleteMembers = members.stream()
            .filter(member -> member.getPerson().getId().equals(host.getId()))
            .collect(Collectors.toList());
        familyMemberRepository.deleteAll(deleteMembers);
        host.setPermanentAddress(separateHouseholdDto.getNewAddress());
        peopleRepository.save(host);
        
        String code = generateHouseholdCode();
        
        Household newHousehold = Household.builder()
            .householdCode(code)
            .host(host)
            .address(separateHouseholdDto.getNewAddress())
            .createdDay(Date.from(Instant.parse(separateHouseholdDto.getCreatedDay())))
            .areaCode(separateHouseholdDto.getAreaCode())
            .performer(performer)
            .build();
        Household savedHousehold = householdRepository.save(newHousehold);
        
        peopleSearchRepository.findById(host.getId()).map(peopleSearch -> {
            HouseholdSearch householdSearch = HouseholdSearch.builder()
                .id(savedHousehold.getId())
                .householdCode(savedHousehold.getHouseholdCode())
                .host(peopleSearch)
                .address(savedHousehold.getAddress())
                .createdDay(savedHousehold.getCreatedDay())
                .build();
            
            return householdSearchRepository.save(householdSearch);
        }).subscribe(Mono::subscribe);
        
        HouseholdHistory householdHistory = HouseholdHistory.builder()
            .household(household)
            .newHousehold(savedHousehold)
            .date(savedHousehold.getCreatedDay())
            .event(Event.SEPARATE)
            .build();
        householdHistoryRepository.save(householdHistory);
        
        return savedHousehold;
    }
    
    private String generateHouseholdCode() {
        String code = "24" + RandomCodeGenerator.generateCode(7);
        while (householdCodeExists(code)) {
            code = "24" + RandomCodeGenerator.generateCode(7);
        }
        
        return code;
    }
    
    private boolean householdCodeExists(String code) {
        return householdRepository.findByHouseholdCode(code) != null;
    }
}

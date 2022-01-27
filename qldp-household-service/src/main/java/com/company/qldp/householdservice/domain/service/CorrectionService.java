package com.company.qldp.householdservice.domain.service;

import com.company.qldp.domain.Correction;
import com.company.qldp.domain.Household;
import com.company.qldp.domain.People;
import com.company.qldp.domain.User;
import com.company.qldp.elasticsearchservice.domain.entity.HouseholdSearch;
import com.company.qldp.elasticsearchservice.domain.entity.PeopleSearch;
import com.company.qldp.elasticsearchservice.domain.repository.HouseholdSearchRepository;
import com.company.qldp.elasticsearchservice.domain.repository.PeopleSearchRepository;
import com.company.qldp.householdservice.domain.dto.CorrectionDto;
import com.company.qldp.householdservice.domain.exception.ChangeInfoNotSupportException;
import com.company.qldp.householdservice.domain.exception.HouseholdNotFoundException;
import com.company.qldp.householdservice.domain.exception.InvalidAddressException;
import com.company.qldp.householdservice.domain.exception.InvalidHostException;
import com.company.qldp.householdservice.domain.repository.CorrectionRepository;
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

@Service
public class CorrectionService {
    
    private CorrectionRepository correctionRepository;
    private HouseholdRepository householdRepository;
    private PeopleRepository peopleRepository;
    private HouseholdSearchRepository householdSearchRepository;
    private PeopleSearchRepository peopleSearchRepository;
    private UserRepository userRepository;
    
    @Autowired
    public CorrectionService(
        CorrectionRepository correctionRepository,
        HouseholdRepository householdRepository,
        PeopleRepository peopleRepository,
        HouseholdSearchRepository householdSearchRepository,
        PeopleSearchRepository peopleSearchRepository,
        UserRepository userRepository
    ) {
        this.correctionRepository = correctionRepository;
        this.householdRepository = householdRepository;
        this.peopleRepository = peopleRepository;
        this.householdSearchRepository = householdSearchRepository;
        this.peopleSearchRepository = peopleSearchRepository;
        this.userRepository = userRepository;
    }
    
    @Transactional
    public Correction createCorrection(Integer id, CorrectionDto correctionDto) {
        Household household = householdRepository.findById(id)
            .orElseThrow(HouseholdNotFoundException::new);
        User performer = userRepository.findByUsername(correctionDto.getPerformerName());
        
        if (performer == null) {
            throw new UserNotFoundException();
        }
    
        Correction correction = Correction.builder()
            .household(household)
            .changeInfo(correctionDto.getChangeInfo())
            .changeDay(Date.from(Instant.parse(correctionDto.getChangeDate())))
            .performer(performer)
            .build();
    
        switch (correctionDto.getChangeInfo()) {
            case "HOST" -> {
                if (household.getHost().getId() != Integer.parseInt(correctionDto.getChangeFrom())) {
                    throw new InvalidHostException();
                }
                
                People newHost = peopleRepository.findById(Integer.parseInt(correctionDto.getChangeTo()))
                    .orElseThrow(PersonNotFoundException::new);
                
                correction.setChangeFrom(household.getHost().getInfo().getFullName());
                correction.setChangeTo(newHost.getInfo().getFullName());
                
                household.setHost(newHost);
                household.setPerformer(performer);
            }
            case "ADDRESS" -> {
                if (!household.getAddress().equals(correctionDto.getChangeFrom())) {
                    throw new InvalidAddressException();
                }
                
                correction.setChangeFrom(correctionDto.getChangeFrom());
                correction.setChangeTo(correctionDto.getChangeTo());
                
                household.setAddress(correction.getChangeTo());
            }
            default -> throw new ChangeInfoNotSupportException();
        }
        
        Household savedHousehold = householdRepository.save(household);
        
        peopleSearchRepository.findById(savedHousehold.getHost().getId())
            .zipWith(householdSearchRepository.findById(savedHousehold.getId()))
            .map(tuple2 -> {
                PeopleSearch host = tuple2.getT1();
                HouseholdSearch householdSearch = tuple2.getT2();
                
                householdSearch.setHost(host);
                householdSearch.setAddress(savedHousehold.getAddress());
                
                return householdSearchRepository.save(householdSearch);
            }).subscribe(Mono::subscribe);
        
        return correctionRepository.save(correction);
    }
    
    @Transactional
    public Correction getCorrectionByHouseholdId(Integer id, Integer correctionId) {
        Correction correction = correctionRepository.findByHousehold_IdAndId(id, correctionId);
        getCorrectionInfo(correction);
        
        return correction;
    }
    
    @Transactional
    public List<Correction> getCorrectionsByHouseholdId(Integer id) {
        List<Correction> corrections = correctionRepository.findAllByHousehold_Id(id);
        corrections.forEach(this::getCorrectionInfo);
        
        return corrections;
    }
    
    private void getCorrectionInfo(Correction correction) {
        correction.getPerformer().hashCode();
    }
}

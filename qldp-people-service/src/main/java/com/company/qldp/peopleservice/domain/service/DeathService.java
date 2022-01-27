package com.company.qldp.peopleservice.domain.service;

import com.company.qldp.common.Event;
import com.company.qldp.domain.*;
import com.company.qldp.elasticsearchservice.domain.repository.PeopleSearchRepository;
import com.company.qldp.householdservice.api.repository.FamilyMemberRepository;
import com.company.qldp.householdservice.api.repository.HouseholdHistoryRepository;
import com.company.qldp.peopleservice.domain.dto.DeathDto;
import com.company.qldp.peopleservice.domain.exception.DeathAlreadyExistException;
import com.company.qldp.peopleservice.domain.exception.DeathNotFoundException;
import com.company.qldp.peopleservice.domain.exception.PersonNotFoundException;
import com.company.qldp.peopleservice.domain.repository.*;
import com.company.qldp.userservice.domain.exception.UserNotFoundException;
import com.company.qldp.userservice.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import static com.company.qldp.peopleservice.domain.repository.specification.DeathSpecification.*;

@Service
public class DeathService {
    
    private DeathRepository deathRepository;
    private IDCardRepository idCardRepository;
    private PeopleRepository peopleRepository;
    private UserRepository userRepository;
    private HouseholdHistoryRepository householdHistoryRepository;
    private FamilyMemberRepository familyMemberRepository;
    private PeopleSearchRepository peopleSearchRepository;
    
    @Autowired
    public DeathService(
        DeathRepository deathRepository,
        IDCardRepository idCardRepository,
        PeopleRepository peopleRepository,
        UserRepository userRepository,
        HouseholdHistoryRepository householdHistoryRepository,
        FamilyMemberRepository familyMemberRepository,
        PeopleSearchRepository peopleSearchRepository
    ) {
        this.deathRepository = deathRepository;
        this.idCardRepository = idCardRepository;
        this.peopleRepository = peopleRepository;
        this.userRepository = userRepository;
        this.householdHistoryRepository = householdHistoryRepository;
        this.familyMemberRepository = familyMemberRepository;
        this.peopleSearchRepository = peopleSearchRepository;
    }
    
    @Transactional
    public Death createDeath(DeathDto deathDto) {
        String deathCertNumber = deathDto.getDeathCertNumber();
        String declaredPersonIDCardNumber = deathDto.getDeclaredPersonIdCardNumber();
        String deathPersonCode = deathDto.getDeathPersonCode();
        
        Death death = deathRepository.findByDeathCertNumber(deathCertNumber);
        People declaredPerson = idCardRepository.findByIdCardNumber(declaredPersonIDCardNumber)
            .getPerson();
        People deathPerson = peopleRepository.findByPeopleCode(deathPersonCode);
        
        if (death != null) {
            throw new DeathAlreadyExistException();
        }
        if (declaredPerson == null || deathPerson == null) {
            throw new PersonNotFoundException();
        }
        
        User deletedManager = userRepository.findByUsername(deathDto.getDeletedManagerUsername());
        if (deletedManager == null) {
            throw new UserNotFoundException();
        }
        
        deathPerson.setDeletedDate(new Date());
        deathPerson.setDeletedManager(deletedManager);
        deathPerson.setDeletedReason("đã chết");
        People savedDeathPerson = peopleRepository.save(deathPerson);
        
        peopleSearchRepository.findById(savedDeathPerson.getId()).map(peopleSearch -> {
            peopleSearch.setLiveStatus("death");
            
            return peopleSearchRepository.save(peopleSearch);
        }).subscribe(Mono::subscribe);
        
        Death createdDeath = Death.builder()
            .deathCertNumber(deathDto.getDeathCertNumber())
            .declaredPerson(declaredPerson)
            .deathPerson(deathPerson)
            .declaredDay(Date.from(Instant.parse(deathDto.getDeclaredDay())))
            .deathDay(Date.from(Instant.parse(deathDto.getDeathDay())))
            .deathReason(deathDto.getDeathReason())
            .build();
    
        Household household = familyMemberRepository.findByPerson_Id(deathPerson.getId())
            .getHousehold();
        HouseholdHistory householdHistory = HouseholdHistory.builder()
            .household(household)
            .affectPerson(savedDeathPerson)
            .date(createdDeath.getDeathDay())
            .event(Event.DEATH)
            .build();
        householdHistoryRepository.save(householdHistory);
        
        return deathRepository.save(createdDeath);
    }
    
    @Transactional
    public Death getDeath(Integer id) {
        Death death = deathRepository.findById(id)
            .orElseThrow(DeathNotFoundException::new);
        getDeathInfo(death);
        
        return death;
    }
    
    @Transactional
    public List<Death> getDeathsByFilters(MultiValueMap<String, String> queryParams) {
        String dateRange = queryParams.getFirst("date");
    
        Specification<Death> spec = makeDateRangeSpecification(dateRange);
        
        List<Death> deaths = deathRepository.findAll(spec);
        deaths.forEach(this::getDeathInfo);
        
        return deaths;
    }
    
    private void getDeathInfo(Death death) {
        death.getDeathPerson().hashCode();
        death.getDeclaredPerson().hashCode();
    }
}

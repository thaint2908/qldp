package com.company.qldp.peopleservice.domain.service;

import com.company.qldp.common.DateInterval;
import com.company.qldp.common.Event;
import com.company.qldp.common.util.RandomCodeGenerator;
import com.company.qldp.domain.*;
import com.company.qldp.elasticsearchservice.domain.repository.PeopleSearchRepository;
import com.company.qldp.householdservice.api.repository.FamilyMemberRepository;
import com.company.qldp.householdservice.api.repository.HouseholdHistoryRepository;
import com.company.qldp.peopleservice.domain.dto.StayDto;
import com.company.qldp.peopleservice.domain.exception.PersonNotFoundException;
import com.company.qldp.peopleservice.domain.exception.StayNotFoundException;
import com.company.qldp.peopleservice.domain.repository.IDCardRepository;
import com.company.qldp.peopleservice.domain.repository.PeopleRepository;
import com.company.qldp.peopleservice.domain.repository.StayRepository;
import com.company.qldp.common.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.company.qldp.peopleservice.domain.repository.specification.GenericSpecification.*;

@Service
public class StayService {
    
    private StayRepository stayRepository;
    private IDCardRepository idCardRepository;
    private PeopleRepository peopleRepository;
    private PeopleSearchRepository peopleSearchRepository;
    private FamilyMemberRepository familyMemberRepository;
    private HouseholdHistoryRepository householdHistoryRepository;
    
    @Autowired
    public StayService(
        StayRepository stayRepository,
        IDCardRepository idCardRepository,
        PeopleRepository peopleRepository,
        PeopleSearchRepository peopleSearchRepository,
        FamilyMemberRepository familyMemberRepository,
        HouseholdHistoryRepository householdHistoryRepository
    ) {
        this.stayRepository = stayRepository;
        this.idCardRepository = idCardRepository;
        this.peopleRepository = peopleRepository;
        this.peopleSearchRepository = peopleSearchRepository;
        this.familyMemberRepository = familyMemberRepository;
        this.householdHistoryRepository = householdHistoryRepository;
    }
    
    @Transactional
    public Stay createStay(StayDto stayDto) {
        People people = idCardRepository.findByIdCardNumber(stayDto.getIdCardNumber())
            .getPerson();
        
        if (people == null) {
            throw new PersonNotFoundException();
        }
    
        DateInterval interval = DateUtils.createDateInterval(
            stayDto.getFromDate(),
            stayDto.getToDate()
        );
        
        String code = RandomCodeGenerator.generateCode(8);
        while (tempResidentCodeExists(code)) {
            code = RandomCodeGenerator.generateCode(8);
        }
        
        Stay stay = Stay.builder()
            .person(people)
            .tempResidenceCode(code)
            .phoneNumber(stayDto.getPhoneNumber())
            .interval(interval)
            .reason(stayDto.getReason())
            .build();
    
        PersonalMobilization mobilization;
        
        if (people.getMobilization() == null) {
            mobilization = PersonalMobilization.builder()
                .arrivalDate(stay.getInterval().getFrom())
                .arrivalReason(stay.getReason())
                .build();
        } else {
            mobilization = people.getMobilization();
            mobilization.setArrivalDate(stay.getInterval().getFrom());
            mobilization.setArrivalReason(stay.getReason());
        }
        
        people.setMobilization(mobilization);
        
        People savedPeople = peopleRepository.save(people);
        
        peopleSearchRepository.findById(savedPeople.getId()).map(peopleSearch -> {
            peopleSearch.setArrivalDate(savedPeople.getMobilization().getArrivalDate());
            
            return peopleSearchRepository.save(peopleSearch);
        }).subscribe(Mono::subscribe);
    
        FamilyMember familyMember = familyMemberRepository.findByPerson_Id(savedPeople.getId());
        
        if (familyMember != null) {
            Household household = familyMember.getHousehold();
            HouseholdHistory householdHistory = HouseholdHistory.builder()
                .household(household)
                .affectPerson(savedPeople)
                .date(savedPeople.getMobilization().getArrivalDate())
                .event(Event.STAY)
                .build();
            householdHistoryRepository.save(householdHistory);
        }
        
        return stayRepository.save(stay);
    }
    
    private boolean tempResidentCodeExists(String code) {
        return stayRepository.findByTempResidenceCode(code) != null;
    }
    
    @Transactional
    public Stay getStay(Integer id) {
        Stay stay = stayRepository.findById(id)
            .orElseThrow(StayNotFoundException::new);
        getStayInfo(stay);
        
        return stay;
    }
    
    @Transactional
    public List<Stay> getStaysByFilters(MultiValueMap<String, String> queryParams) {
        String dateRange = queryParams.getFirst("date");
        
        Specification<Stay> spec = makeDateRangeSpecification(dateRange);
        
        List<Stay> stays = stayRepository.findAll(spec);
        stays.forEach(this::getStayInfo);
        
        return stays;
    }
    
    private void getStayInfo(Stay stay) {
        stay.getPerson().hashCode();
    }
}

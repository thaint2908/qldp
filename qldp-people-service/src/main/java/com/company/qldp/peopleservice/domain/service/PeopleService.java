package com.company.qldp.peopleservice.domain.service;

import com.company.qldp.common.PeopleInfo;
import com.company.qldp.common.util.SexUtils;
import com.company.qldp.domain.*;
import com.company.qldp.elasticsearchservice.domain.entity.PeopleSearch;
import com.company.qldp.elasticsearchservice.domain.repository.PeopleSearchRepository;
import com.company.qldp.peopleservice.domain.dto.LeavePeopleDto;
import com.company.qldp.peopleservice.domain.dto.PersonDto;
import com.company.qldp.peopleservice.domain.dto.UpdatePersonDto;
import com.company.qldp.peopleservice.domain.exception.PersonNotFoundException;
import com.company.qldp.peopleservice.domain.repository.PeopleRepository;
import com.company.qldp.common.util.RandomCodeGenerator;
import com.company.qldp.userservice.domain.exception.UserNotFoundException;
import com.company.qldp.userservice.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Date;

@Service
public class PeopleService {
    
    private PeopleRepository peopleRepository;
    private UserRepository userRepository;
    private PeopleSearchRepository peopleSearchRepository;
    
    @Autowired
    public PeopleService(
        PeopleRepository peopleRepository,
        UserRepository userRepository,
        PeopleSearchRepository peopleSearchRepository
    ) {
        this.peopleRepository = peopleRepository;
        this.userRepository = userRepository;
        this.peopleSearchRepository = peopleSearchRepository;
    }
    
    public People createPeople(PersonDto personDto) {
        String createdManagerUsername = personDto.getCreatedManagerUsername();
        User createdManager = userRepository.findByUsername(createdManagerUsername);
        String peopleCode = RandomCodeGenerator.generateCode(8);
        
        if (createdManager == null) {
            throw new UserNotFoundException();
        }
        
        while (peopleCodeExists(peopleCode)) {
            peopleCode = RandomCodeGenerator.generateCode(12);
        }
        
        PeopleInfo peopleInfo = PeopleInfo.builder()
            .fullName(personDto.getFullName())
            .birthday(Date.from(Instant.parse(personDto.getBirthday())))
            .sex(personDto.getSex())
            .job(personDto.getJob())
            .currentAddress(personDto.getCurrentAddress())
            .build();
    
        PersonalExtraInfo extraInfo = PersonalExtraInfo.builder()
            .domicile(personDto.getDomicile())
            .nation(personDto.getNation())
            .religion(personDto.getReligion())
            .nationality(personDto.getNationality())
            .build();
        
        PersonalEducationInfo educationInfo = PersonalEducationInfo.builder()
            .academicLevel(personDto.getAcademicLevel())
            .criminalRecord(personDto.getCriminalRecord())
            .ethnicLanguage(personDto.getEthnicLanguage())
            .languageLevel(personDto.getLanguageLevel())
            .qualification(personDto.getQualification())
            .workplace(personDto.getWorkplace())
            .build();
        
        People people = People.builder()
            .peopleCode(peopleCode)
            .alias(personDto.getAlias())
            .info(peopleInfo)
            .birthPlace(personDto.getBirthPlace())
            .extraInfo(extraInfo)
            .educationInfo(educationInfo)
            .passportNumber(personDto.getPassportNumber())
            .permanentAddress(personDto.getPermanentAddress())
            .createdManager(createdManager)
            .createdDate(Date.from(Instant.parse(personDto.getCreatedDate())))
            .note(personDto.getNote())
            .build();
        People savedPeople = peopleRepository.save(people);
        
        PeopleSearch peopleSearch = PeopleSearch.builder()
            .id(savedPeople.getId())
            .peopleCode(peopleCode)
            .birthday(savedPeople.getInfo().getBirthday())
            .currentAddress(savedPeople.getInfo().getCurrentAddress())
            .fullName(savedPeople.getInfo().getFullName())
            .job(savedPeople.getInfo().getJob())
            .note(savedPeople.getNote())
            .sex(savedPeople.getInfo().getSex())
            .passportNumber(savedPeople.getPassportNumber())
            .liveStatus("live")
            .build();
        peopleSearchRepository.save(peopleSearch).subscribe();
        
        return savedPeople;
    }
    
    private boolean peopleCodeExists(String peopleCode) {
        return peopleRepository.findByPeopleCode(peopleCode) != null;
    }
    
    public People leavePeople(Integer id, LeavePeopleDto leavePeopleDto) {
        People people = peopleRepository.findById(id)
            .orElseThrow(PersonNotFoundException::new);
        
        PersonalMobilization mobilization = people.getMobilization();
        
        if (mobilization == null) {
            mobilization = PersonalMobilization.builder().build();
        }
        
        mobilization.setLeaveDate(Date.from(Instant.parse(leavePeopleDto.getLeaveDate())));
        mobilization.setLeaveReason(leavePeopleDto.getLeaveReason());
        mobilization.setNewAddress(leavePeopleDto.getNewAddress());
        
        people.setMobilization(mobilization);
        
        People savedPeople = peopleRepository.save(people);
        
        peopleSearchRepository.findById(id).map(peopleSearch -> {
            peopleSearch.setLeaveDate(savedPeople.getMobilization().getLeaveDate());
            
            return peopleSearchRepository.save(peopleSearch);
        }).subscribe(Mono::subscribe);
        
        return savedPeople;
    }
    
    @Transactional
    public People findPersonById(Integer id) {
        People person = peopleRepository.findById(id)
            .orElseThrow(PersonNotFoundException::new);
        getPersonInfo(person);
        
        return person;
    }
    
    private void getPersonInfo(People person) {
        if (person.getCreatedManager() != null) {
            person.getCreatedManager().getRoles().stream().count();
        }
        if (person.getDeletedManager() != null) {
            person.getDeletedManager().getRoles().stream().count();
        }
        if (person.getMobilization() != null) {
            person.getMobilization().getClass();
        }
    }
    
    public People updatePeopleInfo(Integer id, UpdatePersonDto updatePersonDto) {
        People person = peopleRepository.findById(id)
            .orElseThrow(PersonNotFoundException::new);
        
        person.setAlias(updatePersonDto.getAlias());
        person.setPermanentAddress(updatePersonDto.getPermanentAddress());
        person.setBirthPlace(updatePersonDto.getBirthPlace());
        person.setNote(updatePersonDto.getNote());
        person.setPassportNumber(updatePersonDto.getPassportNumber());
        
        PersonalExtraInfo extraInfo = PersonalExtraInfo.builder()
            .domicile(updatePersonDto.getDomicile())
            .nation(updatePersonDto.getNation())
            .nationality(updatePersonDto.getNationality())
            .religion(updatePersonDto.getReligion())
            .build();
        person.setExtraInfo(extraInfo);
        
        PersonalEducationInfo educationInfo = PersonalEducationInfo.builder()
            .workplace(updatePersonDto.getWorkplace())
            .qualification(updatePersonDto.getQualification())
            .languageLevel(updatePersonDto.getLanguageLevel())
            .ethnicLanguage(updatePersonDto.getEthnicLanguage())
            .criminalRecord(updatePersonDto.getCriminalRecord())
            .academicLevel(updatePersonDto.getAcademicLevel())
            .build();
        person.setEducationInfo(educationInfo);
        
        PeopleInfo peopleInfo = PeopleInfo.builder()
            .fullName(updatePersonDto.getFullName())
            .birthday(Date.from(Instant.parse(updatePersonDto.getBirthday())))
            .currentAddress(updatePersonDto.getCurrentAddress())
            .job(updatePersonDto.getJob())
            .sex(SexUtils.getSex(updatePersonDto.getSex()))
            .build();
        person.setInfo(peopleInfo);
        
        People savedPeople = peopleRepository.save(person);
        
        peopleSearchRepository.findById(id).map(peopleSearch -> {
            peopleSearch.setCurrentAddress(savedPeople.getInfo().getCurrentAddress());
            peopleSearch.setNote(savedPeople.getNote());
            peopleSearch.setBirthday(savedPeople.getInfo().getBirthday());
            peopleSearch.setSex(savedPeople.getInfo().getSex());
            peopleSearch.setFullName(savedPeople.getInfo().getFullName());
            peopleSearch.setJob(savedPeople.getInfo().getJob());
            peopleSearch.setPassportNumber(savedPeople.getPassportNumber());
            
            return peopleSearchRepository.save(peopleSearch);
        }).subscribe(Mono::subscribe);
        
        return savedPeople;
    }
}

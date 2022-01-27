package com.company.qldp.peopleservice.domain.service;

import com.company.qldp.domain.IDCard;
import com.company.qldp.domain.People;
import com.company.qldp.elasticsearchservice.domain.entity.IDCardSearch;
import com.company.qldp.elasticsearchservice.domain.entity.PeopleSearch;
import com.company.qldp.elasticsearchservice.domain.repository.IDCardSearchRepository;
import com.company.qldp.elasticsearchservice.domain.repository.PeopleSearchRepository;
import com.company.qldp.peopleservice.domain.dto.IDCardDto;
import com.company.qldp.peopleservice.domain.exception.IDCardNotFoundException;
import com.company.qldp.peopleservice.domain.exception.IDCardNumberAlreadyExistException;
import com.company.qldp.peopleservice.domain.exception.PersonAlreadyHasIDCardException;
import com.company.qldp.peopleservice.domain.exception.PersonNotFoundException;
import com.company.qldp.peopleservice.domain.repository.IDCardRepository;
import com.company.qldp.peopleservice.domain.repository.PeopleRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Date;

@Service
public class IDCardService {
    
    private IDCardRepository idCardRepository;
    private IDCardSearchRepository idCardSearchRepository;
    private PeopleRepository peopleRepository;
    private PeopleSearchRepository peopleSearchRepository;
    
    public IDCardService(
        IDCardRepository idCardRepository,
        IDCardSearchRepository idCardSearchRepository,
        PeopleRepository peopleRepository,
        PeopleSearchRepository peopleSearchRepository
    ) {
        this.idCardRepository = idCardRepository;
        this.idCardSearchRepository = idCardSearchRepository;
        this.peopleRepository = peopleRepository;
        this.peopleSearchRepository = peopleSearchRepository;
    }
    
    public IDCard createPeopleIDCard(Integer id, IDCardDto idCardDto) {
        People people = peopleRepository.findById(id)
            .orElseThrow(PersonNotFoundException::new);
    
        IDCard findCard = idCardRepository.findByPerson_Id(id);
        if (findCard != null) {
            throw new PersonAlreadyHasIDCardException();
        }
        
        Mono<PeopleSearch> peopleSearchMono = peopleSearchRepository.findById(id);
        
        if (idCardNumberExists(idCardDto.getIdCardNumber())) {
            throw new IDCardNumberAlreadyExistException();
        }
        
        IDCard idCard = IDCard.builder()
            .person(people)
            .idCardNumber(idCardDto.getIdCardNumber())
            .issuedDay(Date.from(Instant.parse(idCardDto.getIssuedDay())))
            .issuedPlace(idCardDto.getIssuedPlace())
            .build();
        
        IDCard savedIDCard = idCardRepository.save(idCard);
        
        peopleSearchMono.map(peopleSearch -> {
            IDCardSearch idCardSearch = IDCardSearch.builder()
                .id(savedIDCard.getId())
                .peopleSearch(peopleSearch)
                .idCardNumber(idCardDto.getIdCardNumber())
                .issuedDay(savedIDCard.getIssuedDay())
                .issuedPlace(idCardDto.getIssuedPlace())
                .build();
            
            return idCardSearchRepository.save(idCardSearch);
        }).subscribe(Mono::subscribe);
        
        return savedIDCard;
    }
    
    private boolean idCardNumberExists(String idCardNumber) {
        return idCardRepository.findByIdCardNumber(idCardNumber) != null;
    }
    
    public IDCard getIDCardByPeopleId(Integer peopleId) {
        IDCard idCard = idCardRepository.findByPerson_Id(peopleId);
        
        if (idCard == null) {
            throw new IDCardNotFoundException();
        }
        
        return idCard;
    }
}

package com.company.qldp.peopleservice.domain.service;

import com.company.qldp.common.DateInterval;
import com.company.qldp.domain.People;
import com.company.qldp.domain.Story;
import com.company.qldp.peopleservice.domain.dto.StoryDto;
import com.company.qldp.peopleservice.domain.exception.PersonNotFoundException;
import com.company.qldp.peopleservice.domain.repository.PeopleRepository;
import com.company.qldp.peopleservice.domain.repository.StoryRepository;
import com.company.qldp.common.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoryService {
    
    private StoryRepository storyRepository;
    private PeopleRepository peopleRepository;
    
    @Autowired
    public StoryService(
        StoryRepository storyRepository,
        PeopleRepository peopleRepository
    ) {
        this.storyRepository = storyRepository;
        this.peopleRepository = peopleRepository;
    }
    
    public Story createStory(Integer id, StoryDto storyDto) {
        People person = peopleRepository.findById(id)
            .orElseThrow(PersonNotFoundException::new);
    
        DateInterval interval = DateUtils.createDateInterval(
            storyDto.getFromDate(),
            storyDto.getToDate()
        );
        
        Story story = Story.builder()
            .person(person)
            .interval(interval)
            .address(storyDto.getAddress())
            .job(storyDto.getJob())
            .workplace(storyDto.getWorkplace())
            .build();
        
        return storyRepository.save(story);
    }
    
    public List<Story> getStoriesByPeopleId(Integer peopleId) {
        return storyRepository.findAllByPerson_Id(peopleId);
    }
    
    public Story getStoryById(Integer peopleId, Integer storyId) {
        return storyRepository.findByPerson_IdAndId(peopleId, storyId);
    }
}

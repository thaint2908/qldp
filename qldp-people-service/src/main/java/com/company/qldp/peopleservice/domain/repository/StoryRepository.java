package com.company.qldp.peopleservice.domain.repository;

import com.company.qldp.domain.Story;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoryRepository extends JpaRepository<Story, Integer> {

    List<Story> findAllByPerson_Id(Integer id);
    
    Story findByPerson_IdAndId(Integer peopleId, Integer storyId);
}

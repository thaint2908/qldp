package com.company.qldp.householdservice.domain.service;

import com.company.qldp.domain.HouseholdHistory;
import com.company.qldp.householdservice.api.repository.HouseholdHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HouseholdHistoryService {
    
    private HouseholdHistoryRepository householdHistoryRepository;
    
    @Autowired
    public HouseholdHistoryService(HouseholdHistoryRepository householdHistoryRepository) {
        this.householdHistoryRepository = householdHistoryRepository;
    }
    
    @Transactional
    public List<HouseholdHistory> getHouseholdHistories(Integer id) {
        List<HouseholdHistory> householdHistories = householdHistoryRepository.findAllByHousehold_Id(id);
        householdHistories.forEach(this::getHouseholdHistoryInfo);
        
        return householdHistories;
    }
    
    @Transactional
    public HouseholdHistory getHouseholdHistory(Integer householdId, Integer id) {
        HouseholdHistory householdHistory = householdHistoryRepository.findByHousehold_IdAndId(householdId, id);
        getHouseholdHistoryInfo(householdHistory);
        
        return householdHistory;
    }
    
    private void getHouseholdHistoryInfo(HouseholdHistory householdHistory) {
        if (householdHistory.getAffectPerson() != null) {
            householdHistory.getAffectPerson().hashCode();
        }
        if (householdHistory.getNewHousehold() != null) {
            householdHistory.getNewHousehold().hashCode();
        }
    }
}

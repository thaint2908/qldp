package com.company.qldp.peopleservice.domain.dto;

import com.company.qldp.common.Sex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePersonDto {
    
    private String fullName;
    private String birthday;
    private String sex;
    private String job;
    private String currentAddress;
    private String alias;
    private String birthPlace;
    private String domicile;
    private String nation;
    private String religion;
    private String nationality;
    private String passportNumber;
    private String permanentAddress;
    private String academicLevel;
    private String qualification;
    private String ethnicLanguage;
    private String languageLevel;
    private String workplace;
    private String criminalRecord;
    private String note;
}

package com.company.qldp.peopleservice.domain.dto;

import com.company.qldp.common.util.SexUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

import com.company.qldp.common.Sex;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonDto {
    
    @NotNull
    @Length(min = 1)
    private String fullName;
    
    @NotNull
    @Length(min = 1)
    private String birthday;
    
    @NotNull
    @Length(min = 1)
    private String sex;
    
    private String job;
    
    @NotNull
    @Length(min = 1)
    private String currentAddress;
    
    private String alias;
    
    @NotNull
    @Length(min = 1)
    private String birthPlace;
    
    @NotNull
    @Length(min = 1)
    private String domicile;
    
    @NotNull
    @Length(min = 1)
    private String nation;
    
    @NotNull
    @Length(min = 1)
    private String religion;
    
    @NotNull
    @Length(min = 1)
    private String nationality;
    
    private String passportNumber;
    
    @NotNull
    @Length(min = 1)
    private String permanentAddress;
    
    private String academicLevel;
    
    private String qualification;
    
    private String ethnicLanguage;
    
    private String languageLevel;
    
    private String workplace;
    
    private String criminalRecord;
    
    @NotNull
    @Length(min = 1)
    private String createdManagerUsername;
    
    @NotNull
    @Length(min = 1)
    private String createdDate;
    
    private String note;
    
    public Sex getSex() {
        return SexUtils.getSex(sex);
    }
}

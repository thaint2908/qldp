package com.company.qldp.domain;

import lombok.*;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@Builder
@Embeddable
@Access(AccessType.FIELD)
public class  PersonalEducationInfo {
    
    @Column(name = "academic_level")
    private String academicLevel;
    
    @Column(name = "qualification")
    private String qualification;
    
    @Column(name = "ethnic_language")
    private String ethnicLanguage;
    
    @Column(name = "language_level")
    private String languageLevel;
    
    @Column(name = "workplace")
    private String workplace;
    
    @Column(name = "criminal_record")
    private String criminalRecord;
}

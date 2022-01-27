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
public class PersonalExtraInfo {
    
    @Column(name = "domicile")
    private String domicile;
    
    @Column(name = "nation")
    private String nation;
    
    @Column(name = "religion")
    private String religion;
    
    @Column(name = "nationality")
    private String nationality;
}

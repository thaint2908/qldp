package com.company.qldp.common;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@Builder
@Embeddable
@Access(AccessType.FIELD)
public class PeopleInfo {
    
    @Column(name = "full_name")
    private String fullName;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "birthday")
    private Date birthday;
    
    @Column(name = "sex")
    @Enumerated(EnumType.STRING)
    private Sex sex;
    
    @Column(name = "job")
    private String job;
    
    @Column(name = "current_address")
    private String currentAddress;
}

package com.company.qldp.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@Builder
@Embeddable
@Access(AccessType.FIELD)
public class PersonalMobilization {

    @Temporal(TemporalType.DATE)
    @Column(name = "arrival_date")
    private Date arrivalDate;
    
    @Column(name = "arrival_reason")
    private String arrivalReason;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "leave_date")
    private Date leaveDate;
    
    @Column(name = "leave_reason")
    private String leaveReason;
    
    @Column(name = "new_address")
    private String newAddress;
}

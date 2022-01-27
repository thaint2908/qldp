package com.company.qldp.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "household")
@Access(AccessType.FIELD)
public class Household {
    
    @Id
    @GeneratedValue
    private Integer id;
    
    @Column(name = "household_code")
    private String householdCode;
    
    @OneToOne(
        optional = false,
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "host_id")
    private People host;
    
    @Column(name = "area_code")
    private String areaCode;
    
    @Column(name = "address")
    private String address;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "created_day")
    private Date createdDay;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "leave_day")
    private Date leaveDay;
    
    @Column(name = "leave_reason")
    private String leaveReason;
    
    @ManyToOne(
        optional = false,
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "performer_id")
    private User performer;
}

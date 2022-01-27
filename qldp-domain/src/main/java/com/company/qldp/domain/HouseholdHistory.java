package com.company.qldp.domain;

import com.company.qldp.common.Event;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@Builder
@Entity(name = "household_histories")
@Access(AccessType.FIELD)
public class HouseholdHistory {
    
    @Id
    @GeneratedValue
    private Integer id;
    
    @ManyToOne(
        optional = false,
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "household_id")
    @JsonIgnore
    private Household household;
    
    @Enumerated(EnumType.STRING)
    private Event event;
    
    @OneToOne(
        optional = false,
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "affect_person_id")
    private People affectPerson;
    
    @OneToOne(
        optional = false,
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "new_household_id")
    private Household newHousehold;
    
    @Temporal(TemporalType.DATE)
    private Date date;
}

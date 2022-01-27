package com.company.qldp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "corrections")
@Access(AccessType.FIELD)
public class Correction {
    
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
    
    @Column(name = "change_info")
    private String changeInfo;
    
    @Column(name = "change_from")
    private String changeFrom;
    
    @Column(name = "change_to")
    private String changeTo;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "change_day")
    private Date changeDay;
    
    @ManyToOne(
        optional = false,
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "performer_id")
    private User performer;
}

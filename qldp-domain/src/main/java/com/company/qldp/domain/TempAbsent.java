package com.company.qldp.domain;

import com.company.qldp.common.DateInterval;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "temp_absent")
@Access(AccessType.FIELD)
public class TempAbsent {
    
    @Id
    @GeneratedValue
    private Integer id;
    
    @ManyToOne(
        optional = false,
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "person_id")
    private People person;
    
    @Column(name = "temp_absent_code")
    private String tempAbsentCode;
    
    @Column(name = "temp_residence_place")
    private String tempResidencePlace;
    
    @Embedded
    private DateInterval interval;
    
    @Column(name = "reason")
    private String reason;
}

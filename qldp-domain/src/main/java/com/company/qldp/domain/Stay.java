package com.company.qldp.domain;

import com.company.qldp.common.DateInterval;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "stay")
@Access(AccessType.FIELD)
public class Stay {

    @Id
    @GeneratedValue
    private Integer id;
    
    @ManyToOne(
        optional = false,
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "person_id")
    private People person;
    
    @Column(name = "temp_residence_code")
    private String tempResidenceCode;
    
    @Column(name = "phone_number")
    private String phoneNumber;
    
    @Embedded
    private DateInterval interval;
    
    @Column(name = "reason")
    private String reason;
}

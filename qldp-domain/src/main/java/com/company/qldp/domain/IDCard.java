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
@Table(name = "id_card")
@Access(AccessType.FIELD)
public class IDCard {

    @Id
    @GeneratedValue
    private Integer id;
    
    @OneToOne(
        optional = false,
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "person_id")
    @JsonIgnore
    private People person;
    
    @Column(name = "id_card_number", nullable = false, length = 12)
    private String idCardNumber;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "issued_day", nullable = false)
    private Date issuedDay;
    
    @Column(name = "issued_place", nullable = false)
    private String issuedPlace;
}

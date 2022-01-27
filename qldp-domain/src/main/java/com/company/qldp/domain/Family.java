package com.company.qldp.domain;

import com.company.qldp.common.PeopleInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "family")
@Access(AccessType.FIELD)
public class Family {
    
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
    
    @Embedded
    private PeopleInfo info;
    
    @Column(name = "person_relation")
    private String personRelation;
}

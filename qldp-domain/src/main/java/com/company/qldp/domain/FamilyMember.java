package com.company.qldp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "family_member")
@Access(AccessType.FIELD)
@IdClass(HouseholdPeopleId.class)
public class FamilyMember {

    @Id
    @OneToOne(
        optional = false,
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private People person;

    @Id
    @ManyToOne(
        optional = false,
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "household_id", referencedColumnName = "id")
    @JsonIgnore
    private Household household;

    @Column(name = "host_relation")
    private String hostRelation;
}

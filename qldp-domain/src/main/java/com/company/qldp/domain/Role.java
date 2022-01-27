package com.company.qldp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@Entity
@Table(name = "roles")
@Access(AccessType.FIELD)
public class Role {

    @Id
    @GeneratedValue
    private Integer id;
    
    private String name;
    
    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Collection<User> users = new ArrayList<>();
}

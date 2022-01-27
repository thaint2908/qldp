package com.company.qldp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
@Access(AccessType.FIELD)
public class User {
    
    @Id
    @GeneratedValue
    private Integer id;
    
    @Column(name = "username")
    private String username;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "keycloak_uid")
    @JsonIgnore
    private String keycloakUid;
    
    @ManyToMany
    @JoinTable(
        name = "users_roles",
        joinColumns = @JoinColumn(
            name = "user_id",
            referencedColumnName = "id"
        ),
        inverseJoinColumns = @JoinColumn(
            name = "role_id",
            referencedColumnName = "id"
        )
    )
    @JsonIgnore
    private Collection<Role> roles = new ArrayList<>();
}

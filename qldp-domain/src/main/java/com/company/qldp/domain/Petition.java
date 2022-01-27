package com.company.qldp.domain;

import com.company.qldp.common.ContentBody;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@Builder
@Entity(name = "petitions")
@Access(AccessType.FIELD)
public class Petition {
    
    @Id
    @GeneratedValue
    private Integer id;
    
    @Embedded
    private ContentBody body;
    
    @ManyToOne(
        optional = false,
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "sender_id")
    private User sender;
}

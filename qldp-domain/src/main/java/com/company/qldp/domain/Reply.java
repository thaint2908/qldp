package com.company.qldp.domain;

import com.company.qldp.common.ContentBody;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@Builder
@Entity(name = "replies")
@Access(AccessType.FIELD)
public class Reply {
    
    @Id
    @GeneratedValue
    private Integer id;
    
    @Embedded
    private ContentBody body;
    
    @ManyToOne(
        optional = false,
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "replier_id")
    private User replier;
    
    @OneToOne(
        optional = false,
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "petition_id")
    private Petition petition;
}

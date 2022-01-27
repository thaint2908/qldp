package com.company.qldp.common;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@Builder
@Embeddable
@Access(AccessType.FIELD)
public class ContentBody {
    
    private String subject;
    
    private String content;
    
    @Temporal(TemporalType.DATE)
    private Date date;
    
    @Enumerated(EnumType.STRING)
    private Status status;
}

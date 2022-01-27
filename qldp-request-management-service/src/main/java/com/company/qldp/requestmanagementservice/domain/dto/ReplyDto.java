package com.company.qldp.requestmanagementservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyDto {
    
    @NotNull
    @Length(min = 1)
    private String subject;
    
    @NotNull
    @Length(min = 1)
    private String content;
    
    @NotNull
    @Length(min = 1)
    private String date;
    
    @NotNull
    private Integer toPetition;
}

package com.company.qldp.peopleservice.domain.dto;

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
public class StoryDto {
    
    @NotNull
    @Length(min = 1)
    private String fromDate;
    
    @NotNull
    @Length(min = 1)
    private String toDate;
    
    @NotNull
    @Length(min = 1)
    private String address;
    
    @NotNull
    @Length(min = 1)
    private String job;
    
    @NotNull
    @Length(min = 1)
    private String workplace;
}

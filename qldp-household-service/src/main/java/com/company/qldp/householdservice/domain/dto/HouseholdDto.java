package com.company.qldp.householdservice.domain.dto;

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
public class HouseholdDto {
    
    @NotNull
    private Integer hostPersonId;
    
    @NotNull
    @Length(min = 1)
    private String areaCode;
    
    @NotNull
    @Length(min = 1)
    private String address;
    
    @NotNull
    @Length(min = 1)
    private String createdDay;
    
    @NotNull
    @Length(min = 1)
    private String performerName;
}

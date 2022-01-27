package com.company.qldp.householdservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeparateHouseholdDto {
    
    @NotNull
    private Integer hostId;
    
    @NotNull
    @Length(min = 1)
    private String newAddress;
    
    @NotNull
    @Length(min = 1)
    private String areaCode;
    
    @NotNull
    @Length(min = 1)
    private String createdDay;
    
    @NotNull
    @Length(min = 1)
    private String performerName;
}

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
public class LeaveHouseholdDto {
    
    @NotNull
    @Length(min = 1)
    private String leaveDate;
    
    @NotNull
    @Length(min = 1)
    private String newAddress;
    
    private String leaveReason;
    
    @NotNull
    @Length(min = 1)
    private String performerName;
}

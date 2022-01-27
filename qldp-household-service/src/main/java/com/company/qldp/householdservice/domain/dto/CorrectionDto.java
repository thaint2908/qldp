package com.company.qldp.householdservice.domain.dto;

import com.company.qldp.householdservice.domain.validation.ValidChangeInfo;
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
public class CorrectionDto {
    
    @ValidChangeInfo
    private String changeInfo;
    
    @NotNull
    @Length(min = 1)
    private String changeFrom;
    
    @NotNull
    @Length(min = 1)
    private String changeTo;
    
    @NotNull
    @Length(min = 1)
    private String changeDate;
    
    @NotNull
    @Length(min = 1)
    private String performerName;
}

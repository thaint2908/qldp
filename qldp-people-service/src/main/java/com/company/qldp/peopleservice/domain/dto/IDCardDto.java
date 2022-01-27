package com.company.qldp.peopleservice.domain.dto;

import com.company.qldp.peopleservice.domain.validation.idcard.ValidIDCard;
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
public class IDCardDto {
    
    @ValidIDCard
    private String idCardNumber;
    
    @NotNull
    @Length(min = 1)
    private String issuedDay;
    
    @NotNull
    @Length(min = 1)
    private String issuedPlace;
}

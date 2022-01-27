package com.company.qldp.householdservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FamilyMemberDto {
    
    @NotNull
    @Size(min = 1)
    private List<Member> members = new ArrayList<>();
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Member {
        
        @NotNull
        private Integer id;
        
        @NotNull
        @Length(min = 1)
        String hostRelation;
    }
}

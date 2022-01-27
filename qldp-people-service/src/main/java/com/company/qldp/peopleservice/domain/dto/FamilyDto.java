package com.company.qldp.peopleservice.domain.dto;

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
public class FamilyDto {

    @NotNull
    @Size(min = 1)
    private List<MemberRelation> relations = new ArrayList<>();
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MemberRelation {
        
        @NotNull
        private Integer memberId;
        
        @NotNull
        @Length(min = 1)
        private String memberRelation;
    }
}

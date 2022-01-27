package com.company.qldp.elasticsearchservice.domain.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.hateoas.server.core.Relation;

import java.util.Date;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@Builder
@Document(indexName = "household")
@Setting(settingPath = "/elastic-setting.json")
@Relation(collectionRelation = "households")
public class HouseholdSearch {
    
    @Id
    private Integer id;
    
    @Field(name = "household_code")
    private String householdCode;
    
    @Field(type = FieldType.Nested, includeInParent = true)
    private PeopleSearch host;
    
    @MultiField(
        mainField = @Field(type = FieldType.Text, analyzer = "vn_folding"),
        otherFields = {
            @InnerField(suffix = "search", type = FieldType.Search_As_You_Type, analyzer = "vn_folding")
        }
    )
    private String address;
    
    @Field(name = "created_day", type = FieldType.Date, format = DateFormat.year_month_day)
    private Date createdDay;
}

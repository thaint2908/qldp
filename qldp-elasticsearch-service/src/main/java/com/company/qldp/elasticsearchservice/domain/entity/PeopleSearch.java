package com.company.qldp.elasticsearchservice.domain.entity;

import com.company.qldp.common.Sex;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.hateoas.server.core.Relation;

import java.util.Date;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@Builder
@Document(indexName = "people")
@Setting(settingPath = "/elastic-setting.json")
@Relation(collectionRelation = "people")
public class PeopleSearch {
    
    @Id
    private Integer id;
    
    @MultiField(
        mainField = @Field(name = "people_code", type = FieldType.Text),
        otherFields = {
            @InnerField(suffix = "keyword", type = FieldType.Keyword),
            @InnerField(suffix = "search", type = FieldType.Search_As_You_Type)
        }
    )
    private String peopleCode;
    
    @MultiField(
        mainField = @Field(name = "full_name", type = FieldType.Text, analyzer = "vn_folding"),
        otherFields = {
            @InnerField(suffix = "keyword", type = FieldType.Keyword),
            @InnerField(suffix = "search", type = FieldType.Search_As_You_Type, analyzer = "vn_folding")
        }
    )
    private String fullName;
    
    @Field(type = FieldType.Date, format = DateFormat.year_month_day)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthday;
    
    @Field(type = FieldType.Text)
    private Sex sex;
    
    private String job;
    
    @Field(name = "current_address")
    private String currentAddress;
    
    @Field(name = "passport_number")
    private String passportNumber;
    
    @Field(name = "arrival_date", type = FieldType.Date, format = DateFormat.year_month_day)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date arrivalDate;
    
    @Field(name = "leave_date", type = FieldType.Date, format = DateFormat.year_month_day)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date leaveDate;
    
    private String note;
    
    @Field(name = "live_status")
    private String liveStatus;
}

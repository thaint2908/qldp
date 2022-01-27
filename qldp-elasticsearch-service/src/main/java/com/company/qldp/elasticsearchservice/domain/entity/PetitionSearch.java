package com.company.qldp.elasticsearchservice.domain.entity;

import com.company.qldp.common.Status;
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
@Document(indexName = "petition")
@Setting(settingPath = "/elastic-setting.json")
@Relation(collectionRelation = "petitions")
public class PetitionSearch {
    
    @Id
    private Integer id;
    
    @MultiField(
        mainField = @Field(type = FieldType.Text, analyzer = "vn_folding"),
        otherFields = {
            @InnerField(suffix = "keyword", type = FieldType.Keyword),
            @InnerField(suffix = "search", type = FieldType.Search_As_You_Type, analyzer = "vn_folding")
        }
    )
    private String subject;
    
    @Field(type = FieldType.Date, format = DateFormat.year_month_day)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;
    
    @Field(type = FieldType.Text)
    private Status status;
    
    @MultiField(
        mainField = @Field(type = FieldType.Text, analyzer = "vn_folding"),
        otherFields = {
            @InnerField(suffix = "keyword", type = FieldType.Keyword),
            @InnerField(suffix = "search", type = FieldType.Search_As_You_Type, analyzer = "vn_folding")
        }
    )
    private String sender;
}

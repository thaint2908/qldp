package com.company.qldp.peopleservice.domain.repository.specification;

import com.company.qldp.common.util.DateUtils;
import com.company.qldp.domain.Death;
import com.company.qldp.peopleservice.domain.exception.DateParseException;
import org.springframework.data.jpa.domain.Specification;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.springframework.data.jpa.domain.Specification.where;

public class DeathSpecification {
    
    static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    public static Specification<Death> makeDateRangeSpecification(String dateRange) {
        String[] range = DateUtils.getDateRange(dateRange);
        String fromDate = range[0];
        String toDate = range[1];
        
        Specification<Death> spec = null;
        
        if (fromDate != null && toDate != null) {
            Specification<Death> fromSpec = where(hasDateFrom(fromDate));
            spec = fromSpec.and(hasDateTo(toDate));
        }
        
        return spec;
    }
    
    public static Specification<Death> hasDateFrom(String fromDateStr) {
        return (entity, cq, cb) -> {
            try {
                Date fromDate = dateFormat.parse(fromDateStr);
                
                return cb.greaterThan(entity.get("deathDay"), fromDate);
            } catch (ParseException e) {
                throw new DateParseException(e.getMessage());
            }
        };
    }
    
    public static <T> Specification<T> hasDateTo(String toDateStr) {
        return (entity, cq, cb) -> {
            try {
                Date toDate = dateFormat.parse(toDateStr);
                
                return cb.lessThan(entity.get("deathDay"), toDate);
            } catch (ParseException e) {
                throw new DateParseException(e.getMessage());
            }
        };
    }
}

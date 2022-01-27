package com.company.qldp.peopleservice.domain.repository.specification;

import com.company.qldp.common.util.DateUtils;
import com.company.qldp.peopleservice.domain.exception.DateParseException;
import org.springframework.data.jpa.domain.Specification;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.springframework.data.jpa.domain.Specification.*;

public class GenericSpecification {
    
    static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    public static <T> Specification<T> makeDateRangeSpecification(String dateRange) {
        String[] range = DateUtils.getDateRange(dateRange);
        String fromDate = range[0];
        String toDate = range[1];
    
        Specification<T> spec = null;
    
        if (dateRange != null) {
            Specification<T> fromSpec = where(hasDateFrom(fromDate));
            spec = fromSpec.and(hasDateTo(toDate));
        }
        
        return spec;
    }
    
    public static <T> Specification<T> hasDateFrom(String fromDateStr) {
        return (entity, cq, cb) -> {
            try {
                Date fromDate = dateFormat.parse(fromDateStr);
                
                return cb.greaterThan(entity.get("interval").get("from"), fromDate);
            } catch (ParseException e) {
                throw new DateParseException(e.getMessage());
            }
        };
    }
    
    public static <T> Specification<T> hasDateTo(String toDateStr) {
        return (entity, cq, cb) -> {
            try {
                if (toDateStr == null) {
                    return cb.isNull(entity.get("interval").get("to"));
                }
                
                Date toDate = dateFormat.parse(toDateStr);
        
                return cb.lessThan(entity.get("interval").get("from"), toDate);
            } catch (ParseException e) {
                throw new DateParseException(e.getMessage());
            }
        };
    }
}

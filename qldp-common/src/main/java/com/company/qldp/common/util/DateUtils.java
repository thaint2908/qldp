package com.company.qldp.common.util;

import com.company.qldp.common.DateInterval;
import com.company.qldp.common.exception.InvalidDateRangeException;

import java.time.Instant;
import java.util.*;

public class DateUtils {
    
    public static DateInterval createDateInterval(String fromDate, String toDate) {
        Date from = Date.from(Instant.parse(fromDate));
        
        if (toDate.isEmpty()) {
            return DateInterval.builder()
                .from(from).to(null)
                .build();
        } else {
            Date to = Date.from(Instant.parse(toDate));
            
            if (to.before(from)) {
                throw new InvalidDateRangeException();
            }
    
            return DateInterval.builder()
                .from(from).to(to)
                .build();
        }
    }
    
    public static String[] getDateRange(String dateRange) {
        String fromDate = null;
        String toDate = null;
    
        if (dateRange != null) {
            String[] dateRangeArr = dateRange.split(",");
            fromDate = dateRangeArr[0];
    
            if (dateRangeArr.length > 1) {
                toDate = dateRangeArr[1];
            }
        }
        
        return new String[]{fromDate, toDate};
    }
    
    public static int getBirthYear(Integer age) {
        return Calendar.getInstance().get(Calendar.YEAR) - age;
    }
}

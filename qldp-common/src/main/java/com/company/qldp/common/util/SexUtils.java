package com.company.qldp.common.util;

import com.company.qldp.common.Sex;

public class SexUtils {
    
    public static Sex getSex(String sex) {
        return switch (sex) {
            case "male" -> Sex.MALE;
            case "female" -> Sex.FEMALE;
            case "other" -> Sex.OTHER;
            default -> throw new RuntimeException("Sex not found: " + sex);
        };
    }
}

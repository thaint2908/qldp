package com.company.qldp.householdservice.domain.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ChangeInfoConstraintValidator implements ConstraintValidator<ValidChangeInfo, String> {
    
    @Override
    public void initialize(ValidChangeInfo constraintAnnotation) {
    
    }
    
    @Override
    public boolean isValid(String changeInfo, ConstraintValidatorContext context) {
        return changeInfo.equals("HOST") || changeInfo.equals("ADDRESS");
    }
}

package com.company.qldp.peopleservice.domain.validation.peoplecode;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PeopleCodeConstraintValidator implements ConstraintValidator<ValidPeopleCode, String> {
    
    @Override
    public void initialize(ValidPeopleCode constraintAnnotation) {
    
    }
    
    @Override
    public boolean isValid(String peopleCode, ConstraintValidatorContext context) {
        return peopleCode.length() == 8;
    }
}

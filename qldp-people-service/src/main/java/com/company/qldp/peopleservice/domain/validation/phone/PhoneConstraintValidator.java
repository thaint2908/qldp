package com.company.qldp.peopleservice.domain.validation.phone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PhoneConstraintValidator implements ConstraintValidator<ValidPhoneNumber, String> {
    
    @Override
    public void initialize(ValidPhoneNumber constraintAnnotation) {
    
    }
    
    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        return phoneNumber.matches("(03|07|08|09|01[2|6|8|9])+([0-9]{8})\\b");
    }
}

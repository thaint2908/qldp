package com.company.qldp.peopleservice.domain.validation.phone;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneConstraintValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPhoneNumber {
    
    String message() default "Invalid phone number";
    
    Class<?>[] groups() default {};
    
    Class<?>[] payload() default {};
}

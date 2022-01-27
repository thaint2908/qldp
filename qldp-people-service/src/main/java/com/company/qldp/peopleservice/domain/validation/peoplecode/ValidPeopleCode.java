package com.company.qldp.peopleservice.domain.validation.peoplecode;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PeopleCodeConstraintValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPeopleCode {
    
    String message() default "Invalid people code";
    
    Class<?>[] groups() default {};
    
    Class<?>[] payload() default {};
}

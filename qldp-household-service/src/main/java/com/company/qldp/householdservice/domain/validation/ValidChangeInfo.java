package com.company.qldp.householdservice.domain.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ChangeInfoConstraintValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidChangeInfo {
    
    String message() default "Invalid change info";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}

package com.company.qldp.peopleservice.domain.validation.idcard;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IDCardConstraintValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidIDCard {
    
    String message() default "Invalid ID Card number";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}

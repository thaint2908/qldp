package com.company.qldp.userservice.domain.validation.password;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordConstraintValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    
    String message() default "Invalid password";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}

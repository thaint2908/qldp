package com.company.qldp.userservice.domain.validation.password;

import com.company.qldp.userservice.domain.exception.NullPasswordException;
import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {
    
    @Override
    public void initialize(ValidPassword constraintAnnotation) {
    
    }
    
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                new LengthRule(8, 30),
                new CharacterRule(EnglishCharacterData.UpperCase),
                new CharacterRule(EnglishCharacterData.Digit),
                new CharacterRule(EnglishCharacterData.Special),
                new IllegalSequenceRule(EnglishSequenceData.Numerical),
                new IllegalSequenceRule(EnglishSequenceData.Alphabetical),
                new IllegalSequenceRule(EnglishSequenceData.USQwerty),
                new WhitespaceRule())
        );
        try {
            RuleResult result = validator.validate(new PasswordData(password));
    
            if (result.isValid()) {
                return true;
            }
    
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                String.join(",", validator.getMessages(result))
            )
                .addConstraintViolation();
    
            return false;
        } catch (Exception e) {
            throw new NullPasswordException();
        }
    }
}

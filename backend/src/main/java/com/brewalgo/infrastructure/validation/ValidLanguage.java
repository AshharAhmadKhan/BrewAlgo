package com.brewalgo.infrastructure.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LanguageValidator.class)
@Documented
public @interface ValidLanguage {
    
    String message() default "Invalid language. Only JAVA and PYTHON are supported";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}

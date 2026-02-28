package com.brewalgo.infrastructure.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class LanguageValidator implements ConstraintValidator<ValidLanguage, String> {
    
    private static final List<String> VALID_LANGUAGES = Arrays.asList("JAVA", "PYTHON");
    
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return false;
        }
        return VALID_LANGUAGES.contains(value.toUpperCase());
    }
}

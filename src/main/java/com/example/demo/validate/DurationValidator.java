package com.example.demo.validate;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Duration;

public class DurationValidator implements ConstraintValidator<DurationValidation, Duration> {

    @Override
    public void initialize(DurationValidation constraintAnnotation) {
    }

    @Override
    public boolean isValid(Duration value, ConstraintValidatorContext context) {

        return !value.isNegative();
    }
}

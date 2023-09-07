package com.example.demo.validate;

import com.example.demo.validate.DateValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class DateValidator implements ConstraintValidator<DateValidation, LocalDate> {

    @Override
    public void initialize(DateValidation constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        // Реализуйте вашу логику валидации
        LocalDate criticalDate = LocalDate.of(1895,12,28);
        return value.isAfter(criticalDate);
    }
}

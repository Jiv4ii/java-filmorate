package ru.yandex.practicum.filmorate.validate;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Duration;

public class DurationValidator implements ConstraintValidator<DurationValidation, Duration> {

    @Override
    public void initialize(DurationValidation constraintAnnotation) {
    }

    @Override
    public boolean isValid(Duration value, ConstraintValidatorContext context) {
        if (value == null){
            return false;
        }

        return !value.isNegative();
    }
}

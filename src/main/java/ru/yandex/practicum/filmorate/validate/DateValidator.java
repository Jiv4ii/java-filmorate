package ru.yandex.practicum.filmorate.validate;



import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DateValidator implements ConstraintValidator<DateValidation, LocalDate> {

    @Override
    public void initialize(DateValidation constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        // Реализуйте вашу логику валидации
        LocalDate criticalDate = LocalDate.of(1895, 12, 28);
        return value.isAfter(criticalDate);
    }
}

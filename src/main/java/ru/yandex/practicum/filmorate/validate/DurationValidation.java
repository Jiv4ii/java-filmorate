package ru.yandex.practicum.filmorate.validate;




import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DurationValidator.class)
public @interface DurationValidation {
    String message() default "Неверное значение";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}


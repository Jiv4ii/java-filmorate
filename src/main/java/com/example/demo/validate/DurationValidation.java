package com.example.demo.validate;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DurationValidator.class)
public @interface DurationValidation {
    String message() default "Неверное значение";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}


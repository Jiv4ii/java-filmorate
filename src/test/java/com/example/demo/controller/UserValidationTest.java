package com.example.demo.controller;


import com.example.demo.model.User;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserValidationTest {
    private ValidatorFactory validatorFactory;
    private Validator validator;
    private User user;

    @BeforeEach
    public void setUp() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
        user = new User();
        user.setId(1)
                .setEmail("Email@mail.mail")
                .setLogin("sher")
                .setName("faer")
                .setBirthday(LocalDate.of(2000,12,12));
    }

    @Test
    public void testValidFilm() {
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(0, violations.size(), "Поля фильма переданы неверно");

    }

    @Test
    public void testInvalidEmail() {
        user.setEmail("bademail");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        List<String> list = violations.stream().map(v -> v.getMessage()).collect(Collectors.toList());

        assertEquals(1, violations.size(), "Создан Пользователь с некорректным email");
        assertEquals("Неправильный формат", list.get(0));

        user.setEmail(null);
        Set<ConstraintViolation<User>> violations1 = validator.validate(user);
        List<String> list1 = violations1.stream().map(v -> v.getMessage()).collect(Collectors.toList());

        assertEquals(1, violations1.size(), "Создан Пользователь с некорректным email");
        assertEquals("Поле Email не может быть пустым", list1.get(0));


    }

    @Test
    public void testInvalidLogin() {
        user.setLogin(null);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        List<String> list = violations.stream().map(v -> v.getMessage()).collect(Collectors.toList());

        assertEquals(1, violations.size(), "Создан обьект с некорректным описанием");
        assertEquals("Поле login не может быть пустым", list.get(0));

        user.setLogin("log in");

        Set<ConstraintViolation<User>> violations1 = validator.validate(user);
        List<String> list1 = violations1.stream().map(v -> v.getMessage()).collect(Collectors.toList());

        assertEquals(1, violations1.size(), "Создан обьект с некорректным описанием");
        assertEquals("Поле login не может содержать пробелы", list1.get(0));

    }



    @Test
    public void testInvalidFilmDuration() {
        user.setBirthday(LocalDate.of(2100,12,12));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        List<String> list = violations.stream().map(v -> v.getMessage()).collect(Collectors.toList());

        assertEquals(1, violations.size(), "Создан обьект с длительностью");
        assertEquals("Дата рождения не может быть в будущем", list.get(0));
    }




}






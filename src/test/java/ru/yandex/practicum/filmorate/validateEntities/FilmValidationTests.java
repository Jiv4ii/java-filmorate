package ru.yandex.practicum.filmorate.validateEntities;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.Duration;
import java.time.LocalDate;

import java.util.List;

import java.util.Set;
import java.util.stream.Collectors;


public class FilmValidationTests {
    private ValidatorFactory validatorFactory;
    private Validator validator;
    private Film film;

    @BeforeEach
    public void setUp() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
        film = new Film();
        film.setId(1)
                .setName("Name")
                .setDescription("Описание")
                .setDate(LocalDate.of(1950, 12, 12))
                .setDuration(Duration.ofHours(2));
    }

    @Test
    public void testValidFilm() {
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        Assertions.assertEquals(0, violations.size(), "Поля фильма переданы неверно");

    }

    @Test
    public void testInvalidFilmName() {
        film.setName(null);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        List<String> list = violations.stream().map(v -> v.getMessage()).collect(Collectors.toList());

        Assertions.assertEquals(1, violations.size(), "Создан обьект с некорректным именем");
        Assertions.assertEquals("название не может быть пустым", list.get(0));

    }

    @Test
    public void testInvalidFilmDescription() {
        film.setDescription("ll of the annotations used in the example are standard JSR annotations:\n" +
                "\n" +
                "@NotNull validates that the annotated property value is not null.\n" +
                "@AssertTrue validates that the annotated property value is true.\n" +
                "@Size validates that the annotated property value has a size between the attributes min and max; can be applied to String, Collection, Map, and array properties.\n" +
                "@Min validates that the annotated property has a value no smaller than the value attribute.\n" +
                "@Max validates that the annotated property has a value no larger than the value attribute.\n" +
                "@Email validates that the annotated property is a valid email address.\n" +
                "Some annotations accept additional attributes, but the message attribute is common to all of them. This is the message that will usually be rendered when the value of the respective property fails validation.\n" +
                "\n" +
                "And some additional annotations that can be found in the JSR:\n" +
                "\n" +
                "@NotEmpty validates that the property is not null or empty; can be applied to String, Collection, Map or Array values.\n" +
                "@NotBlank can be applied only to text values and validates that the property is not null or whitespace.\n" +
                "@Positive and @PositiveOrZero apply to numeric values and validate that they are strictly positive, or positive including 0.\n" +
                "@Negative and @NegativeOrZero apply to numeric values and validate that they are strictly negative, or negative including 0.\n" +
                "@Past and @PastOrPresent validate that a date value is in the past or the past including the present; can be applied to date types including those added in Java 8.\n" +
                "@Future and @FutureOrPresent validate that a date value is in the future, or in the future including the present.");
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        List<String> list = violations.stream().map(v -> v.getMessage()).collect(Collectors.toList());

        Assertions.assertEquals(1, violations.size(), "Создан обьект с некорректным описанием");
        Assertions.assertEquals("максимальная длина описания — 200 символов", list.get(0));

    }

    @Test
    public void testInvalidFilmDate() {
        film.setDate(LocalDate.of(1800, 12, 21));
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        List<String> list = violations.stream().map(v -> v.getMessage()).collect(Collectors.toList());

        Assertions.assertEquals(1, violations.size(), "Создан обьект с некорректной датой выпуска");
        Assertions.assertEquals("дата релиза — не раньше 28 декабря 1895 года", list.get(0));
    }

    @Test
    public void testInvalidFilmDuration() {
        film.setDuration(Duration.ofHours(1).minusHours(2));
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        List<String> list = violations.stream().map(v -> v.getMessage()).collect(Collectors.toList());

        Assertions.assertEquals(1, violations.size(), "Создан обьект с длительностью");
        Assertions.assertEquals("продолжительность фильма должна быть положительной", list.get(0));
    }


}






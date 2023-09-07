package com.example.demo.model;

import com.example.demo.validate.DateValidation;
import com.example.demo.validate.DurationValidation;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;


import java.time.Duration;
import java.time.LocalDate;

@Data
@ToString
@Accessors(chain = true)
public class Film {
    private long id;
    @NotBlank(message = "название не может быть пустым")
    private String name;
    @Size(max = 200, message = "максимальная длина описания — 200 символов")
    private String description;
    @DateValidation(message = "дата релиза — не раньше 28 декабря 1895 года")
    private LocalDate date;
    @DurationValidation(message = "продолжительность фильма должна быть положительной")
    private Duration duration;

}


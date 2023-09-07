package com.example.demo.model;


import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@ToString
@Accessors(chain = true)
public class User {
    private long id;
    @NotBlank(message = "Поле Email не может быть пустым")
    @Email(message = "Неправильный формат")
    private String email;

    @NotBlank(message = "Поле login не может быть пустым")
    @Pattern(regexp = "^[^\\s]+$", message = "Поле login не может содержать пробелы")
    private String login;

    private String name;
    @Past(message = "Дата рождения не может быть в будущем")
    private LocalDate birthday;

}


package ru.yandex.practicum.filmorate.model;


import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
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


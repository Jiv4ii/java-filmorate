package ru.yandex.practicum.filmorate.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
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

    @JsonIgnore
    @ToString.Exclude
    private Set<User> friends = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(email, user.email) && Objects.equals(login, user.login) && Objects.equals(name, user.name) && Objects.equals(birthday, user.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, login, name, birthday);
    }
}


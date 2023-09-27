package ru.yandex.practicum.filmorate.model;


import lombok.Data;
import lombok.experimental.Accessors;
import ru.yandex.practicum.filmorate.validate.DateValidation;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Accessors(chain = true)
public class Film {
    private long id;
    @NotBlank(message = "название не может быть пустым")
    private String name;
    @Size(max = 200, message = "максимальная длина описания — 200 символов")
    private String description;
    @DateValidation(message = "дата релиза — не раньше 28 декабря 1895 года")
    private LocalDate releaseDate;
    @Positive(message = "продолжительность фильма должна быть положительной")
    private int duration;
    private Set<Long> likes = new HashSet<>();
    private String genre;
    private Rating rating;

}


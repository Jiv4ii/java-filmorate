package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

@Data
@Component
@Accessors(chain = true)
public class Genre {
    private Long id;
    private String name;
}

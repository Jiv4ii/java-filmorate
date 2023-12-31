package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Rating;

import java.util.List;

public interface MpaStorage {

    Rating getRating(long id);

    List<Rating> getRatings();
}

package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.List;

public interface FilmStorage {

    Film addFilm(Film film);

    void deleteFilm(long id);

    Film updateFilm(Film film);

    Collection<Film> getFilms();

    Film getFilm(long id);

    void addLike(long filmId, long userId);

    void removeLike(long filmId, long userId);

    List<Film> getMostLikedFilms(Integer count);


}

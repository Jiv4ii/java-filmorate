package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {

    Film addFilm(Film film);

    void deleteFilm(long id);

    Film updateFilm(Film film);

    Collection<Film> getFilms();

    Film getFilm(long id);

    long getId();

}

package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;

@Slf4j
@Service
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Autowired
    public FilmService(@Qualifier("filmDb") FilmStorage filmStorage, @Qualifier("db") UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public void addlike(Long filmId, Long userId) {
        if (filmStorage.getFilm(filmId) == null) {
            throw new IllegalArgumentException("фильм с Id - {}, не найден");
        }
        if (userStorage.getUser(userId) == null) {
            throw new IllegalArgumentException("Пользователь с Id - {}, не найден");
        }
        filmStorage.addLike(filmId, userId);
        log.info("Пользователь с id {} поставил лайк фильму с индексом {}", userId, filmId);
    }

    public void deleteLike(Long filmId, Long userId) {
        if (filmStorage.getFilm(filmId) == null) {
            throw new IllegalArgumentException("Фильм с Id - {}, не найден");
        }
        if (userStorage.getUser(userId) == null) {
            throw new IllegalArgumentException("Пользователь с Id - {}, не найден");
        }
        filmStorage.removeLike(filmId, userId);
        log.info("Пользователь с id {} убрал лайк фильму с индексом {}", userId, filmId);
    }

    public List<Film> getMostLikedFilms(Integer count) {
        if (count <= 0) {
            throw new IllegalArgumentException("Поле <count> должно быть положительным");
        }
        return filmStorage.getMostLikedFilms(count);
    }

    public Film createFilm(Film film) {
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }

    public Collection<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film getFilm(long id) {
        return filmStorage.getFilm(id);
    }

    public Integer getRatingId(long filmId) {
        if (filmStorage.getRatingId(filmId) == null) {
            throw new IllegalArgumentException("Фильм с Id - {}, не найден");
        }
        return filmStorage.getRatingId(filmId);
    }

    public String getRating(Integer id) {
        if (filmStorage.getRatingId(id) == null) {
            throw new IllegalArgumentException("Rating с Id - {}, не найден");
        }
        return filmStorage.getRating(id);
    }

    public Integer getGenreId(long filmId) {
        if (filmStorage.getRatingId(filmId) == null) {
            throw new IllegalArgumentException("Фильм с Id - {}, не найден");
        }
        return filmStorage.getGenreId(filmId);
    }

    public String getGenre(Integer id) {
        if (filmStorage.getRatingId(id) == null) {
            throw new IllegalArgumentException("Genre с Id - {}, не найден");
        }
        return filmStorage.getGenre(id);
    }
}

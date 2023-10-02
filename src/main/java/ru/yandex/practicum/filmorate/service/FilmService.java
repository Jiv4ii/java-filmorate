package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.List;

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
        log.info("Выведен список самых популярных фильмов");
        return filmStorage.getMostLikedFilms(count);
    }

    public Film createFilm(Film film) {
        log.info("Фильм создан");
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) {
        if (filmStorage.getFilm(film.getId()) == null) {
            throw new IllegalArgumentException("Фильм с Id - {}, не найден");
        }
        return filmStorage.updateFilm(film);
    }

    public Collection<Film> getFilms() {
        log.info("Выведен список всех фильмов");
        return filmStorage.getFilms();
    }

    public Film getFilm(long id) {
        if (filmStorage.getFilm(id) == null) {
            throw new IllegalArgumentException("Фильм с Id - {}, не найден");
        }
        log.info("Получен фильм id - {}",id);
        return filmStorage.getFilm(id);
    }


}




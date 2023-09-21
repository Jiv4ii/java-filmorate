package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public void addlike(Long filmId, Long userId) {
        if (filmId < 1 || filmId > filmStorage.getId()) {
            throw new IllegalArgumentException("Некорректный фильм Id");
        }
        if (userId < 1 || userId > userStorage.getId()) {
            throw new IllegalArgumentException("Некорректный пользовательский Id");
        }
        filmStorage.getFilm(filmId).getLikes().add(userId);
        log.info("Пользователь с id {} поставил лайк фильму с индексом {}", userId, filmId);
    }

    public void deleteLike(Long filmId, Long userId) {
        if (filmId < 1 || filmId > filmStorage.getId()) {
            throw new IllegalArgumentException("Некорректный фильм Id");
        }
        if (userId < 1 || userId > userStorage.getId()) {
            throw new IllegalArgumentException("Некорректный пользовательский Id");
        }
        filmStorage.getFilm(filmId).getLikes().remove(userId);
        log.info("Пользователь с id {} убрал лайк фильму с индексом {}", userId, filmId);
    }

    public List<Film> getMostLikedFilms(Integer count) {
        if (count < 0) {
            throw new IllegalArgumentException("Поле <count> должно быть положительным");
        }
        Collection<Film> films = new HashSet<>(filmStorage.getFilms());
        List<Film> sortedFilms = new ArrayList<>(films);
        Comparator<Film> comparator = Collections.reverseOrder(Comparator.comparingInt(obj -> obj.getLikes().size()));
        sortedFilms.sort(comparator);
        if (sortedFilms.size() > count) {
            sortedFilms = sortedFilms.subList(0, count);
        }
        return sortedFilms;
    }
}

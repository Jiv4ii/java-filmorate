package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {


    private final HashMap<Long, Film> filmMap = new HashMap<>();
    private long id = 1;

    @Override
    public Film addFilm(Film film) {
        film.setId(id++);
        filmMap.put(film.getId(), film);
        log.info("Success, created film - {}", film);
        return filmMap.get(film.getId());
    }

    @Override
    public void deleteFilm(long id) {
        if (!filmMap.containsKey(id)) {
            log.info("Error, film with id {} doesnt exist", id);
            return;
        }
        filmMap.remove(id);
        log.info("Success, film with id {} deleted", id);
    }

    @Override
    public Collection<Film> getFilms() {
        log.info("Success, films received");
        return filmMap.values();
    }

    @Override
    public Film updateFilm(Film film) {
        if (filmMap.containsKey(film.getId())) {
            filmMap.put(film.getId(), film);
            log.info("Success, updated film - {}", film);
            return film;
        }
        log.info("Error, with updating film ");
        throw new IllegalStateException();
    }

    @Override
    public Film getFilm(long id) {
        if (id < 0 || id > this.id) {
            throw new IllegalArgumentException("Некорректный фильм id");
        }
        return filmMap.get(id);
    }

    public long getId() {
        return id;
    }
}

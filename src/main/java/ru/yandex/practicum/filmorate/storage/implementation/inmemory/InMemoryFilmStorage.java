package ru.yandex.practicum.filmorate.storage.implementation.inmemory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.*;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {

    @Override
    public Integer getRatingId(long filmId) {
        return null;
    }

    @Override
    public String getRating(Integer id) {
        return null;
    }

    @Override
    public Integer getGenreId(long filmId) {
        return null;
    }

    @Override
    public String getGenre(Integer id) {
        return null;
    }

    @Override
    public Collection<Long> getLikes(long id) {
        return null;
    }

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
        if (!filmMap.containsKey(id)) {
            throw new IllegalArgumentException("Фильм c Id - " + id + ", не найден");
        }
        return filmMap.get(id);
    }

    @Override
    public void addLike(long filmId, long userId) {
        filmMap.get(filmId).getLikes().add(userId);
    }

    @Override
    public void removeLike(long filmId, long userId) {
        filmMap.get(filmId).getLikes().remove(userId);
    }

    @Override
    public List<Film> getMostLikedFilms(Integer count) {
        Collection<Film> films = new HashSet<>(getFilms());
        List<Film> sortedFilms = new ArrayList<>(films);
        Comparator<Film> comparator = Collections.reverseOrder(Comparator.comparingInt(obj -> obj.getLikes().size()));
        sortedFilms.sort(comparator);
        if (sortedFilms.size() > count) {
            sortedFilms = sortedFilms.subList(0, count);
        }
        return sortedFilms;
    }


}

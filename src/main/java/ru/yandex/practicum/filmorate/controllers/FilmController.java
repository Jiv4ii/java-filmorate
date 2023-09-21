package ru.yandex.practicum.filmorate.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;


import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@RestController
@Validated
@RequestMapping("/films")
public class FilmController {
    private final FilmStorage filmStorage;
    private final FilmService filmService;

    @Autowired
    public FilmController(InMemoryFilmStorage filmStorage, FilmService filmService) {
        this.filmStorage = filmStorage;
        this.filmService = filmService;
    }

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        return filmStorage.addFilm(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        return filmStorage.updateFilm(film);
    }


    @GetMapping
    public Collection<Film> getFilms() {
        return filmStorage.getFilms();
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable long id, @PathVariable long userId) {
        filmService.addlike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable long id, @PathVariable long userId) {
        filmService.deleteLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(@RequestParam(name = "count", defaultValue = "10") Integer count) {
        return filmService.getMostLikedFilms(count);
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable long id) {
        return filmStorage.getFilm(id);
    }
}


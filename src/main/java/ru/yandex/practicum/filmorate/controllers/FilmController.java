package ru.yandex.practicum.filmorate.controllers;


import lombok.extern.slf4j.Slf4j;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;


import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;

@RestController
@Slf4j
@Validated
@RequestMapping("/films")
public class FilmController {
    private final HashMap<Long, Film> filmMap = new HashMap<>();
    private long id = 0;

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        film.setId(id++);
        filmMap.put(film.getId(), film);
        log.info("Success, created film - {}", film);
        return film;
    }

    @PutMapping("/{id}")
    public Film updateFilm(@PathVariable Long id, @RequestBody Film film) {
        filmMap.put(id, film);
        log.info("Success, updated film - {}", film);
        return film;
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable Long id) {
        log.info("Success, films received");
        return filmMap.get(id);
    }

    @GetMapping()
    public Collection<Film> getFilms() {
        log.info("Success, films received");
        return filmMap.values();
    }
}


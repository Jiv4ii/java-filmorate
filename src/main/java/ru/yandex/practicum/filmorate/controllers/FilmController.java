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
    private long id = 1;

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        film.setId(id++);
        filmMap.put(film.getId(), film);
        log.info("Success, created film - {}", film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        if (filmMap.containsKey(film.getId())) {
            filmMap.put(film.getId(), film);
            log.info("Success, updated film - {}", film);
            return film;
        }
        log.info("Error, with updating film ");
        return filmMap.get(film.getId());
    }


    @GetMapping()
    public Collection<Film> getFilms() {
        log.info("Success, films received");
        return filmMap.values();
    }
}


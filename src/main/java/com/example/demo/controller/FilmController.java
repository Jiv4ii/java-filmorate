package com.example.demo.controller;


import com.example.demo.model.Film;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;

@RestController
@Slf4j
@Validated
@RequestMapping("/film")
public class FilmController {
    private final HashMap<Long, Film> filmMap = new HashMap<>();

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
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
    public Film getFilms(@PathVariable Long id) {
        log.info("Success, films received");
        return filmMap.get(id);
    }
}


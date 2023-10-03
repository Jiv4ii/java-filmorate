package ru.yandex.practicum.filmorate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;

@RestController
@Validated
@RequestMapping("/genres")
public class GenreConroller {
    private final GenreService genreService;

    @Autowired
    public GenreConroller(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/{id}")
    public Genre getFilmGenre(@PathVariable long id) {
        return genreService.getGenre(id);
    }

    @GetMapping
    public List<Genre> getGenreName() {
        return genreService.getGenres();
    }
}

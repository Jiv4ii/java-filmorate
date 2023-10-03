package ru.yandex.practicum.filmorate.controllers;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

@RestController
@Validated
@RequestMapping("/mpa")
public class MpaConroller {
    private final MpaService mpaService;

    public MpaConroller(MpaService mpaService) {
        this.mpaService = mpaService;
    }

    @GetMapping("/{id}")
    public Rating getRatingName(@PathVariable long id) {
        return mpaService.getGenre(id);
    }

    @GetMapping
    public List<Rating> getFilmRating() {
        return mpaService.getGenres();
    }
}

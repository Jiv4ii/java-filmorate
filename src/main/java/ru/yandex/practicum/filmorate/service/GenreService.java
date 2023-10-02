package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.List;
@Slf4j
@Service
public class GenreService {
    private final GenreStorage genreStorage;

    @Autowired
    public GenreService(GenreStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    public Genre getGenre(long id) {
        try {
            genreStorage.getGenre(id);
        } catch (Exception e) {
            throw new IllegalArgumentException("Некорректный рейтинг Id");
        }
        log.info("Получен жанр - {}", genreStorage.getGenre(id).getName());
        return genreStorage.getGenre(id);
    }

    public List<Genre> getGenres() {
        log.info("Выведен список всех жанров");
        return genreStorage.getGenres();
    }
}

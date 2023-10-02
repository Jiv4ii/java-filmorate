package ru.yandex.practicum.filmorate.storage.implementation.db.genrestorage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.List;

@Component
public class GenreDbStorage implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;

    private final GenreRowMapper genreRowMapper;

    public GenreDbStorage(JdbcTemplate jdbcTemplate, GenreRowMapper genreRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.genreRowMapper = genreRowMapper;
    }

    @Override
    public Genre getGenre(long id) {
        return jdbcTemplate.queryForObject("select * from genres where id = ?", genreRowMapper, id);
    }

    @Override
    public List<Genre> getGenres() {
        return jdbcTemplate.query("select * from genres", genreRowMapper);
    }
}

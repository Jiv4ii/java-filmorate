package ru.yandex.practicum.filmorate.storage.implementation.db.mpastorage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.util.List;

@Component
public class MpaDbStorage implements MpaStorage {

    private final JdbcTemplate jdbcTemplate;
    private final RatingRowMapper ratingRowMapper;

    public MpaDbStorage(JdbcTemplate jdbcTemplate, RatingRowMapper ratingRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.ratingRowMapper = ratingRowMapper;
    }

    @Override
    public Rating getRating(long id) {
        return jdbcTemplate.queryForObject("select * from Ratings where id = ?", ratingRowMapper, id);
    }

    @Override
    public List<Rating> getRatings() {
        return jdbcTemplate.query("select * from Ratings", ratingRowMapper);
    }
}

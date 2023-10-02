package ru.yandex.practicum.filmorate.storage.implementation.db.filmstorage;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Rating;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class FilmRowMapper implements RowMapper<Film> {
    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("rating_id");
        Film film = new Film()
                .setId(rs.getLong("id"))
                .setName(rs.getString("name"))
                .setDescription(rs.getString("description"))
                .setDuration(rs.getInt("duration"))
                .setReleaseDate(rs.getDate("release_date").toLocalDate())
                .setMpa(new Rating());
        film.getMpa().setId(id);
        film.getMpa().setName(rs.getString("rating"));
        return film;

    }
}

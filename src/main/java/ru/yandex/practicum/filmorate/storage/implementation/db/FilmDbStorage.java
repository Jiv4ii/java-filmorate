package ru.yandex.practicum.filmorate.storage.implementation.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component("filmDb")
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    private final FilmRowMapper filmRowMapper;
    private long id = 1;

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate, FilmRowMapper filmRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.filmRowMapper = filmRowMapper;
    }

    @Override
    public Film addFilm(Film film) {
        jdbcTemplate.update("insert into films(name,description,release_date,duration) values (?,?,?,?)", film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration());
        film.setId(id);
        id++;
        return film;
    }

    @Override
    public void deleteFilm(long id) {
        jdbcTemplate.update("delete from FILMS where id = ?", id);
    }

    @Override
    public Film updateFilm(Film film) {
        jdbcTemplate.update("update films set name = ?, description = ?, release_date = ?, duration = ? where id = ?", film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getId());
        return null;
    }

    @Override
    public Collection<Film> getFilms() {
        return jdbcTemplate.query("select * from FILMS", filmRowMapper);
    }

    @Override
    public Film getFilm(long id) {
        return jdbcTemplate.query("select * from films where id = ?", ps -> ps.setLong(1, id), filmRowMapper).stream().findFirst().orElse(null);
    }

    @Override
    public void addLike(long filmId, long userId) {
        jdbcTemplate.update("insert into FILM_LIKES(film_id, user_id) values (?,?)", filmId, userId);
    }

    @Override
    public void removeLike(long filmId, long userId) {
        jdbcTemplate.update("delete from FILM_LIKES where FILM_ID = ? and USER_ID = ?", filmId, userId);
    }

    @Override
    public List<Film> getMostLikedFilms(Integer count) {
        return new ArrayList<>(jdbcTemplate.query("select * from films as t1 RIGHT JOIN (select film_id, count(USER_ID) as co  from FILM_LIKES group by film_id) as t2 on t1.id = t2.film_id order by co desc limit ?", ps -> ps.setLong(1, count), filmRowMapper));

    }

    @Override
    public Collection<Long> getLikes(long id) {
        return jdbcTemplate.queryForList("Select USER_ID from FILM_LIKES where film_id = ?", Long.class, id);
    }

    @Override
    public Integer getRatingId(long filmId) {
        return getFilm(filmId).getRating();
    }

    @Override
    public String getRating(Integer id) {
        return jdbcTemplate.queryForObject("select rating from ratings where id = ?", String.class, id);
    }

    @Override
    public Integer getGenreId(long filmId) {
        return getFilm(filmId).getGenre();
    }

    @Override
    public String getGenre(Integer id) {
        return jdbcTemplate.queryForObject("select genre from genres where id = ?", String.class, id);
    }
}

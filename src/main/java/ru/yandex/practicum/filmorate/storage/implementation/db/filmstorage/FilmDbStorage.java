package ru.yandex.practicum.filmorate.storage.implementation.db.filmstorage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.*;

@Slf4j
@Component("filmDb")
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    private final FilmRowMapper filmRowMapper;
    private final GenreFilmRowMapper genreRowMapper;
    private long id = 1;

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate, FilmRowMapper filmRowMapper, GenreFilmRowMapper genreRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.filmRowMapper = filmRowMapper;
        this.genreRowMapper = genreRowMapper;
    }

    @Override
    public Film addFilm(Film film) {
        jdbcTemplate.update("insert into films(name,description,release_date,duration,RATING_id,likes) values (?,?,?,?,?,?);", film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa().getId(), 0);
        film.setId(id);
        id++;
        Set<Genre> uniqueSet = new LinkedHashSet<>(film.getGenres());
        for (Genre genre : uniqueSet) {
            jdbcTemplate.update("insert into FILM_GENRES values ( ?,? )", film.getId(), genre.getId());
        }
        film.setGenres(new ArrayList<>(uniqueSet));
        return film;
    }

    @Override
    public void deleteFilm(long id) {
        jdbcTemplate.update("delete from FILMS where id = ?", id);
    }

    @Override
    public Film updateFilm(Film film) {
        jdbcTemplate.update("update films set name = ?, description = ?, release_date = ?, duration = ?, RATING_id = ? where id = ?", film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa().getId(), film.getId());
        Set<Genre> uniqueSet = new LinkedHashSet<>(film.getGenres());
        jdbcTemplate.update("delete from FILM_GENRES where FILM_ID = ?", film.getId());
        for (Genre genre : uniqueSet) {
            jdbcTemplate.update("insert into FILM_GENRES values ( ?,? )", film.getId(), genre.getId());
        }
        film.setGenres(new ArrayList<>(uniqueSet));
        return film;
    }

    @Override
    public Collection<Film> getFilms() {
        Collection<Film> films = jdbcTemplate.query("select * from films as t1 inner join ratings as t2 on t1.RATING_id = t2.ID", filmRowMapper);
        for (Film film : films) {
            try {
                Set<Genre> genres = new LinkedHashSet<>(jdbcTemplate.query("select genre_id, genre from FILM_GENRES as t1 inner join genres as t2 on t1.GENRE_ID = t2.ID where FILM_ID = ?", genreRowMapper, film.getId()));
                film.setGenres(new ArrayList<>(genres));
            } catch (Exception exception) {

            }
        }
        return films;
    }

    @Override
    public Film getFilm(long id) {
        Film film = jdbcTemplate.query("select * from films as t1 inner join ratings as t2 on t1.RATING_id = t2.ID  where t1.id = ?", ps -> ps.setLong(1, id), filmRowMapper).stream().findFirst().orElse(null);
        try {
            Set<Genre> genres = new HashSet<>(jdbcTemplate.query("select genre_id, genre from FILM_GENRES as t1 inner join genres as t2 on t1.GENRE_ID = t2.ID where FILM_ID = ?", genreRowMapper, id));
            assert film != null;
            film.setGenres(new ArrayList<>(genres));
        } catch (Exception exception) {

        }

        return film;
    }

    @Override
    public void addLike(long filmId, long userId) {
        /*jdbcTemplate.update("insert into FILM_LIKES(film_id, user_id) values (?,?)", filmId, userId);*/
        jdbcTemplate.update("update FILMS set likes = likes + 1 where id = ?", filmId);
    }

    @Override
    public void removeLike(long filmId, long userId) {
        /*jdbcTemplate.update("delete from FILM_LIKES where FILM_ID = ? and USER_ID = ?", filmId, userId);*/
        jdbcTemplate.update("update FILMS set likes = likes - 1 where id = ?", filmId);
    }

    @Override
    public List<Film> getMostLikedFilms(Integer count) {
        List<Film> popularFilms = jdbcTemplate.query("select * from films as t1 inner join RATINGS as t3 on t1.RATING_ID = t3.ID order by likes desc limit ?", ps -> ps.setLong(1, count), filmRowMapper);
        for (Film film : popularFilms) {
            try {
                Set<Genre> genres = new HashSet<>(jdbcTemplate.query("select genre_id, genre from FILM_GENRES as t1 inner join genres as t2 on t1.GENRE_ID = t2.ID where FILM_ID = ?", genreRowMapper, film.getId()));
                film.setGenres(new ArrayList<>(genres));
            } catch (Exception exception) {

            }
        }
        return popularFilms;
    }

    @Override
    public Collection<Long> getLikes(long id) {
        return jdbcTemplate.queryForList("Select USER_ID from FILM_LIKES where film_id = ?", Long.class, id);
    }


}

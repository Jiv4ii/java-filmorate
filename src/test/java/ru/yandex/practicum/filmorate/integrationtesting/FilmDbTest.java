
package ru.yandex.practicum.filmorate.integrationtesting;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.implementation.db.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.implementation.db.UserDbStorage;

import java.time.LocalDate;
import java.util.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmDbTest {

    private final FilmDbStorage filmDbStorage;
    private final UserDbStorage userDbStorage;

    @Test
    void testFilmDb() {
        Film film = new Film().setId(1).setName("film").setDescription("descr").setDuration(120).setReleaseDate(LocalDate.of(2000,1,1));
        Film film2 = new Film().setId(2).setName("film2").setDescription("descr2").setDuration(120).setReleaseDate(LocalDate.of(2000,1,1));
        Film film3 = new Film().setId(3).setName("film3").setDescription("descr3").setDuration(120).setReleaseDate(LocalDate.of(2000,1,1));
        Film updatedFilm = new Film().setId(1).setName("upfilm").setDescription("updescr").setDuration(120).setReleaseDate(LocalDate.of(2000,1,1));
        User user = new User().setId(1).setEmail("email@.ru").setName("Name").setLogin("login").setBirthday(LocalDate.of(2000, 1, 1));
        User user2 = new User().setId(2).setEmail("email@.ru").setName("Name2").setLogin("login").setBirthday(LocalDate.of(2000, 1, 1));
        User user3 = new User().setId(3).setEmail("email@.ru").setName("Name3").setLogin("login").setBirthday(LocalDate.of(2000, 1, 1));
        userDbStorage.addUser(user);
        userDbStorage.addUser(user2);
        userDbStorage.addUser(user3);

        filmDbStorage.addFilm(film);
        Assertions.assertEquals(film, filmDbStorage.getFilm(1), "Неверное сохраненения фильма в базу");

        filmDbStorage.addFilm(film2);
        Set<Film> filmSet = new HashSet<>();
        filmSet.add(film);
        filmSet.add(film2);
        Set<Film> filmSetDb = new HashSet<>(filmDbStorage.getFilms());
        Assertions.assertEquals(filmSet,filmSetDb, "Список фильмов восстановлен неверно");

        Assertions.assertEquals(film,filmDbStorage.getFilm(1), "Неверное восстановление фильмов");

        filmDbStorage.addLike(1,3);
        Assertions.assertEquals(1,filmDbStorage.getLikes(1).size());
        Assertions.assertTrue(filmDbStorage.getLikes(1).contains(3l), "Неверное сохранение лайков");

        filmDbStorage.removeLike(1,3);
        Assertions.assertTrue(filmDbStorage.getMostLikedFilms(10).isEmpty());

        filmDbStorage.addFilm(film3);
        filmDbStorage.addLike(2,3);
        filmDbStorage.addLike(1,3);
        filmDbStorage.addLike(1,2);
        filmDbStorage.addLike(1,1);
        filmDbStorage.addLike(3,2);
        filmDbStorage.addLike(3,1);

        List<Film> films = new ArrayList<>();
        films.add(film);
        films.add(film3);
        films.add(film2);
        Assertions.assertEquals(films, filmDbStorage.getMostLikedFilms(10), "Список популярных фильмов некорректен");

        filmDbStorage.updateFilm(updatedFilm);
        Assertions.assertEquals(updatedFilm, filmDbStorage.getFilm(1), "Обновление фильма происходит неверно");

        filmDbStorage.deleteFilm(1);
        Assertions.assertNull(filmDbStorage.getFilm(1), "Удаление пользователей не работает");








    }
}


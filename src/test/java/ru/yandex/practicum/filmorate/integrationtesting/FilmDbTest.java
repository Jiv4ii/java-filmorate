
package ru.yandex.practicum.filmorate.integrationtesting;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.implementation.db.filmstorage.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.implementation.db.userstorage.UserDbStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmDbTest {

    private final FilmDbStorage filmDbStorage;
    private final UserDbStorage userDbStorage;

    @Transactional
    @Test
    void testFilmDb() {
        Film film = new Film().setName("film").setDescription("descr").setDuration(120).setReleaseDate(LocalDate.of(2000, 1, 1)).setMpa(new Rating().setId(2L).setName("PG"));
        Film film2 = new Film().setName("film2").setDescription("descr2").setDuration(120).setReleaseDate(LocalDate.of(2000, 1, 1)).setMpa(new Rating().setId(2L).setName("PG"));
        Film film3 = new Film().setName("film3").setDescription("descr3").setDuration(120).setReleaseDate(LocalDate.of(2000, 1, 1)).setMpa(new Rating().setId(2L).setName("PG"));
        Film updatedFilm = new Film().setId(1).setName("upfilm").setDescription("updescr").setDuration(120).setReleaseDate(LocalDate.of(2000, 1, 1)).setMpa(new Rating().setId(2L).setName("PG"));
        User user = new User().setEmail("email1@stas.ru").setName("Name").setLogin("login1stas").setBirthday(LocalDate.of(2000, 1, 1));
        User user2 = new User().setEmail("email2@fred.ru").setName("Name2").setLogin("login2fred").setBirthday(LocalDate.of(2000, 1, 1));
        User user3 = new User().setEmail("email3@jorsh.ru").setName("Name3").setLogin("login3jorsh").setBirthday(LocalDate.of(2000, 1, 1));
        userDbStorage.addUser(user);
        userDbStorage.addUser(user2);
        userDbStorage.addUser(user3);
        System.out.println(userDbStorage.getUser(1));
        System.out.println(userDbStorage.getUser(2));
        System.out.println(userDbStorage.getUser(3));
        filmDbStorage.addFilm(film);
        Assertions.assertEquals(film, filmDbStorage.getFilm(1), "Неверное сохраненения фильма в базу");

        filmDbStorage.addFilm(film2);
        Set<Film> filmSet = new HashSet<>();
        filmSet.add(film);
        filmSet.add(film2);
        Set<Film> filmSetDb = new HashSet<>(filmDbStorage.getFilms());
        Assertions.assertEquals(filmSet, filmSetDb, "Список фильмов восстановлен неверно");

        Assertions.assertEquals(film, filmDbStorage.getFilm(1), "Неверное восстановление фильмов");

        filmDbStorage.addFilm(film3);
        filmDbStorage.addLike(1, 3);
/*        filmDbStorage.addLike(1, 2);
        filmDbStorage.addLike(3, 1);

        List<Film> films = new ArrayList<>();
        films.add(film);
        films.add(film3);
        films.add(film2);
        Assertions.assertEquals(films, filmDbStorage.getMostLikedFilms(10), "Список популярных фильмов некорректен");*/

    /*    filmDbStorage.updateFilm(updatedFilm);
        Assertions.assertEquals(updatedFilm, filmDbStorage.getFilm(1), "Обновление фильма происходит неверно");

        filmDbStorage.deleteFilm(1);
        Assertions.assertNull(filmDbStorage.getFilm(1), "Удаление пользователей не работает");*/


    }
}


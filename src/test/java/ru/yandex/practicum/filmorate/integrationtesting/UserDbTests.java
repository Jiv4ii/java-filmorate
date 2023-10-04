/*

package ru.yandex.practicum.filmorate.integrationtesting;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.implementation.db.userstorage.UserDbStorage;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserDbTests {
    private final UserDbStorage userStorage;

    @Transactional
    @Test
    void testUserDb() {
        User user = new User().setEmail("email@gri.ru").setName("Name").setLogin("logingri").setBirthday(LocalDate.of(2000, 1, 1));
        User user2 = new User().setEmail("email2vov@.ru").setName("Name2").setLogin("login2vov").setBirthday(LocalDate.of(2000, 1, 1));
        User user3 = new User().setEmail("email23cher@.ru").setName("Name2").setLogin("login23cher").setBirthday(LocalDate.of(2000, 1, 1));
        User updatedUser = new User().setEmail("emailUp@.ru").setName("NameUp").setLogin("loginUp").setBirthday(LocalDate.of(2000, 1, 1)).setId(1);

        userStorage.addUser(user);
        User userDb = userStorage.getUser(1);
        Assertions.assertEquals(user, userDb, "Пользователь неверно вносится в базу");


        userStorage.addUser(user2);
        Set<User> userSet = new HashSet<>();
        userSet.add(user);
        userSet.add(user2);
        Set<User> userSetDb = new HashSet<>(userStorage.getUsers());
        Assertions.assertEquals(userSet, userSetDb, "Список пользователей восстанавливается неправильно");

        userStorage.addFriend(1, 2);

        Assertions.assertTrue(userStorage.getFriends(1).contains(user2), "Пользователи не могут добавиться в друзья");

        userStorage.addUser(user3);
        userStorage.addFriend(3, 2);
        Assertions.assertEquals(1, userStorage.getCommonFriends(1L, 3L).size(), "Неверное кол-во общих друзей");
        Assertions.assertTrue(userStorage.getCommonFriends(1L, 3L).contains(user2), "Неверный общий друг");

        userStorage.removeFriend(1, 2);
        Assertions.assertTrue(userStorage.getFriends(1).isEmpty(), "Удаление друзей не работает");

        userStorage.updateUser(updatedUser);
        Assertions.assertEquals(updatedUser, userStorage.getUser(1), "Пользователь неверно обновлен");


*/
/*        userStorage.deleteUser(1);
        Assertions.assertNull(userStorage.getUser(1), "Пользователь не удаляется из базы данных");*//*



    }
}

*/

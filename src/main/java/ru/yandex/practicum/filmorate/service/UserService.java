package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;


import java.util.Collection;
import java.util.Set;

@Slf4j
@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(@Qualifier("db") UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriend(Long userId, Long friendId) {
        if (userStorage.getUser(userId) == null) {
            throw new IllegalArgumentException("Пользователь с Id - {}, не найден");
        }
        if (userStorage.getUser(friendId) == null) {
            throw new IllegalArgumentException("Друг с Id - {}, не найден");
        }
        userStorage.addFriend(userId, friendId);
        log.info("Пользователи с id {} и {} теперь друзья", userId, friendId);
    }

    public void deleteFriend(Long userId, Long friendId) {
        if (userStorage.getUser(userId) == null) {
            throw new IllegalArgumentException("Пользователь с Id - {}, не найден");
        }
        if (userStorage.getUser(friendId) == null) {
            throw new IllegalArgumentException("Друг с Id - {}, не найден");
        }
        userStorage.removeFriend(userId, friendId);
        log.info("Пользователи с id {} и {} теперь не друзья", userId, friendId);
    }

    public Set<User> getCommonFriends(Long userId, Long friendId) {
        if (userStorage.getUser(userId) == null) {
            throw new IllegalArgumentException("Пользователь с Id - {}, не найден");
        }
        if (userStorage.getUser(friendId) == null) {
            throw new IllegalArgumentException("Друг с Id - {}, не найден");
        }

        Set<User> commonFriends = userStorage.getCommonFriends(userId, friendId);
        log.info("Выведен список фильмов с общих друзей пользователей {} и {}", userId, friendId);
        return commonFriends;
    }

    public Set<User> getFriends(Long userId) {
        Set<User> friends = userStorage.getFriends(userId);
        if (friends == null) {
            throw new IllegalArgumentException("Пользователь с Id - {}, не найден");
        }
        log.info("Выведен список друзей пользователя {}", userId);
        return friends;

    }

    public User createUser(User user) {
        log.info("Пользователь создан");
        return userStorage.addUser(user);
    }

    public User updateUser(User user) {
        if (userStorage.getUser(user.getId()) == null) {
            throw new IllegalArgumentException("Пользователь с Id - {}, не найден");
        }
        log.info("Пользователь Id - {}, обновлен", user.getId());
        return userStorage.updateUser(user);
    }

    public Collection<User> getUsers() {
        return userStorage.getUsers();
    }

    public User getUser(long id) {
        if (userStorage.getUser(id) == null) {
            throw new IllegalArgumentException("Пользователь с Id - {}, не найден");
        }
        return userStorage.getUser(id);
    }
}

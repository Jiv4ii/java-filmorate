package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Set;

@Slf4j
@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriend(Long userId, Long friendId) {
        if (userId < 1 || userId > userStorage.getId()) {
            throw new IllegalArgumentException("Некорректный Id пользователя");
        }
        if (friendId < 1 || friendId > userStorage.getId()) {
            throw new IllegalArgumentException("Некорректный Id друга");
        }
        userStorage.getUser(userId).getFriends().add(friendId);
        userStorage.getUser(friendId).getFriends().add(userId);
        log.info("Пользователи с id {} и {} теперь друзья", userId, friendId);
    }

    public void deleteFriend(Long userId, Long friendId) {
        if (userId < 1 || userId > userStorage.getId()) {
            throw new IllegalArgumentException("Некорректный Id пользователя");
        }
        if (friendId < 1 || friendId > userStorage.getId()) {
            throw new IllegalArgumentException("Некорректный Id друга");
        }
        userStorage.getUser(userId).getFriends().remove(friendId);
        userStorage.getUser(friendId).getFriends().remove(userId);
        log.info("Пользователи с id {} и {} теперь не друзья", userId, friendId);
    }

    public Set getCommonFriends(Long userId, Long friendId) {
        if (userId < 1 || userId > userStorage.getId()) {
            throw new IllegalArgumentException("Некорректный Id пользователя");
        }
        if (friendId < 1 || friendId > userStorage.getId()) {
            throw new IllegalArgumentException("Некорректный Id друга");
        }
        Set<Long> set1 = userStorage.getUser(userId).getFriends();
        Set<Long> set2 = userStorage.getUser(friendId).getFriends();

        set1.retainAll(set2);
        log.info("Выведен список фильмов с общих друзей пользователей {} и {}", userId, friendId);
        return set1;
    }

    public Set<Long> getFriends(Long userId) {
        if (userId < 1 || userId > userStorage.getId()) {
            throw new IllegalArgumentException("Некорректный Id пользователя");
        }
        log.info("Выведен список друзей пользователя {}", userId);
        return userStorage.getUser(userId).getFriends();

    }
}

package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Set;

public interface UserStorage {
    User addUser(User user);

    void deleteUser(long id);

    User updateUser(User user);

    Collection<User> getUsers();

    User getUser(long id);

    void addFriend(long userId, long friendID);

    void removeFriend(long userId, long friendID);

    Set<User> getCommonFriends(Long userId, Long friendId);

}

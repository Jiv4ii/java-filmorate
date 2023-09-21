package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {
    User addUser(User user);

    void deleteUser(long id);

    User updateUser(User user);

    Collection<User> getUsers();

    User getUser(long id);

    long getId();

}

package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private final HashMap<Long, User> userMap = new HashMap<>();
    private long id = 1;

    @Override
    public User addUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setId(id++);
        userMap.put(user.getId(), user);
        log.info("Success, created user - {}", user);
        return userMap.get(user.getId());

    }

    @Override
    public Collection<User> getUsers() {
        log.info("Success, users received");
        return userMap.values();
    }

    @Override
    public void deleteUser(long id) {
        if (!userMap.containsKey(id)) {
            log.info("Error, user with id {} doesnt exist", id);
            return;
        }
        userMap.remove(id);
        log.info("Success, user with id {} deleted", id);
    }


    @Override
    public User updateUser(User user) {
        if (userMap.containsKey(user.getId())) {
            user.setFriends(userMap.get(user.getId()).getFriends());
            userMap.put(user.getId(), user);
            log.info("Success, updated user - {}", user);
            return user;
        }
        log.info("Error, with updating user ");
        throw new IllegalStateException();
    }

    @Override
    public User getUser(long id) {
        if (userMap.get(id) == null) {
            throw new IllegalArgumentException("Некорректный пользовательский id");
        }
        return userMap.get(id);
    }

    @Override
    public void addLike(long userId, long friendId) {
        userMap.get(userId).getFriends().add(userMap.get(friendId));
        userMap.get(friendId).getFriends().add(userMap.get(userId));
    }

    @Override
    public void removeLike(long userId, long friendId) {
        userMap.get(userId).getFriends().remove(userMap.get(friendId));
        userMap.get(friendId).getFriends().remove(userMap.get(userId));
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public Set<User> getCommonFriends(Long userId, Long friendId) {
        Set<User> set1 = userMap.get(userId).getFriends();
        Set<User> set2 = userMap.get(friendId).getFriends();
        Set<User> commonFriends = new HashSet<>(set1);

        commonFriends.retainAll(set2);
        return commonFriends;
    }

}

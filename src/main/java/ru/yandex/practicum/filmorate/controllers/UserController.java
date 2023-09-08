package ru.yandex.practicum.filmorate.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;


import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;

@RestController
@Slf4j
@Validated
@RequestMapping("/users")
public class UserController {

    private final HashMap<Long, User> userMap = new HashMap<>();

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        userMap.put(user.getId(), user);
        log.info("Success, created user - {}", user);
        return userMap.get(user.getId());
    }

    @PutMapping("/{userId}")
    public User updateFilm(@PathVariable Long userId, @RequestBody User user) {
        userMap.put(userId, user);
        log.info("Success, updated user - {}", user);
        return user;
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") long id) {
        log.info("Success, users received");
        return userMap.get(id);
    }

    @GetMapping
    public Collection<User> getUsers() {
        log.info("Success, users received");
        return userMap.values();
    }


}

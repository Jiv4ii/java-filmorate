package ru.yandex.practicum.filmorate.storage.implementation.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component("db")
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;
    private final UserRowMapper userRowMapper;
    private long id = 1;


    @Autowired
    public UserDbStorage(JdbcTemplate jdbcTemplate, UserRowMapper userRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRowMapper = userRowMapper;
    }


    @Override
    public User addUser(User user) {
        jdbcTemplate.update("insert into users(email,login,name,birthday) values (?,?,?,?)", user.getEmail(), user.getLogin(), user.getName(), user.getBirthday());
        user.setId(id);
        id++;
        return user;
    }

    @Override
    public void deleteUser(long id) {
        jdbcTemplate.update("delete from users where id = ?", id);
    }

    @Override
    public User updateUser(User user) {
        jdbcTemplate.update("update users set email = ?,login = ?,name = ?,birthday = ? where id = ?", user.getEmail(), user.getLogin(), user.getName(), user.getBirthday(), user.getId());
        return user;
    }

    @Override
    public Collection<User> getUsers() {
        return jdbcTemplate.query("select * from users", userRowMapper);
    }

    @Override
    public User getUser(long id) {
        return jdbcTemplate.query("select * from users where id = ?", ps -> ps.setLong(1, id), userRowMapper).stream().findFirst().orElse(null);
    }

    @Override
    public void addFriend(long userId, long friendID) {
        jdbcTemplate.update("insert into user_friends(user_id,friend_id) values (?,?)", userId, friendID);
    }

    @Override
    public void removeFriend(long userId, long friendID) {
        jdbcTemplate.update("delete from user_friends where user_id = ? and friend_id = ?", userId, friendID);
    }

    @Override
    public Set<User> getCommonFriends(Long userId, Long friendId) {
        return new HashSet<>(jdbcTemplate.query("select * from users where id in (select f1.friend_id from user_friends f1 INNER JOIN user_friends f2 on f1.friend_id = f2.friend_id where f1.user_id = ? and f2.user_id = ?)", ps -> {
            ps.setLong(1, userId);
            ps.setLong(2, friendId);
        }, userRowMapper));
    }

    @Override
    public Set<User> getFriends(long id) {
        return new HashSet<>(jdbcTemplate.query("select * from users where id in (select friend_id from user_friends where user_id = ?)", ps -> ps.setLong(1, id), userRowMapper));
    }
}

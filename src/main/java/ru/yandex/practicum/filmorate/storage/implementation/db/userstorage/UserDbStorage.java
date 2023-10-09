package ru.yandex.practicum.filmorate.storage.implementation.db.userstorage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.sql.PreparedStatement;
import java.sql.Date;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component("db")
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;
    private final UserRowMapper userRowMapper;


    @Autowired
    public UserDbStorage(JdbcTemplate jdbcTemplate, UserRowMapper userRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRowMapper = userRowMapper;
    }


    @Override
    public User addUser(User user) {
        if (user.getName().isBlank() || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into users(email,login,name,birthday) values (?,?,?,?)", new String[]{"id"}
            );
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getName());
            ps.setDate(4, Date.valueOf(user.getBirthday()));

            return ps;
        }, keyHolder);
        user.setId(keyHolder.getKey().longValue());
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
        return new HashSet<>(jdbcTemplate.query("SELECT u.* FROM users u INNER JOIN user_friends f1 ON u.id = f1.friend_id INNER JOIN user_friends f2 ON f1.friend_id = f2.friend_id WHERE f1.user_id = ? AND f2.user_id = ?;", ps -> {
            ps.setLong(1, userId);
            ps.setLong(2, friendId);
        }, userRowMapper));
    }

    @Override
    public Set<User> getFriends(long id) {
        return new HashSet<>(jdbcTemplate.query("select u.* from users as u inner join user_friends as uf ON u.id = uf.friend_id WHERE uf.user_id = ?", ps -> ps.setLong(1, id), userRowMapper));
    }
}

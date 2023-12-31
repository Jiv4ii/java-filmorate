package ru.yandex.practicum.filmorate.storage.implementation.db.userstorage;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;


import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new User()
                .setId(rs.getLong("id"))
                .setLogin(rs.getString("login"))
                .setName(rs.getString("name"))
                .setEmail(rs.getString("email"))
                .setBirthday(rs.getDate("birthday").toLocalDate());
    }
}

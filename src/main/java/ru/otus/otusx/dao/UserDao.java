package ru.otus.otusx.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.otus.otusx.dao.entity.User;
import ru.otus.otusx.logic.exception.NotSaveException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserDao {
    private static final String FIND_BY_UUID = "SELECT uuid, name, surname, birthDate, sex, interests, city, " +
            "password FROM otus_x.authors WHERE uuid=?";
    private static final String INSERT_AUTHOR = "INSERT INTO otus_x.authors (uuid, name, surname, birthDate, sex, interests," +
            " city, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_BY_NAMES = "SELECT uuid, name, surname, birthDate, sex, interests, city " +
            "FROM otus_x.authors WHERE surname LIKE ? AND name LIKE ?";
    private static final String LIKE_PERCENT = "%";
    private final JdbcTemplate jdbcTemplate;

    public Optional<User> findByUuid(UUID uuid) {
        return Optional.ofNullable(jdbcTemplate.query(FIND_BY_UUID, userResultSetExtractor(), uuid));
    }

    private ResultSetExtractor<User> userResultSetExtractor() {
        return rs -> {
            if (rs.next()) {
                return userFromResultSet(rs)
                        .setPassword(rs.getString("password"));
            } else {
                return null;
            }
        };
    }

    private User userFromResultSet(ResultSet rs) throws SQLException {
        return new User()
                .setUuid(rs.getObject("uuid", UUID.class))
                .setName(rs.getString("name"))
                .setSurname(rs.getString("surname"))
                .setBirthDate(rs.getObject("birthDate", LocalDate.class))
                .setSex(rs.getString("sex"))
                .setInterests(rs.getString("interests"))
                .setCity(rs.getString("city"));
    }

    public UUID save(User user) {
        var rows = jdbcTemplate.update(INSERT_AUTHOR, statement -> {
            statement.setObject(1, user.getUuid());
            statement.setString(2, user.getName());
            statement.setString(3, user.getSurname());
            statement.setObject(4, user.getBirthDate());
            statement.setObject(5, user.getSex());
            statement.setString(6, user.getInterests());
            statement.setString(7, user.getCity());
            statement.setString(8, user.getPassword());
        });
        if (rows == 0) {
            throw new NotSaveException();
        }
        return user.getUuid();
    }

    public List<User> findByPrefixNames(String name, String surname) {
        return jdbcTemplate.query(FIND_BY_NAMES, userRowMapper(), surname + LIKE_PERCENT, name + LIKE_PERCENT);
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> userFromResultSet(rs);
    }
}

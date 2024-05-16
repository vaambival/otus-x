package ru.otus.otusx.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.otus.otusx.dao.entity.User;
import ru.otus.otusx.dao.exception.NotSaveException;
import ru.otus.otusx.dao.exception.AlreadyFollowException;
import ru.otus.otusx.dao.exception.FolloweeNotExistException;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Slf4j
public class FollowerDao {

    private static final String VIOLATE_UNIQUE_CONSTRAINT = "23505";
    private static final String FOLLOWEE_NOT_EXIST_CONSTRAINT = "23503";
    private static final String MAKE_FRIENDSHIP = "INSERT INTO otus_x.followers (follower_id, followee_id) " +
            "VALUES (?, ?)";
    private static final String DELETE_FRIENDSHIP = "DELETE FROM otus_x.followers WHERE follower_id=? " +
            "AND followee_id=?";
    private static final String FIND_ALL_FOLLOWERS = "SELECT follower_id FROM otus_x.followers WHERE followee_id=?";

    @Qualifier("writeJdbcTemplate")
    private final JdbcTemplate writeJdbcTemplate;
    @Qualifier("readJdbcTemplate")
    private final JdbcTemplate readJdbcTemplate;

    public void insertFollower(UUID follower, UUID followee) {
        try {
            var rows = writeJdbcTemplate.update(MAKE_FRIENDSHIP, statement -> {
                statement.setObject(1, follower);
                statement.setObject(2, followee);
            });
            if (rows == 0) {
                throw new NotSaveException();
            }
        } catch (Exception error) {
            if (error.getCause() instanceof SQLException ex) {
                if (StringUtils.equals(ex.getSQLState(), VIOLATE_UNIQUE_CONSTRAINT)) {
                    log.error("Duplicate follower and followee", ex);
                    throw new AlreadyFollowException(follower, followee);
                } else if (StringUtils.equals(ex.getSQLState(), FOLLOWEE_NOT_EXIST_CONSTRAINT)) {
                    log.error("Followee doesn't exist", ex);
                    throw new FolloweeNotExistException(followee);
                }
            }
            throw error;
        }
    }

    public void deleteFollower(UUID follower, UUID followee) {
        writeJdbcTemplate.update(DELETE_FRIENDSHIP, statement -> {
            statement.setObject(1, follower);
            statement.setObject(2, followee);
        });
    }

    public List<UUID> findAllFollowers(UUID uuid) {
        return readJdbcTemplate.queryForList(FIND_ALL_FOLLOWERS, UUID.class, uuid);
    }
}

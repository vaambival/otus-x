package ru.otus.otusx.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import ru.otus.otusx.dao.entity.Post;
import ru.otus.otusx.dao.exception.NotSaveException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PostDao {

    private static final String INSERT_POST = "INSERT INTO otus_x.posts (id, text, author, created, updated) " +
            "VALUES (?, ?, ?, ?, ?)";
    private static final String FIND_BY_UUID = "SELECT id, text, author, created, updated FROM otus_x.posts WHERE id=?";
    private static final String UPDATE_POST = "UPDATE otus_x.posts SET text=?, updated = ? WHERE id=?";
    private static final String DELETE_POST = "DELETE FROM otus_x.posts WHERE id=?";

    @Qualifier("writeJdbcTemplate")
    private final JdbcTemplate writeJdbcTemplate;
    @Qualifier("readJdbcTemplate")
    private final JdbcTemplate readJdbcTemplate;

    public UUID insert(Post post) {
        var rows = writeJdbcTemplate.update(INSERT_POST, statement -> {
            statement.setObject(1, post.getUuid());
            statement.setString(2, post.getText());
            statement.setObject(3, post.getAuthor());
            statement.setObject(4, post.getCreated());
            statement.setObject(5, post.getUpdated());
        });
        if (rows == 0) {
            throw new NotSaveException();
        }
        return post.getUuid();
    }

    public void update(Post updated) {
        var rows = writeJdbcTemplate.update(UPDATE_POST, statement -> {
            statement.setString(1, updated.getText());
            statement.setObject(2, updated.getUpdated());
            statement.setObject(3, updated.getUuid());
        });
        if (rows == 0) {
            throw new NotSaveException();
        }
    }

    public Optional<Post> findByUUID(UUID uuid) {
        return Optional.ofNullable(readJdbcTemplate.query(FIND_BY_UUID, postResultSetExtractor(), uuid));
    }

    private ResultSetExtractor<Post> postResultSetExtractor() {
        return rs -> {
            if (rs.next()) {
                return new Post()
                        .setUuid(rs.getObject("id", UUID.class))
                        .setAuthor(rs.getObject("author", UUID.class))
                        .setText(rs.getString("text"))
                        .setCreated(rs.getObject("created", LocalDateTime.class))
                        .setUpdated(rs.getObject("updated", LocalDateTime.class));
            }
            return null;
        };
    }

    public void delete(UUID post) {
        writeJdbcTemplate.update(DELETE_POST, statement -> statement.setObject(1, post));
    }
}

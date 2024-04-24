package ru.otus.otusx.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.otus.otusx.dao.entity.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class FeedDao {

    private static final String FEED_QUERY = "SELECT p.id, p.text, p.author, p.created, p.updated " +
            "FROM otus_x.posts p INNER JOIN otus_x.authors a ON p.author = a.uuid " +
            "INNER JOIN otus_x.followers f ON f.followee_id = a.uuid " +
            "WHERE f.follower_id = ? AND a.celebrity IS TRUE AND p.created >= ? ORDER BY p.created DESC LIMIT 1000";

    @Qualifier("readJdbcTemplate")
    private final JdbcTemplate readJdbcTemplate;

    public List<Post> findFeedForUser(UUID uuid, LocalDateTime since) {
        return readJdbcTemplate.query(FEED_QUERY, postRowMapper(), uuid, since);
    }

    private RowMapper<Post> postRowMapper() {
        return (rs, rowNum) -> new Post()
                .setUuid(rs.getObject("id", UUID.class))
                .setText(rs.getString("text"))
                .setAuthor(rs.getObject("author", UUID.class))
                .setCreated(rs.getObject("created", LocalDateTime.class))
                .setUpdated(rs.getObject("updated", LocalDateTime.class));
    }
}

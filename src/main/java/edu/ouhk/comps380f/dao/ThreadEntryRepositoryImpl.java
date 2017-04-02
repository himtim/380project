package edu.ouhk.comps380f.dao;

import edu.ouhk.comps380f.model.Attachment;
import edu.ouhk.comps380f.model.Thread;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;


@Repository
public class ThreadEntryRepositoryImpl implements ThreadEntryRepository {
    
    DataSource dataSource;
    private final JdbcOperations jdbcOp;

    @Autowired
    public ThreadEntryRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcOp = new JdbcTemplate(this.dataSource);
    }

private static final String SQL_INSERT_ENTRY
        = "insert into thread (title,content,username,category) values (?, ?, ?, ?)";

@Override
public int create(final Thread entry) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    
    PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
        public PreparedStatement createPreparedStatement(Connection connection)
                throws SQLException {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_ENTRY, new String[] { "id" });

            preparedStatement.setString(1, entry.getTitle());
            preparedStatement.setString(2, entry.getContent());
            preparedStatement.setString(3, entry.getUsername());
            preparedStatement.setString(4, entry.getCategory());

            return preparedStatement;
        }
    };
    jdbcOp.update(preparedStatementCreator, keyHolder
    );
    return keyHolder.getKey().intValue();
}

    private static final String SQL_SELECT_ALL_ENTRY
            = "select t.tid, t.title, t.username, u.banning from thread t, users u where t.category = ? and t.username = u.username";

@Override
public List<Thread> findAll(String category) {

    List<Thread> entries = new ArrayList<>();
    List<Map<String, Object>> rows = jdbcOp.queryForList(SQL_SELECT_ALL_ENTRY, category);
    for (Map<String, Object> row : rows) {
        Thread entry = new Thread();
        entry.setTid((int) row.get("tid"));
        entry.setTitle((String) row.get("title"));
        entry.setUsername((String) row.get("username"));
        entry.setBanning((String) row.get("banning"));
        entries.add(entry);
    }
    return entries;
}
private static final String SQL_SELECT_ENTRY
            = "select tid, title, content, category, username from thread where tid = ?";

@Override
public Thread findById(int tid) {
    return jdbcOp.queryForObject(SQL_SELECT_ENTRY, new EntryRowMapper(), tid);
}

    private static final class EntryRowMapper implements RowMapper<Thread> {

        @Override
        public Thread mapRow(ResultSet rs, int i) throws SQLException {
            Thread entry = new Thread();
            entry.setTid(rs.getInt("tid"));
            entry.setTitle(rs.getString("title"));
            entry.setContent(rs.getString("content"));
            entry.setCategory(rs.getString("category"));
            entry.setUsername(rs.getString("username"));
            return entry;
        }
    }
    
    private static final String SQL_INSERT_ATTACHMENT
        = "insert into threadAttachment (tid, name, mimeContentType, contents) values (?, ?, ?, ?)";

    @Override
    public void createAttachment(Attachment entry) {
        jdbcOp.update(SQL_INSERT_ATTACHMENT,
                entry.getId(),
                entry.getName(),
                entry.getMimeContentType(),
                entry.getContents()
        );
    }
    private static final String SQL_SELECT_ALL_ATTACHMENT
            = "select name from threadAttachment where tid = ?";

@Override
public Map<String, Attachment> findAllAttachment(int id) {

    Map<String, Attachment> entries = new LinkedHashMap<>();
    List<Map<String, Object>> rows = jdbcOp.queryForList(SQL_SELECT_ALL_ATTACHMENT, id);
    for (Map<String, Object> row : rows) {
        Attachment entry = new Attachment();
        entry.setName((String) row.get("name"));
        entries.put(entry.getName(), entry);
    }
    return entries;
}
private static final String SQL_SELECT_ATTACHMENT
            = "select name, mimeContentType, contents from threadAttachment where tid = ? and name = ?";

@Override
public Attachment findAttachmentById(int tid, String name) {
    return jdbcOp.queryForObject(SQL_SELECT_ATTACHMENT, new EntryRowMapper2(), tid, name);
}

    private static final class EntryRowMapper2 implements RowMapper<Attachment> {

        @Override
        public Attachment mapRow(ResultSet rs, int i) throws SQLException {
            Attachment entry = new Attachment();
            entry.setName(rs.getString("name"));
            entry.setMimeContentType(rs.getString("mimeContentType"));
            Blob blob = rs.getBlob("contents");
            entry.setContents(blob.getBytes(1, (int) blob.length()));
            return entry;
        }
    }
    private static final String SQL_DELETE_THREAD
        = "delete from thread where tid = ?";

    @Override
    public void deleteThread(int id) {
        jdbcOp.update(SQL_DELETE_THREAD, id);
    }

}

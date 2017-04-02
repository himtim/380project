package edu.ouhk.comps380f.dao;

import edu.ouhk.comps380f.model.Attachment;
import edu.ouhk.comps380f.model.Reply;
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
public class ReplyEntryRepositoryImpl implements ReplyEntryRepository {
    
    DataSource dataSource;
    private final JdbcOperations jdbcOp;

    @Autowired
    public ReplyEntryRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcOp = new JdbcTemplate(this.dataSource);
    }

private static final String SQL_INSERT_ENTRY
        = "insert into reply (tid,content,username) values (?, ?, ?)";

@Override
public int create(final edu.ouhk.comps380f.model.Reply entry) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    
    PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
        public PreparedStatement createPreparedStatement(Connection connection)
                throws SQLException {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_ENTRY, new String[] { "id" });

            preparedStatement.setLong(1, entry.getTid());
            preparedStatement.setString(2, entry.getContent());
            preparedStatement.setString(3, entry.getUsername());

            return preparedStatement;
        }
    };
    jdbcOp.update(preparedStatementCreator, keyHolder
    );
    return keyHolder.getKey().intValue();
}

    private static final String SQL_SELECT_ALL_ENTRY
            = "select r.rid, r.content, r.username, u.banning from reply r, users u where r.tid = ?  and r.username = u.username order by r.rid";

@Override
public List<Reply> findAll(int tid) {

    List<Reply> entries = new ArrayList<>();
    List<Map<String, Object>> rows = jdbcOp.queryForList(SQL_SELECT_ALL_ENTRY, tid);
    for (Map<String, Object> row : rows) {
        Reply entry = new Reply();
        entry.setRid((int) row.get("rid"));
        entry.setContent((String) row.get("content"));
        entry.setUsername((String) row.get("username"));
        entry.setBanning((String) row.get("banning"));
        entries.add(entry);
    }
    return entries;
}


    private static final String SQL_INSERT_ATTACHMENT
        = "insert into ReplyAttachment (rid, name, mimeContentType, contents) values (?, ?, ?, ?)";

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
            = "select name from ReplyAttachment where rid = ?";

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
            = "select name, mimeContentType, contents from ReplyAttachment where rid = ? and name = ?";

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

private static final String SQL_DELETE_REPLY
        = "delete from reply where rid = ?";

    @Override
    public void deleteReply(int id) {
        jdbcOp.update(SQL_DELETE_REPLY, id);
    }
    
private static final String SQL_DELETE_REPLY_OF_THREAD
        = "delete from reply where tid = ?";

    @Override
    public void deleteReplyOfThread(int id) {
        jdbcOp.update(SQL_DELETE_REPLY_OF_THREAD, id);
    }

}

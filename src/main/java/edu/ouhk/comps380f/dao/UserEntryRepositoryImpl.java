package edu.ouhk.comps380f.dao;

import edu.ouhk.comps380f.model.UserEntry;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserEntryRepositoryImpl implements UserEntryRepository {

    DataSource dataSource;
    private final JdbcOperations jdbcOp;

    @Autowired
    public UserEntryRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcOp = new JdbcTemplate(this.dataSource);
    }

    private static final String SQL_INSERT_ENTRY
            = "insert into users (username,password,banning,role,voted) values (?,?,'N',?,'N')";

    @Override
    public void create(UserEntry entry) {
        jdbcOp.update(SQL_INSERT_ENTRY,
                entry.getUsername(),
                entry.getPassword(),
                entry.getRole()
        );
    }

    private static final String SQL_SELECT_ALL_ENTRY
            = "select uid, username, banning, role from users";

    @Override
    public List<UserEntry> findAll() {

        List<UserEntry> entries = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcOp.queryForList(SQL_SELECT_ALL_ENTRY);

        for (Map<String, Object> row : rows) {
            UserEntry entry = new UserEntry();
            entry.setUid((int) row.get("uid"));
            entry.setUsername((String) row.get("username"));
            entry.setBanning((String) row.get("banning"));
            entry.setRole((String) row.get("role"));
            entries.add(entry);
        }
        return entries;
    }

    private static final String SQL_DELETE_USER
            = "delete from users where uid = ?";

    @Override
    public void deleteUser(int id) {
        jdbcOp.update(SQL_DELETE_USER, id);
    }

    private static final String SQL_SELECT_ENTRY
            = "select uid, username, password, banning, role, voted from users where uid = ?";

    @Override
    public UserEntry findById(int uid) {
        return jdbcOp.queryForObject(SQL_SELECT_ENTRY, new EntryRowMapper(), uid);
    }

    private static final String SQL_SELECT_ENTRY_UNAME
            = "select uid, username, password, banning, role, voted from users where username = ?";

    @Override
    public UserEntry findByUsername(String username) {
        return jdbcOp.queryForObject(SQL_SELECT_ENTRY_UNAME, new EntryRowMapper(), username);
    }

    private static final class EntryRowMapper implements RowMapper<UserEntry> {

        @Override
        public UserEntry mapRow(ResultSet rs, int i) throws SQLException {
            UserEntry entry = new UserEntry();
            entry.setUid(rs.getInt("uid"));
            entry.setUsername(rs.getString("username"));
            entry.setPassword(rs.getString("password"));
            entry.setBanning(rs.getString("banning"));
            entry.setRole(rs.getString("role"));
            entry.setVoted(rs.getString("voted"));

            return entry;
        }
    }

    private static final String SQL_UPDATE_ENTRY
            = "update users set username = ?, password = ?, banning = ?, role = ? where uid = ?";

    @Override
    public void update(UserEntry entry) {
        jdbcOp.update(SQL_UPDATE_ENTRY,
                entry.getUsername(),
                entry.getPassword(),
                entry.getBanning(),
                entry.getRole(),
                entry.getUid()
        );
    }
    
    private static final String SQL_UPDATE_VOTED
            = "update users set voted = 'Y' where username = ?";

    @Override
    public void updateVoted(UserEntry entry) {
        jdbcOp.update(SQL_UPDATE_VOTED,
                entry.getUsername()
        );
    }
    private static final String SQL_UPDATE_ALL_NOT_VOTED
            = "update users set voted = 'N'";

    @Override
    public void updateAllNotVoted() {
        jdbcOp.update(SQL_UPDATE_ALL_NOT_VOTED
        );
    }

}

package edu.ouhk.comps380f.dao;

import edu.ouhk.comps380f.model.UserEntry;
import java.util.List;

public interface UserEntryRepository {
    public void create(UserEntry entry);
    public List<UserEntry> findAll();
    public void deleteUser (int id);
    public UserEntry findById(int uid);
    public UserEntry findByUsername(String username);
    public void update(UserEntry entry);
    public void updateVoted(UserEntry entry);
    public void updateAllNotVoted();
}

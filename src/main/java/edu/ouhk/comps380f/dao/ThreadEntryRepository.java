package edu.ouhk.comps380f.dao;

import edu.ouhk.comps380f.model.Attachment;
import edu.ouhk.comps380f.model.Thread;
import java.util.List;
import java.util.Map;

public interface ThreadEntryRepository {
    public int create(Thread entry);
    public List<Thread> findAll(String category);
    public Thread findById(int tid);
    public void createAttachment(Attachment entry);
    public Map<String, Attachment> findAllAttachment(int id);
    public Attachment findAttachmentById(int id, String name);
    public void deleteThread(int id);
}

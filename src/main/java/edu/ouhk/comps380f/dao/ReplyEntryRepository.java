package edu.ouhk.comps380f.dao;

import edu.ouhk.comps380f.model.Attachment;
import edu.ouhk.comps380f.model.Reply;
import java.util.List;
import java.util.Map;

public interface ReplyEntryRepository {
    public int create(Reply entry);
    public List<Reply> findAll(int tid);
    public void deleteReply (int id);
    public void deleteReplyOfThread (int id);
    public void createAttachment(Attachment entry);
    public Map<String, Attachment> findAllAttachment(int id);
    public Attachment findAttachmentById(int id, String name);
}

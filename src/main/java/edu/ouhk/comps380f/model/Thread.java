package edu.ouhk.comps380f.model;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Thread {
    private long tid;
    private String title;
    private String content;
    private String username;
    private String category;
    private String banning;
    private Map<String, Attachment> attachments = new LinkedHashMap<>();
    private Map<String, Reply> replies = new LinkedHashMap<>();

    public long getTid() {
        return tid;
    }

    public void setTid(long tid) {
        this.tid = tid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBanning() {
        return banning;
    }

    public void setBanning(String banning) {
        this.banning = banning;
    }
    
    
    
     public Attachment getAttachment(String name) {
        return this.attachments.get(name);
    }

    public Collection<Attachment> getAttachments() {
        return this.attachments.values();
    }

    public void addAttachment(Attachment attachment) {
        this.attachments.put(attachment.getName(), attachment);
    }

    public int getNumberOfAttachments() {
        return this.attachments.size();
    }

    public boolean hasAttachment(String name) {
        return this.attachments.containsKey(name);
    }

    public Attachment deleteAttachment(String name) {
        return this.attachments.remove(name);
    }
    
     public Reply getReply(String name) {
        return this.replies.get(name);
    }

    public Collection<Reply> getReplys() {
        return this.replies.values();
    }

    public int getNumberOfReplies() {
        return this.replies.size();
    }

    public Reply deleteReply(long tid) {
        return this.replies.remove(tid);
    }
}

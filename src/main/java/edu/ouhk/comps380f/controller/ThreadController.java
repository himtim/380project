package edu.ouhk.comps380f.controller;

import edu.ouhk.comps380f.dao.ReplyEntryRepository;
import edu.ouhk.comps380f.dao.ThreadEntryRepository;
import edu.ouhk.comps380f.model.Attachment;
import edu.ouhk.comps380f.model.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import edu.ouhk.comps380f.model.Thread;
import edu.ouhk.comps380f.view.DownloadingView;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping(value = {"forum/{type}"})
public class ThreadController{

    @Autowired
    ThreadEntryRepository tdEntryRepo; 
    @Autowired
    ReplyEntryRepository rEntryRepo;
    
    @RequestMapping(value={"", "listThread"})
    public String index(ModelMap model, @PathVariable("type") String type) {
        model.addAttribute("entries", tdEntryRepo.findAll(type));
        return "listThread";
    }
    
    @RequestMapping(value="addThread", method=RequestMethod.GET)
    public ModelAndView addThreadForm() {
        return new ModelAndView("addThread", "command", new Thread());
    }
    public static class Form {

        private String subject;
        private String body;
        private List<MultipartFile> attachments;

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public List<MultipartFile> getAttachments() {
            return attachments;
        }

        public void setAttachments(List<MultipartFile> attachments) {
            this.attachments = attachments;
        }
    }
    
    @RequestMapping(value="addThread", method=RequestMethod.POST)
    public View addThreadHandle(Thread entry, Form form, @PathVariable("type") String type, Principal principal) throws IOException {
        entry.setCategory(type);
        entry.setUsername(principal.getName());
        int id = tdEntryRepo.create(entry);
        for (MultipartFile filePart : form.getAttachments()) {
            Attachment attachment = new Attachment();
            attachment.setId(id);
            attachment.setName(filePart.getOriginalFilename());
            attachment.setMimeContentType(filePart.getContentType());
            attachment.setContents(filePart.getBytes());
            if (attachment.getName() != null && attachment.getName().length() > 0
                    && attachment.getContents() != null && attachment.getContents().length > 0) {
                tdEntryRepo.createAttachment(attachment);
            }
        }
        return new RedirectView("../listThread", true);
    }
    
    @RequestMapping(value={"viewThread/{id}"})
    public String index(ModelMap model, @PathVariable("id") int id) {
        model.addAttribute("thread", tdEntryRepo.findById(id));
        model.addAttribute("replies", rEntryRepo.findAll(id));
        model.addAttribute("attachments", tdEntryRepo.findAllAttachment(id));
        return "viewThread";
    }
    
    @RequestMapping(value="replyAttachment/{rid}", method=RequestMethod.GET)
    public String replyAttachment(ModelMap model, @PathVariable("rid") int rid){
        model.addAttribute("rid", rid);
        model.addAttribute("attachments", rEntryRepo.findAllAttachment(rid));
        return "replyAttachment";
    }
    
    @RequestMapping(value="addReply/{id}", method=RequestMethod.GET)
    public ModelAndView addReplyForm() {
        return new ModelAndView("addReply", "command", new Reply());
    }
    
    @RequestMapping(value="addReply/{id}", method=RequestMethod.POST)
    public View addThreadHandle(Reply entry, Form form, @PathVariable("id") int id, Principal principal) throws IOException {
        entry.setTid(id);
        entry.setUsername(principal.getName());
        int rid = rEntryRepo.create(entry);
        for (MultipartFile filePart : form.getAttachments()) {
            Attachment attachment = new Attachment();
            attachment.setId(rid);
            attachment.setName(filePart.getOriginalFilename());
            attachment.setMimeContentType(filePart.getContentType());
            attachment.setContents(filePart.getBytes());
            if (attachment.getName() != null && attachment.getName().length() > 0
                    && attachment.getContents() != null && attachment.getContents().length > 0) {
                rEntryRepo.createAttachment(attachment);
            }
        }
        return new RedirectView("../viewThread/{id}", true);
    }
    
    @RequestMapping(
            value = "viewThread/{id}/attachment/{attachment:.+}",
            method = RequestMethod.GET
    )
    public View download(@PathVariable("id") int id,
            @PathVariable("attachment") String name) {
            Attachment attachment = tdEntryRepo.findAttachmentById(id, name);
            if (attachment != null) {
                return new DownloadingView(attachment.getName(),
                        attachment.getMimeContentType(), attachment.getContents());
            }
            return new RedirectView("../viewThread/{id}", true);
    }
    
    @RequestMapping(
            value = "replyAttachment/{id}/attachment/{attachment:.+}",
            method = RequestMethod.GET
    )
    public View download2(@PathVariable("id") int id,
            @PathVariable("attachment") String name) {
            Attachment attachment = rEntryRepo.findAttachmentById(id, name);
            if (attachment != null) {
                return new DownloadingView(attachment.getName(),
                        attachment.getMimeContentType(), attachment.getContents());
            }
            return new RedirectView("../listThread", true);
    }
    
    @RequestMapping(value = "deleteThread/{id}", method = RequestMethod.GET)
    public View deleteThread(@PathVariable("id") int id) {
        tdEntryRepo.deleteThread(id);
        rEntryRepo.deleteReplyOfThread(id);
        return new RedirectView("../listThread", true);
    }
    @RequestMapping(value = "deleteReply/{id}", method = RequestMethod.GET)
    public View deleteReply(@PathVariable("id") int id) {
        rEntryRepo.deleteReply(id);
        return new RedirectView("../listThread", true);
    }

}

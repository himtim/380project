package edu.ouhk.comps380f.controller;

import edu.ouhk.comps380f.dao.UserEntryRepository;
import edu.ouhk.comps380f.model.UserEntry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("user")
public class UserController  {
    @Autowired
    UserEntryRepository uEntryRepo; 
    
    @RequestMapping(value={"", "list"})
    public String index(ModelMap model) {
        model.addAttribute("entries", uEntryRepo.findAll());
        return "listUser";
    }
    
    @RequestMapping(value="register", method=RequestMethod.GET)
    public ModelAndView register() {
        return new ModelAndView("register", "command", new UserEntry());
    }
    
    @RequestMapping(value="register", method=RequestMethod.POST)
    public View registerHandle(UserEntry entry) {
        entry.setRole("ROLE_USER");
        uEntryRepo.create(entry);
        return new RedirectView("../../login", true);
    }
    
    @RequestMapping(value="add", method=RequestMethod.GET)
    public ModelAndView add() {
        return new ModelAndView("addNewUser", "command", new UserEntry());
    }
    
    @RequestMapping(value="add", method=RequestMethod.POST)
    public View addHandle(UserEntry entry) {
        uEntryRepo.create(entry);
        return new RedirectView("../", true);
    }
    
    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public View deleteReply(@PathVariable("id") int id) {
        uEntryRepo.deleteUser(id);
        return new RedirectView("../", true);
    }
    
    public static class Form {
    int uid;
    String username;
    String password;
    String banning;
    String role;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBanning() {
        return banning;
    }

    public void setBanning(String banning) {
        this.banning = banning;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    
    
}
    
    @RequestMapping(value="edit/{id}", method=RequestMethod.GET)
    public ModelAndView edit(ModelMap model, @PathVariable("id") int id) {
        
        UserEntry ue = uEntryRepo.findById(id);
        ModelAndView modelAndView = new ModelAndView("editUser");
        modelAndView.addObject("entry", ue);
        Form userForm = new Form();
        userForm.setUid(ue.getUid());
        userForm.setUsername(ue.getUsername());
        userForm.setPassword(ue.getPassword());
        userForm.setBanning(ue.getBanning());
        userForm.setRole(ue.getRole());
        modelAndView.addObject("userForm", userForm);
        return modelAndView;
    }
    
    @RequestMapping(value="edit/{id}", method=RequestMethod.POST)
    public View editHandle(UserEntry entry, Form form, @PathVariable("id") int id) {
        entry.setUid(id);
        entry.setUsername(form.getUsername());
        entry.setPassword(form.getPassword());
        entry.setBanning(form.getBanning());
        entry.setRole(form.getRole());
        uEntryRepo.update(entry);
        return new RedirectView("../", true);
    }
    
}

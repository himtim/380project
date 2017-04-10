package edu.ouhk.comps380f.controller;

import edu.ouhk.comps380f.dao.UserEntryRepository;
import edu.ouhk.comps380f.model.Poll;
import edu.ouhk.comps380f.model.UserEntry;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class IndexController {
    @Autowired
    UserEntryRepository uEntryRepo; 
    
    Poll mPoll = new Poll();

    @RequestMapping("/")
    public String index(ModelMap model) {
        model.addAttribute("poll", mPoll);
        return "index";
    }

    @RequestMapping("login")
    public String login() {
        return "login";
    }
    
    @RequestMapping(value="register", method=RequestMethod.GET)
    public ModelAndView register() {
        return new ModelAndView("register", "command", new UserEntry());
    }
    
    @RequestMapping(value="register", method=RequestMethod.POST)
    public View registerHandle(UserEntry entry) {
        entry.setRole("ROLE_USER");
        uEntryRepo.create(entry);
        return new RedirectView("./login", true);
    }
    
    @RequestMapping(value = "voted")
    public String voted() {
        return "voted";
    }

    @RequestMapping(value="addPoll", method=RequestMethod.GET)
    public ModelAndView addPoll() {
        return new ModelAndView("addPoll", "command", new Poll());
    }
    
    @RequestMapping(value="addPoll", method=RequestMethod.POST)
    public View addPollHandle(Poll poll) {
        mPoll = poll;
        mPoll.setNoA(0);
        mPoll.setNoB(0);
        mPoll.setNoC(0);
        mPoll.setNoD(0);
        uEntryRepo.updateAllNotVoted();
        return new RedirectView("../", true);
    }

    @RequestMapping(value = "deletePoll", method = RequestMethod.GET)
    public View deletePoll() {
        mPoll = new Poll();
        uEntryRepo.updateAllNotVoted();
        return new RedirectView("../", true);
    }
    
    @RequestMapping(value = "vote/{choice}", method = RequestMethod.GET)
    public View vote(ModelMap model, Principal principal, @PathVariable("choice") String choice) {
        UserEntry ue = uEntryRepo.findByUsername(principal.getName());
        int i = 1;
        if(ue.getVoted().equals("Y")){
            return new RedirectView("../voted", true);
        }
        else{
            if(choice.equals("A")){
                mPoll.setNoA(mPoll.getNoA()+i);
            }
            if(choice.equals("B")){
                mPoll.setNoB(mPoll.getNoB()+i);
            }
            if(choice.equals("C")){
                mPoll.setNoC(mPoll.getNoC()+i);
            }
            if(choice.equals("D")){
                mPoll.setNoD(mPoll.getNoD()+i);
            }
            uEntryRepo.updateVoted(ue);
            return new RedirectView("../", true);
        }
    }

}

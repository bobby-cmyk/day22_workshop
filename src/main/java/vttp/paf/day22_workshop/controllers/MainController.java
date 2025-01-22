package vttp.paf.day22_workshop.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import vttp.paf.day22_workshop.models.RSVP;
import vttp.paf.day22_workshop.services.RsvpService;

@Controller
@RequestMapping()
public class MainController {

    @Autowired
    private RsvpService rsvpSvc;
    
    @GetMapping("/new")
    public ModelAndView addRsvpForm() {

        ModelAndView mav = new ModelAndView();

        RSVP rsvp = new RSVP();

        mav.addObject("rsvp", rsvp);
        mav.setViewName("add-rsvp");

        return mav;
    }

    @PostMapping("/new")
    public ModelAndView addRsvp(@ModelAttribute RSVP rsvp) {
        ModelAndView mav = new ModelAndView();

        rsvpSvc.addRsvp(rsvp);

        mav.setViewName("redirect:/");

        return mav;
    }

    @GetMapping
    public ModelAndView allRsvp() {

        ModelAndView mav = new ModelAndView();

        List<RSVP> rsvps = rsvpSvc.getRsvps();
        int count = rsvpSvc.countRsvp();
        
        mav.addObject("rsvps", rsvps);
        mav.addObject("count", count);
        mav.setViewName("all-rsvp");

        return mav;
    }

    @PostMapping("delete/{id}")
    public ModelAndView deleteRsvpById(@PathVariable(name = "id") int id) {

        ModelAndView mav = new ModelAndView();

        rsvpSvc.deleteRsvp(id);

        mav.setViewName("redirect:/");

        return mav;
    }

    @GetMapping("/update/{id}")
    public ModelAndView getUpdateRsvpForm(@PathVariable(name="id") int id) {
        ModelAndView mav = new ModelAndView();
        
        Optional<RSVP> opt = rsvpSvc.getRsvpById(id);

        if(opt.isEmpty()) {
            mav.setViewName("redirect:/");

            return mav;
        }

        RSVP rsvp = opt.get();

        mav.addObject("rsvp", rsvp);   
        mav.setViewName("update-rsvp");

        return mav;
    }

    @PostMapping("/update/{id}")
    public ModelAndView updateRsvpForm(@PathVariable(name="id") int id, @ModelAttribute RSVP rsvp) {
        ModelAndView mav = new ModelAndView();

        rsvp.setId(id);

        rsvpSvc.updatedRsvp(rsvp);

        mav.setViewName("redirect:/");

        return mav;
    }
}

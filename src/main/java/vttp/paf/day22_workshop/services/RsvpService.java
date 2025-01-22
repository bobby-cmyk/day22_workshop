package vttp.paf.day22_workshop.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.paf.day22_workshop.models.RSVP;
import vttp.paf.day22_workshop.repositories.RsvpRepository;

@Service
public class RsvpService {
    
    @Autowired
    private RsvpRepository rsvpRepo;

    public List<RSVP> getRsvps() {
        return rsvpRepo.getRsvps();
    }

    public Optional<RSVP> getRsvpById(int id) {
        return rsvpRepo.getRsvpById(id);
    }

    public boolean addRsvp(RSVP rsvp) {
        rsvp.setConfirmationDate(LocalDate.now());
        return rsvpRepo.addRsvp(rsvp);
    }

    public boolean deleteRsvp(int id) {
        return rsvpRepo.deleteRsvp(id);
    }

    public boolean updatedRsvp(RSVP rsvp) {
        // Update confirmation date
        rsvp.setConfirmationDate(LocalDate.now());
        return rsvpRepo.updateRsvpById(rsvp);
    }

    public int countRsvp() {
        return rsvpRepo.countRsvp();
    }

}

package vttp.paf.day22_workshop.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp.paf.day22_workshop.models.RSVP;

import static vttp.paf.day22_workshop.models.RSVP.*;

import static vttp.paf.day22_workshop.repositories.Queries.*;

@Repository
public class RsvpRepository {

    @Autowired
    private JdbcTemplate template;

    public List<RSVP> getRsvps() {
        List<RSVP> rsvps = new ArrayList<>();

        SqlRowSet rs = template.queryForRowSet(SQL_GET_ALL_RSVPS);

        while (rs.next()) {
            rsvps.add(toRSVP(rs));
        }

        return rsvps;
    }

    public Optional<RSVP> getRsvpById(int id) {

        SqlRowSet rs = template.queryForRowSet(SQL_GET_RSVP_BY_ID, id);

        if(!rs.next()) {
                return Optional.empty();
        }

        return Optional.of(toRSVP(rs));
    }

    public boolean addRsvp(RSVP newRsvp) {

        int rsvpAdded = template.update(
            SQL_ADD_RSVP, 
            newRsvp.getName(), 
            newRsvp.getEmail(), 
            newRsvp.getPhone(), 
            newRsvp.getConfirmationDate(), 
            newRsvp.getComments());

        if (rsvpAdded > 0) {
            return true;
        }

        return false;
    }

    public boolean deleteRsvp(int id) {
        int rsvpDeleted = template.update(
            SQL_DELETE_RSVP_BY_ID,
            id);

        if (rsvpDeleted > 0) {
            return true;
        }

        return false;
    } 

    public boolean updateRsvpById(RSVP updatedRsvp) {
        
        int rsvpUpdated = template.update(SQL_UPDATE_RSVP_BY_ID, 
                updatedRsvp.getName(),
                updatedRsvp.getEmail(),
                updatedRsvp.getPhone(),
                updatedRsvp.getConfirmationDate(),
                updatedRsvp.getComments(),
                updatedRsvp.getId());

        if (rsvpUpdated>0) {
            return true;
        }

        return false;
    }

    public int countRsvp() {
        SqlRowSet rs = template.queryForRowSet(SQL_RSVP_COUNT);

        rs.next();

        return rs.getInt("count");
    }
    
}

package vttp.paf.day22_workshop.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import vttp.paf.day22_workshop.models.RSVP;
import vttp.paf.day22_workshop.services.RsvpService;

@RestController
@RequestMapping("/api")
public class RsvpRestController {

    @Autowired
    private RsvpService rsvpSvc;
    
    @GetMapping(path="/rsvps", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getRsvps() 
    {
        List<RSVP> rsvps = rsvpSvc.getRsvps();

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();

        for (RSVP rsvp : rsvps) {
            arrBuilder.add(rsvp.toJsonObj());
        }

        return ResponseEntity.ok().body(arrBuilder.build().toString());
    }

    @GetMapping(path="/rsvp", produces=MediaType.APPLICATION_JSON_VALUE) 
    public ResponseEntity<String> getRsvpByName(@RequestParam(name="id") int id)
    {
        Optional<RSVP> opt = rsvpSvc.getRsvpById(id);

        if (opt.isEmpty()) {

            JsonObject jsonObj = Json.createObjectBuilder()
                .add("message", "ID does not exist")
                .build();

            return ResponseEntity.status(404).body(jsonObj.toString());
        }

        RSVP rsvp = opt.get();

        return ResponseEntity.ok().body(rsvp.toJsonObj().toString());
    }

    @DeleteMapping(path="/rsvp/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteRsvp(@PathVariable(name="id") int id) {
        
        if(!rsvpSvc.deleteRsvp(id)) {

            JsonObject jsonObj = Json.createObjectBuilder()
                .add("message", "Operation unsuccessful")
                .build();

            return ResponseEntity.status(500).body(jsonObj.toString());
        }

        JsonObject jsonObj = Json.createObjectBuilder()
                .add("message", "Deleted RSVP id: %d".formatted(id))
                .build();

        return ResponseEntity.ok().body(jsonObj.toString());
    }

    @PostMapping(
        path="/rsvp", 
        consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE, 
        produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addRsvp(
        @RequestParam MultiValueMap<String, String> rsvpForm)
    {   
        RSVP rsvp = new RSVP(
            rsvpForm.getFirst("name"),
            rsvpForm.getFirst("email"),
            rsvpForm.getFirst("phone"),
            rsvpForm.getFirst("comments")
        );
        
        rsvpSvc.addRsvp(rsvp);

        JsonObject jsonObj = Json.createObjectBuilder()
                .add("message", "RSVP successfully added")
                .build();

        return ResponseEntity.status(201).body(jsonObj.toString());
    }

    @PutMapping(
        path="/rsvp/{id}", 
        consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE, 
        produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateRsvp(
        @RequestParam MultiValueMap<String, String> rsvpForm, 
        @PathVariable(name="id") int id) 
    {
        RSVP rsvp = new RSVP(
            rsvpForm.getFirst("name"),
            rsvpForm.getFirst("email"),
            rsvpForm.getFirst("phone"),
            rsvpForm.getFirst("comments")
        );

        // Set id given in the path variable
        rsvp.setId(id);

        if(!rsvpSvc.updatedRsvp(rsvp)) {
            JsonObject jsonObj = Json.createObjectBuilder()
                .add("message", "Unsuccessful update")
                .build();

            return ResponseEntity.status(500).body(jsonObj.toString());
        }

        JsonObject jsonObj = Json.createObjectBuilder()
                .add("message", "RSVP %d updated".formatted(id))
                .build();

        return ResponseEntity.status(201).body(jsonObj.toString());
    }

    @GetMapping(path="/rsvps/count", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> countRsvp(){
        
        int count = rsvpSvc.countRsvp();

        JsonObject jsonObj = Json.createObjectBuilder()
            .add("count", count)
            .build();

        return ResponseEntity.status(201).body(jsonObj.toString());
    }

}

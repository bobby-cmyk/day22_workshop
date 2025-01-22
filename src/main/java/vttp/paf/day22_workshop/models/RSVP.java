package vttp.paf.day22_workshop.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class RSVP {
    private int id;
    private String name;
    private String email;
    private String phone;
    private LocalDate confirmationDate;
    private String comments;

    public RSVP(String name, String email, String phone, String comments) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.comments = comments;
    }

    public RSVP(){};

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public LocalDate getConfirmationDate() {
        return confirmationDate;
    }
    public void setConfirmationDate(LocalDate confirmationDate) {
        this.confirmationDate = confirmationDate;
    }
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }

    public static RSVP toRSVP(SqlRowSet rs) {
        
        RSVP rsvp = new RSVP();

        rsvp.setId(rs.getInt("id"));
        rsvp.setName(rs.getString("name"));
        rsvp.setEmail(rs.getString("email"));
        rsvp.setPhone(rs.getString("phone"));

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(Locale.US);  // Locale specifies human language for translating, and cultural norms for lowercase/uppercase and abbreviations and such. Example: Locale.US or Locale.CANADA_FRENCH
        LocalDate confirminationDate = LocalDate.parse(rs.getString("confirmation_date"), fmt);
        rsvp.setConfirmationDate(confirminationDate);

        rsvp.setComments(rs.getString("comments"));

        return rsvp;
    }

    public JsonObject toJsonObj() {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        builder.add("id", id);
        builder.add("name", name);
        builder.add("email", email);
        builder.add("phone", phone);
        builder.add("confirmation_date", confirmationDate.toString());
        builder.add("comments", comments);

        return builder.build(); 
    }
}
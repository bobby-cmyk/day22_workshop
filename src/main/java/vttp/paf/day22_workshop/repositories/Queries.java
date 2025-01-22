package vttp.paf.day22_workshop.repositories;

public class Queries {
    
    public static String SQL_GET_ALL_RSVPS = """
        SELECT * FROM rsvp     
    """;

    public static String SQL_GET_RSVP_BY_ID = """
        SELECT * FROM rsvp WHERE id = ?
    """;

    public static String SQL_UPDATE_RSVP_BY_ID = """
        UPDATE rsvp 
        SET name = ?, 
            email = ?, 
            phone = ?, 
            confirmation_date = ?, 
            comments = ?  
        WHERE id = ?
    """;

    public static String SQL_ADD_RSVP = """
        INSERT INTO rsvp (name, email, phone, confirmation_date, comments) 
	    VALUES (?, ?, ?, ?, ?)  
    """;

    public static String SQL_DELETE_RSVP_BY_ID = """
        DELETE FROM rsvp
        WHERE id = ?
    """;

    public static String SQL_RSVP_COUNT = """
        SELECT COUNT(*) AS count FROM rsvp
    """;

}

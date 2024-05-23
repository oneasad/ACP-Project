package dataStructure;

import java.sql.Date;
import java.sql.Time;

public class EventClass {

    // <editor-fold desc="Global Declarations">
    private final String title;
    private final Date date;
    private final Time time;
    private final String location;
    private final String description;
    // </editor-fold>

    // <editor-fold desc="Constructor">
    public EventClass(String title, Date date, Time time, String location, String description) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.location = location;
        this.description = description;
    }
    // </editor-fold>

    // <editor-fold desc="Getter Functions">
    public String getTitle() { return title; }
    public Date getDate() { return date; }
    public Time getTime() { return time; }
    public String getLocation() { return location; }
    public String getDescription() { return description; }
    // </editor-fold>
}

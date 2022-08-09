package edu.neu.madcourse.numad22su_team11.Model;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class Event {
    private String eventId;
    private String name;
    private long timeStamp;
    private String creatorId;
    private int category;
    private String locationId;
    private List<String> peopleJoined;

    public Event(){}

    public Event(String name,String creatorId, long timeStamp, String locationId, int category, List<String> peopleJoined, String eventId){
        this.name = name;
        this.creatorId = creatorId;
        this.timeStamp = timeStamp;
        this.locationId = locationId;
        this.category = category;
        this.peopleJoined = peopleJoined;
        this.eventId = eventId;
    }

    // Getter Methods
    public String getName() { return name; }
    public String getCreatorId() {return creatorId; }
    public int getNumPeopleJoined() {
        if (this.getPeopleJoined() == null) {
            return 0;
        }
        return getPeopleJoined().size();
    }
    public List<String> getPeopleJoined() {return peopleJoined;}
    public String getLocationId() {return locationId;}

    public long getTimeStamp() {
        return timeStamp;
    }

    public int getCategory() {
        return category;
    }

    public String getEventId() {
        return eventId;
    }

    // Setter Methods
    public void setName(String name) { this.name = name;}
    public void setCreatorId(String creatorId) {this.creatorId = creatorId;}
    public void setPeopleJoined(List<String> peopleJoined) {this.peopleJoined = peopleJoined;}
    public void setLocationId(String locationId) {this.locationId = locationId;}

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String convertTimestamp() {
        Timestamp ts = new Timestamp(this.timeStamp);
        Date date = new Date(ts.getTime());
        SimpleDateFormat format = new SimpleDateFormat("MMMM dd, yyyy - hh:mm a", Locale.getDefault());
        return format.format(date);
    }

    public static Comparator<Event> eventTimeOldToNew = new Comparator<Event>() {
        @Override
        public int compare(Event o1, Event o2) {
            if (o1.getTimeStamp() - o2.getTimeStamp() > 0) {
                return 1;
            }
            return -1;
        }
    };

    public static Comparator<Event> eventTimeNewToOld = new Comparator<Event>() {
        @Override
        public int compare(Event o1, Event o2) {
            if (o1.getTimeStamp() - o2.getTimeStamp() > 0) {
                return -1;
            }
            return 1;
        }
    };

    public boolean isPastEvent() {
        if (this.getTimeStamp() < System.currentTimeMillis()) {
            return true;
        }
        return false;
    }
}
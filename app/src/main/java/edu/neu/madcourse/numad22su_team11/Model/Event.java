package edu.neu.madcourse.numad22su_team11.Model;

import java.util.List;

public class Event {
    private String name;
    private String time;
    private String creatorId;
    private int category;
    private String locationId;
    private List<User> peopleJoined;

    public Event(){}

    public Event(String name,String creatorId, String time, String locationId, int category){
        this.name = name;
        this.creatorId = creatorId;
        this.time = time;
        this.locationId = locationId;
        this.category = category;
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
    public List<User> getPeopleJoined() {return peopleJoined;}
    public String getLocationId() {return locationId;}
    public String getTime() {return time;}
    public int getCategory() {
        return category;
    }

    // Setter Methods
    public void setName(String name) { this.name = name;}
    public void setCreatorId(String creatorId) {this.creatorId = creatorId;}
    public void setPeopleJoined(List<User> peopleJoined) {this.peopleJoined = peopleJoined;}
    public void setLocationId(String locationId) {this.locationId = locationId;}
    public void setTime(String time) {this.time = time;}
    public void setCategory(int category) {
        this.category = category;
    }
}
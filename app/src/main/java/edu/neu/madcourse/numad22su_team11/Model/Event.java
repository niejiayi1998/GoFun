package edu.neu.madcourse.numad22su_team11.Model;

import java.util.List;

public class Event {
    private String name;
    private String imgUrl;
    private String time;
    private String creatorId;
    private int category;
    private int NumPeopleJoined;
    private String locationId;
    private List<User> peopleJoined;

    public Event(String name,String creatorId, String imgUrl, String time, String locationId){
        this.name = name;
        this.creatorId = creatorId;
        this.imgUrl = imgUrl;
        this.time = time;
        this.locationId = locationId;
    }

    // Getter Methods
    public String getName() { return name; }
    public String getCreatorId() {return creatorId; }
    public String getImgUrl() {return imgUrl; }
    public int getCategory() {return category;}
    public int getNumPeopleJoined() {return NumPeopleJoined;}
    public List<User> getPeopleJoined() {return peopleJoined;}
    public String getLocationId() {return locationId;}
    public String getTime() {return time;}

    // Setter Methods
    public void setName(String name) { this.name = name;}
    public void setCreatorId(String creatorId) {this.creatorId = creatorId;}
    public void setImgUrl(String imgUrl) {this.imgUrl = imgUrl;}
    public void setCategory(int category) {this.category = category;}
    public void setNumPeopleJoined(int numPeopleJoined) {NumPeopleJoined = numPeopleJoined;}
    public void setPeopleJoined(List<User> peopleJoined) {this.peopleJoined = peopleJoined;}
    public void setLocationId(String locationId) {this.locationId = locationId;}
    public void setTime(String time) {this.time = time;}
}
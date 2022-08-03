package edu.neu.madcourse.numad22su_team11.Model;

import java.util.ArrayList;

public class Location {
    private String name;
    private String imgUrl;
    private int category;
    private int numOfLike;
    private double latitude;
    private double longitude;

    public Location() {}

    public Location(String name, String imgUrl, int category, int numOfLike, double latitude, double longitude) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.category = category;
        this.numOfLike = numOfLike;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getNumOfLike() {
        return numOfLike;
    }

    public void setNumOfLike(int numOfLike) {
        this.numOfLike = numOfLike;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

//    public ArrayList<Event> getEvents() {
//        return events;
//    }
//
//    public void setEvents(ArrayList<Event> events) {
//        this.events = events;
//    }
}

package edu.neu.madcourse.numad22su_team11.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
    private final String name;
    private final String email;
    private String password;
    // according to the survey, the indexes represent categories, 0/1 represents chosen or not
    private int[] preferences;
    private final int NUM_OF_CATEGORIES = 6;

    private final String imgUrl = "www.sample.com";
    // [category : frequency]
    private Map<Integer, Integer> likedActivities;
    // events joined
    private List<Event> joinedEvents;
    // events posted
    private List<Event> postedEvents;


    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        preferences = new int[NUM_OF_CATEGORIES];
        likedActivities = new HashMap<>();
        joinedEvents = new ArrayList<>();
        postedEvents = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int[] getPreferences() {
        return preferences;
    }

    public void setPreferences(int[] preferences) {
        this.preferences = preferences;
    }

    public int getNUM_OF_CATEGORIES() {
        return NUM_OF_CATEGORIES;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Map<Integer, Integer> getLikedActivities() {
        return likedActivities;
    }

    public void setLikedActivities(Map<Integer, Integer> likedActivities) {
        this.likedActivities = likedActivities;
    }

    public List<Event> getJoinedEvents() {
        return joinedEvents;
    }

    public void setJoinedEvents(List<Event> joinedEvents) {
        this.joinedEvents = joinedEvents;
    }

    public List<Event> getPostedEvents() {
        return postedEvents;
    }

    public void setPostedEvents(List<Event> postedEvents) {
        this.postedEvents = postedEvents;
    }
}

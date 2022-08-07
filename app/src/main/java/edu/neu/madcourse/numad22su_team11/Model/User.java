package edu.neu.madcourse.numad22su_team11.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
    private final String id;
    private final String name;
    private final String email;
    private String password;
    // according to the survey, the indexes represent categories, 0/1 represents chosen or not
    // private int[] preferences;

    private String imgUrl;
    // [category : frequency]
    private Map<Integer, Integer> likedActivities;
    // events joined
    private List<Event> joinedEvents;
    // events posted
    private List<Event> postedEvents;

    // ATTENTION: all fields should be added to constructor, inorder to add to firebase
    public User(String id,
                String name,
                String email,
                String password,
                String imgUrl,
                Map<Integer, Integer> likedActivities,
                List<Event> joinedEvents,
                List<Event> postedEvents) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        // this.preferences = preferences;
        this.imgUrl = imgUrl;
        this.likedActivities = likedActivities;
        this.joinedEvents = joinedEvents;
        this.postedEvents = postedEvents;
    }

    public String getId() {
        return id;
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

//    public int[] getPreferences() {
//        return preferences;
//    }
//
//    public void setPreferences(int[] preferences) {
//        this.preferences = preferences;
//    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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
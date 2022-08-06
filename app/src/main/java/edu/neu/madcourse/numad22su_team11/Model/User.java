package edu.neu.madcourse.numad22su_team11.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
    private String name;
    private String email;
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
}

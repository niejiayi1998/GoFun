package edu.neu.madcourse.numad22su_team11.Model;


import java.util.Comparator;

public class Location {
    private String name;
    private String imgUrl;
    private int category;
    private int numOfLike;
    private double latitude;
    private double longitude;
    private String locationId;
    private String address;
    private String description;

    public Location() {}

    public Location(String name, String imgUrl, int category, int numOfLike, double latitude, double longitude, String locationId, String address, String description) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.category = category;
        this.numOfLike = numOfLike;
        this.latitude = latitude;
        this.longitude = longitude;
        this.locationId = locationId;
        this.address = address;
        this.description = description;
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

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDistance(double latitude, double longitude) {
        float[] result = new float[1];
        android.location.Location.distanceBetween(latitude, longitude, this.getLatitude(), this.getLongitude(), result);
        double distance = result[0] * 0.000621371;
        return distance;
    }

    public static Comparator<Location> locationLikeComparator = new Comparator<Location>() {
        @Override
        public int compare(Location o1, Location o2) {
            return o2.getNumOfLike() - o1.getNumOfLike();
        }
    };
}

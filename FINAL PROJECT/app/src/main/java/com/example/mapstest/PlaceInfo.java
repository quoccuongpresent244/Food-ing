package com.example.mapstest;

import android.graphics.Bitmap;

import java.util.List;

public class PlaceInfo {
    private String name;
    private String vicinity;
    private String phone;
    private Bitmap photo;
    private String rating;
    private String website;
    private String open_now;
    private String user_ratings_total;
    private String price_level;
    private String distance;
    static List<PlaceInfo> placeInfoList;

    public PlaceInfo(String name, String vicinity, String rating, String open_now, String user_ratings_total, String price_level, String distance) {
        this.name = name;
        this.vicinity = vicinity;
        this.rating = rating;
        this.open_now = open_now;
        this.user_ratings_total = user_ratings_total;
        this.price_level = price_level;
        this.distance = distance;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getOpen_now() {
        return open_now;
    }

    public void setOpen_now(String open_now) {
        this.open_now = open_now;
    }

    public String getUser_ratings_total() {
        return user_ratings_total;
    }

    public void setUser_ratings_total(String user_ratings_total) {
        this.user_ratings_total = user_ratings_total;
    }

    //photo
}

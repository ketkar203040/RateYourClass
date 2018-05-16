package com.example.abhishek.rateyourclass;

public class RatingData {
    private String userId;
    private Float rating;

    public RatingData(String userId, Float rating){
        this.userId = userId;
        this.rating = rating;
    }

    public String getUserId(){
        return userId;
    }

    public Float getRating(){
        return rating;
    }
}

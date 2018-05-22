package com.example.abhishek.rateyourclass;

public class RatingData {
    private String userId;
    private Float rating;
    private String comments;

    RatingData(String userId, Float rating){
        this.userId = userId;
        this.rating = rating;
    }

    RatingData(String userId, Float rating, String comments){
        this.userId = userId;
        this.rating = rating;
        this.comments = comments;
    }

    public String getUserId(){
        return userId;
    }

    public Float getRating(){
        return rating;
    }

    public String getComments() {
        return comments;
    }
}

package com.example.abhishek.rateyourclass;

public class RatingData {
    //private String userId;
    private Float rating;
    private String comments;
    private String subName;
    private String section;
    private String userId;
    private String key;

    RatingData(){}

    RatingData(String userId, Float rating){
        //this.userId = userId;
        this.rating = rating;
    }

    RatingData(String userId, Float rating, String comments){
        //this.userId = userId;
        this.rating = rating;
        this.comments = comments;
    }


    RatingData(String userId ,String subName, String section, String comments, Float rating ){
        this.userId = userId;
        this.subName = subName;
        this.section = section;
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

    public String getSubName(){
        return subName;
    }

    public String getSection() {
        return section;
    }

    public String getKey() {
        return key;
    }

    public void putKey(String key){
        this.key = key;
    }

    public void putSection(String section) {
        this.section = section;
    }

    public void putSubName(String subName){
        this.subName = subName;
    }

    public void putRating(Float rating){
        this.rating = rating;
    }

    public void putComments(String comments){
        this.comments = comments;
    }
}

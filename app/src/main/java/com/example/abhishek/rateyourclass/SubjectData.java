package com.example.abhishek.rateyourclass;

public class SubjectData {
    private String subName;
    private String subLecturer;

    public SubjectData(){

    }

    public SubjectData(String subName){
        this.subName = subName;
    }

    public SubjectData(String subName, String subLecturer){
        this.subName = subName;
        this.subLecturer = subLecturer;
    }

    public String getSubName(){
        return subName;
    }

    public String getSubLecturer(){
        return subLecturer;
    }
}

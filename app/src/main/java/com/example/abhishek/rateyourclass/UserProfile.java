package com.example.abhishek.rateyourclass;

/**
 * Created by Abhishek on 07-05-2018.
 */

public class UserProfile {
    private String firstName;
    private String lastName;
    private String rollNumber;
    private String startYear;
    private String endYear;
    private String department;

    public UserProfile(){

    }

    public UserProfile(String firstName, String lastName, String rollNumber, String department, String startYear, String endYear){
        this.firstName = firstName;
        this.lastName = lastName;
        this.rollNumber = rollNumber;
        this.department = department;
        this.startYear = startYear;
        this.endYear = endYear;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getRollNumber(){
        return rollNumber;
    }

    public String getDepartment(){
        return department;
    }

    public String getStartYear(){
        return startYear;
    }

    public String getEndYear(){
        return endYear;
    }
}

package com.example.abhishek.rateyourclass;

public class ScheduleItem {
    private String className;
    private String classTime;
    private String key;
    private String day;

    ScheduleItem(){}

    ScheduleItem(String className, String classTime){
        this.className = className;
        this.classTime = classTime;
    }

    ScheduleItem(String className, String classTime, String day, String key){
        this.className = className;
        this.classTime = classTime;
        this.day = day;
        this.key = key;
    }

    public String getClassName() {
        return className;
    }

    public String getClassTime() {
        return classTime;
    }

    public String getDay() {
        return day;
    }

    public String getKey() {
        return key;
    }

    public void putKey(String key){
        this.key = key;
    }

    public void putDay(String day){
        this.day = day;
    }
}

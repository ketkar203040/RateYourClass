package com.example.abhishek.rateyourclass;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.example.abhishek.rateyourclass.ReviewForm;
import com.google.firebase.database.Query;

/**
 * Created by Abhishek on 04-05-2018.
 */

public class FirebaseHelper {
    private static FirebaseDatabase firebaseDatabase;
    static DatabaseReference databaseReference;
    static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public static String userId ;
    private static DatabaseReference mUserDataRef;

    static DataSnapshot mDataSnap;


    FirebaseHelper(){

    }

    public static void getDatabase(){
        if (firebaseDatabase == null){
            firebaseDatabase  = FirebaseDatabase.getInstance();
            firebaseDatabase.setPersistenceEnabled(true);
            userId = firebaseAuth.getUid();
            databaseReference = firebaseDatabase.getReference();
            mUserDataRef = databaseReference.child("users").child(userId);
        }

    }

    public void createUserProfile(UserProfile userProfile){
        mUserDataRef.child("userProfile").push().setValue(userProfile);
    }

    public void addSubject(String year, String sem, String dept, SubjectData subjectData){
        databaseReference.child("classes").child(dept).child(year + "-" + sem).push().setValue(subjectData);
    }


    public void submitReview(String subjectName, Float rating, String comments){
        DatabaseReference reviewRef = databaseReference.child("reviews").child(ReviewForm.acYear).child(ReviewForm.dept)
                .child(ReviewForm.studyYear + "-" + ReviewForm.studySem).push();
        String key = reviewRef.getKey();
        reviewRef.setValue(new RatingData(userId, subjectName, ReviewForm.section, comments, rating));

    }

    public void setSchedule(String className, String day, String time){
        DatabaseReference scheduleRef = mUserDataRef.child("schedule").child(day);
        scheduleRef.push().setValue(new ScheduleItem(className, time));
    }


    public void deleteScheduleItem(String day, String key){
        mUserDataRef.child("schedule").child(day).child(key).removeValue();
    }

    public void updateScheduleItem(Map<String, Object> map, String day, String key){
        mUserDataRef.child("schedule").child(day).child(key).updateChildren(map);
    }
}

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

/**
 * Created by Abhishek on 04-05-2018.
 */

public class FirebaseHelper {
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private String userId = firebaseAuth.getUid();
    private DatabaseReference userDataRef = databaseReference.child("users").child(userId);

    //User Info
    private String mStudyYear;
    private String mStudySem;
    private String mDept;




    public FirebaseHelper(){

    }

    public void setYearSem(String studyYear, String studySem, String dept){
        mStudyYear = studyYear;
        mStudySem = studySem;
        mDept = dept;
    }


    public void createUserProfile(UserProfile userProfile){
        userDataRef.push().setValue(userProfile);
    }

    public void addSubject(int year, int sem, String dept, SubjectData subjectData){
        databaseReference.child("classes").child(dept).child(Integer.toString(year)).child(Integer.toString(sem)).push().setValue(subjectData);
    }

    public void submitReview(String subjectName, String section, Float rating, String dept, String year, String sem){
        DatabaseReference reviewRef = databaseReference.child("reviews").child(dept).child(year)
                .child(sem).child(subjectName).child(section);
        reviewRef.setValue(new RatingData(userId, rating));

    }


}

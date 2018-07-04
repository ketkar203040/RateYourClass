package com.example.abhishek.rateyourclass;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.firebase.ui.auth.AuthUI;

public class ViewProfile extends AppCompatActivity {

    private android.support.v7.widget.Toolbar toolbar;

    private TextView nameView, emailView, rollView, deptView, yearView, semView;
    private ImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        //Set up toolbar
        toolbar = findViewById(R.id.profile_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_white_24);
        //actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("User Info");
       // actionBar.setHomeAsUpIndicator(R.drawable.baseline_menu_24);

        //Find all Views
        nameView = findViewById(R.id.profile_name);
        emailView = findViewById(R.id.profile_email);
        rollView = findViewById(R.id.profile_rollno);
        deptView = findViewById(R.id.profile_dept);
        yearView = findViewById(R.id.profile_year);
        semView = findViewById(R.id.profile_sem);
        profileImage = findViewById(R.id.profile_pic);

        nameView.setText(ReviewForm.userName);
        emailView.setText(FirebaseHelper.firebaseAuth.getCurrentUser().getEmail());
        rollView.setText(ReviewForm.rollNo);
        deptView.setText(String.format("%s - %s", ReviewForm.dept, ReviewForm.section));
        yearView.setText(ReviewForm.duration);
        semView.setText(String.format("%s - %s", ReviewForm.studyYear, ReviewForm.studySem));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }
}
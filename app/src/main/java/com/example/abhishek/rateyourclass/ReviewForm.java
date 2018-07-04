package com.example.abhishek.rateyourclass;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.example.abhishek.rateyourclass.FirebaseHelper;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.collection.LLRBNode;

public class ReviewForm extends AppCompatActivity {
    private android.support.v7.widget.Toolbar toolbar;
    // private CollapsingToolbarLayout collapsingToolbar;

    //public static final int RC_SIGN_IN = 100;

    //Firebase
    FirebaseHelper firebaseHelper;
    //private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private ChildEventListener mChildEventListener, nChildEventListener;
    DatabaseReference userDataRef, classRef;



    //UserData
    static String dept;
    static String studyYear;
    static String studySem;
    static String acYear;
    static String section;
    static String userName;
    static String rollNo;
    static String duration;

    //Drawer Layout
    private DrawerLayout drawerLayout;
    private TextView userNameText;
    private TextView userEmailText;
    private DrawerLayout.DrawerListener drawerListener;
    Fragment fragment = null;
    Class fragmentClass = null;

    Bundle mSavedState;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSavedState = savedInstanceState;
        setContentView(R.layout.activity_review_form);
        //Check Authentication
        firebaseAuthCheck();



        //Set up nav drawer
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View navView = navigationView.getHeaderView(0);
        userNameText = navView.findViewById(R.id.user_name);
        userEmailText = navView.findViewById(R.id.user_email);

        //Listener for drawer events
        drawerListener = new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                //For smooth transition load fragment after drawer is closed
                if (fragmentClass != null){
                    try {
                        fragment = (Fragment) fragmentClass.newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //For Schedule Fragment
                    if (fragmentClass == ClassScheduleFragment.class){
                        if (mSavedState == null) {
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                                    .replace(R.id.content_frame, fragment)
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                    .commit();
                        }
                    }
                    else {

                        // Insert the fragment by replacing any existing fragment
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(android.R.animator.fade_in, android.R.anim.slide_out_right)
                                .replace(R.id.content_frame, fragment).commit();
                    }
                }
                //Remove listener after transition
                drawerLayout.removeDrawerListener(drawerListener);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        };

        //Default drawer check item
        navigationView.setCheckedItem(R.id.nav_schedule);
        navigationView.getMenu().findItem(R.id.nav_user_profile).setCheckable(false);


        //Action on selecting option in Drawer
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        //Set up action for selected option
                        int itemId = menuItem.getItemId();
                        switch (itemId){
                            case R.id.nav_logout:
                                AuthUI.getInstance().signOut(ReviewForm.this);
                                detachDatabaseReadListener();
                                //finish();
                                break;

                            case R.id.nav_user_profile:
                                startActivity(new Intent(ReviewForm.this, ViewProfile.class));
                                break;

                            case R.id.nav_review:
                                fragmentClass = ReviewFragment.class;
                                break;

                            case R.id.nav_schedule:
                                fragmentClass = ClassScheduleFragment.class;
                                break;

                            /*case R.id.nav_home:
                                fragmentClass = HomeFragment.class;
                                break;
                                */

                            default:
                                //fragmentClass = ReviewFragment.class;
                                break;
                        }

                        // Close the navigation drawer
                        drawerLayout.closeDrawers();
                        drawerLayout.addDrawerListener(drawerListener);

                        // Highlight the selected item has been done by NavigationView
                        // Set action bar title
                        if (itemId != R.id.nav_user_profile) {
                            menuItem.setChecked(true);
                            setTitle(menuItem.getTitle());
                        }

                        return true;
                    }

                });


        //Set up app bar
        toolbar = findViewById(R.id.anim_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_menu_24);

    }


    //Menu Items (For sign out, etc)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       // MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            /*
            case R.id.dummy_class:
                firebaseHelper = new FirebaseHelper();
                firebaseHelper.addSubject("2", "2", "CSE",new SubjectData("Operating Systems", "John Smith"));
                return true;
                */

            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    //Method checking if user is signed in or not
    public void firebaseAuthCheck(){
        //Auth Listener implementation
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    //onSignedInInitialize(user.getDisplayName());
                    FirebaseHelper.getDatabase();
                    userNameText.setText(user.getDisplayName());
                    userEmailText.setText(user.getEmail());

                    attachDatabaseReadListener();
                }
                else {
                    //firebaseSignIn();
                    detachDatabaseReadListener();
                    Intent intent = new Intent(ReviewForm.this, Login.class);
                    startActivity(intent);
                    finish();
                }

            }
        };
        FirebaseHelper.firebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    //Set up listener to get data from firebase
    private void attachDatabaseReadListener(){
        //Retrieve User data to add reviews
        userDataRef = FirebaseHelper.databaseReference.child("users")
                .child(FirebaseHelper.userId).child("userProfile");
        if (nChildEventListener == null && mChildEventListener == null){
            nChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    int mstartYear = 1;

                    if (dataSnapshot != null) {
                        UserProfile userData = dataSnapshot.getValue(UserProfile.class);
                        dept = userData.getDepartment();
                        rollNo = userData.getRollNumber();
                        mstartYear = Integer.parseInt(userData.getStartYear());
                        duration = Integer.toString(mstartYear) + " - " + Integer.toString(mstartYear + 4);
                        section = userData.getSection();
                        userName = userData.getFirstName() + " " + userData.getLastName();
                        userNameText.setText(userName);
                    }

                    //Get Current year and month
                    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.ENGLISH);
                    SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.ENGLISH);
                    Date curDate = Calendar.getInstance().getTime();
                    int curYear = Integer.parseInt(yearFormat.format(curDate));
                    int curMonth = Integer.parseInt(monthFormat.format(curDate));

                    //Finding year and semester
                    int yearDiff = curYear - mstartYear;
                    if (curMonth <= 6) {
                        studyYear = Integer.toString(yearDiff);
                        studySem = "2";
                        acYear = Integer.toString(curYear - 1) + "-" + Integer.toString(curYear);
                    } else {
                        studyYear = Integer.toString(yearDiff + 1);
                        studySem = "1";
                        acYear = Integer.toString(curYear) + "-" + Integer.toString(curYear + 1);
                    }


                    //Read class data from firebase
                    if (dept != null && studyYear != null && studySem != null) {

                        classRef = FirebaseHelper.databaseReference.child("classes")
                                .child(dept).child(studyYear + "-" + studySem);
                        mChildEventListener = new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                SubjectData data = dataSnapshot.getValue(SubjectData.class);
                                ReviewFragment.dataList.add(data.getSubName());
                                ReviewFragment.adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onChildRemoved(DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        };
                        classRef.addChildEventListener(mChildEventListener);
                        //Start Default fragment

                        if (getSupportFragmentManager().getFragments().isEmpty()){
                            Fragment mFragment = null;
                            Class mFragmentClass = ClassScheduleFragment.class;
                            try {
                                mFragment = (Fragment) mFragmentClass.newInstance();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            // Insert the fragment by replacing any existing fragment
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            fragmentManager.beginTransaction()
                                    .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                                    .replace(R.id.content_frame, mFragment).commit();
                            toolbar.setTitle("Class Schedule");
                        }

                    }

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            userDataRef.addChildEventListener(nChildEventListener);
        }


    }

    //Detach listener for firebase database
    private void detachDatabaseReadListener() {
        if (nChildEventListener != null){
            userDataRef.removeEventListener(nChildEventListener);
            nChildEventListener = null;
        }
        if (mChildEventListener != null){
            classRef.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }
        ReviewFragment.dataList.clear();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAuthStateListener != null){
            FirebaseHelper.firebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
        detachDatabaseReadListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
      //  FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        FirebaseHelper.firebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthStateListener != null){
            FirebaseHelper.firebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
        detachDatabaseReadListener();
    }
}

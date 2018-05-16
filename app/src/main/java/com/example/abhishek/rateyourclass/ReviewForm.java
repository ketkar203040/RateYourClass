package com.example.abhishek.rateyourclass;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

public class ReviewForm extends AppCompatActivity {
    private android.support.v7.widget.Toolbar toolbar;
    // private CollapsingToolbarLayout collapsingToolbar;
    private AppBarLayout appBarLayout;

    public static final int RC_SIGN_IN = 100;

    //Firebase
    FirebaseHelper firebaseHelper;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private ChildEventListener mChildEventListener;

    //RecycleView
    private RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    public List<String> dataList;

    //USerData
    public static String dept;
    public static String studyYear;
    public static String studySem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_form);

        //Check Authentication
        firebaseAuthUi();

        //Initialize firebase helper
        firebaseHelper = new FirebaseHelper();

        //Set up app bar
        toolbar = findViewById(R.id.anim_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getString(R.string.app_name));

        appBarLayout = findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //  Vertical offset == 0 indicates appBar is fully  expanded.
                boolean appBarExpanded;
                if (Math.abs(verticalOffset) > 200) {
                    appBarExpanded = false;
                    invalidateOptionsMenu();
                } else {
                    appBarExpanded = true;
                    invalidateOptionsMenu();
                }
            }
        });


        //Set up Recycler view for subjects
        recyclerView = findViewById(R.id.review_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        dataList = new ArrayList<>();
        adapter = new SubjectListAdapter(dataList);
        recyclerView.setAdapter(adapter);


        //Retrieve User data to add reviews
        DatabaseReference userDataRef = FirebaseDatabase.getInstance().getReference().child("users")
                .child(FirebaseAuth.getInstance().getUid());
        userDataRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                int startYear = 1;

                if (dataSnapshot != null) {
                    UserProfile userData = dataSnapshot.getValue(UserProfile.class);
                    dept = userData.getDepartment();
                    startYear = Integer.parseInt(userData.getStartYear());
                }

                //Get Current year and month
                SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.ENGLISH);
                SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.ENGLISH);
                Date curDate = Calendar.getInstance().getTime();
                int curYear = Integer.parseInt(yearFormat.format(curDate));
                int curMonth = Integer.parseInt(monthFormat.format(curDate));

                //Finding year and semester
                int yearDiff = curYear - startYear;
                if (curMonth <= 7) {
                    studyYear = Integer.toString(yearDiff);
                    studySem = "2";
                } else {
                    studyYear = Integer.toString(yearDiff + 1);
                    studySem = "1";
                }

                //Read class data from firebase
                if (dept != null && studyYear != null && studySem != null) {

                    DatabaseReference classRef = FirebaseDatabase.getInstance().getReference().child("classes")
                            .child("CSE").child("2").child("2");
                    mChildEventListener = new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            SubjectData data = dataSnapshot.getValue(SubjectData.class);
                            dataList.add(data.getSubName());
                            adapter.notifyDataSetChanged();

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
        });


    }



    //}

    //Menu Items (For sign out, etc)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sign_out_menu:
                AuthUI.getInstance().signOut(this);
                return true;

            case R.id.dummy_class:
                firebaseHelper.addSubject(2, 2, "CSE",new SubjectData("Operating Systems", "John Smith"));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    //Method checking if user is signed in or not
    public void firebaseAuthUi(){
        //Auth Listener implementation
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    //onSignedInInitialize(user.getDisplayName());
                }
                else {
                    //firebaseSignIn();
                    Intent intent = new Intent(ReviewForm.this, Login.class);
                    startActivity(intent);
                    finish();
                }

            }
        };
        firebaseAuth.addAuthStateListener(mAuthStateListener);
    }


}

package com.example.abhishek.rateyourclass;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;


public class ReviewFragment extends Fragment {

    //RecycleView
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    public static List<String> dataList = new ArrayList<>();
    private static List<RatingData> ratingData = new ArrayList<>();
    public static RecyclerView.Adapter adapter = new SubjectListAdapter(dataList, ratingData);

    private DatabaseReference revRef;
    private ChildEventListener mChildListener;


    public ReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_review, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Get already existing rating data
        attachReviewDataListener();


        //Set up Recycler view for subjects
        recyclerView = view.findViewById(R.id.review_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        //dataList = new ArrayList<>();
        //adapter = new SubjectListAdapter(dataList);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    //Listener to get existing reviews
    private void attachReviewDataListener(){
        revRef = FirebaseHelper.databaseReference.child("reviews").child(ReviewForm.acYear)
                .child(ReviewForm.dept).child(ReviewForm.studyYear + "-" + ReviewForm.studySem);


        mChildListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                RatingData mRatingData = dataSnapshot.getValue(RatingData.class);
                mRatingData.putKey(dataSnapshot.getKey());
                ratingData.add(mRatingData);
                adapter.notifyDataSetChanged();
                Log.v("KEY", dataSnapshot.getKey());
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
        revRef.addChildEventListener(mChildListener);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onPause() {
        super.onPause();
        ratingData.clear();
        revRef.removeEventListener(mChildListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        ratingData.clear();
        revRef.removeEventListener(mChildListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        attachReviewDataListener();
    }
}

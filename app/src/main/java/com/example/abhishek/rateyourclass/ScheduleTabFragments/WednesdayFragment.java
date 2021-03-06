package com.example.abhishek.rateyourclass.ScheduleTabFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abhishek.rateyourclass.FirebaseHelper;
import com.example.abhishek.rateyourclass.R;
import com.example.abhishek.rateyourclass.ScheduleItem;
import com.example.abhishek.rateyourclass.ScheduleListAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class WednesdayFragment extends Fragment {

    private List<ScheduleItem> scheduleData = new ArrayList<>();
    private RecyclerView.Adapter scheduleAdapter = new ScheduleListAdapter(scheduleData);
    public static final String WEDNESDAY = "WED";
    private DatabaseReference scheduleRef;
    private ChildEventListener childEventListener;
    public WednesdayFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_class_schedule_tab, container, false);


        RecyclerView scheduleList = rootView.findViewById(R.id.schedule_list);
        scheduleList.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        scheduleList.setLayoutManager(mLayoutManager);
        scheduleList.setAdapter(scheduleAdapter);


        //Schedule Data
        scheduleRef = FirebaseDatabase.getInstance().getReference().child("users")
                .child(FirebaseHelper.userId).child("schedule").child(WEDNESDAY);
        //Query query = scheduleRef.orderByValue();
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ScheduleItem scheduleItem = dataSnapshot.getValue(ScheduleItem.class);
                scheduleItem.putKey(dataSnapshot.getKey());
                scheduleItem.putDay(WEDNESDAY);
                scheduleData.add(scheduleItem);
                scheduleAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                for (ScheduleItem item : scheduleData){
                    if (item.getKey().equals(dataSnapshot.getKey())){
                        ScheduleItem scheduleItem = dataSnapshot.getValue(ScheduleItem.class);
                        scheduleItem.putKey(dataSnapshot.getKey());
                        scheduleItem.putDay(WEDNESDAY);
                        int pos = scheduleData.indexOf(item);
                        scheduleData.set(pos, scheduleItem);
                    }
                }
                scheduleAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                for (ScheduleItem item : scheduleData){
                    if (item.getKey().equals(dataSnapshot.getKey())){
                        scheduleData.remove(item);
                    }
                }
                scheduleAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
       // query.addChildEventListener(childEventListener);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(scheduleData.isEmpty()){
            scheduleRef.addChildEventListener(childEventListener);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        scheduleData.clear();
        scheduleRef.removeEventListener(childEventListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        scheduleData.clear();
        scheduleRef.removeEventListener(childEventListener);
    }
}

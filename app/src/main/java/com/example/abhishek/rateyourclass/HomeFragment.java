package com.example.abhishek.rateyourclass;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        CardView classCard = rootView.findViewById(R.id.class_card);
        CardView ratingCard = rootView.findViewById(R.id.rating_card);
        CardView userCard = rootView.findViewById(R.id.user_card);

        classCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSelectedFragment(ClassScheduleFragment.class);

            }
        });

        ratingCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSelectedFragment(ReviewFragment.class);
            }
        });

        userCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ViewProfile.class));
            }
        });



        return rootView;

    }

    private void openSelectedFragment(Class fragmentClass){
        Fragment mFragment = null;
        Class mFragmentClass = fragmentClass;
        try {
            mFragment = (Fragment) mFragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .replace(R.id.content_frame, mFragment).commit();
    }

}

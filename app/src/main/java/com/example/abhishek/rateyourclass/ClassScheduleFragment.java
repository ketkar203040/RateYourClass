package com.example.abhishek.rateyourclass;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abhishek.rateyourclass.ScheduleTabFragments.*;

import java.util.Calendar;


public class ClassScheduleFragment extends Fragment {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private ClassScheduleFragment.SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private FloatingActionButton fab;

    static FragmentManager mFragmentManager;


    public ClassScheduleFragment() {
        // Required empty public constructor
    }


    public static ClassScheduleFragment newInstance(String param1, String param2) {
        ClassScheduleFragment fragment = new ClassScheduleFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FirebaseHelper.attachScheduleListener();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_class_schedule, container, false);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
         mFragmentManager = getChildFragmentManager();
        mSectionsPagerAdapter = new ClassScheduleFragment.SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) rootView.findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        Calendar calendar = Calendar.getInstance();
        int curDay = calendar.get(Calendar.DAY_OF_WEEK);
        switch (curDay){
            case 1:
                mViewPager.setCurrentItem(6);//Sunday Last item 6
                break;
            case 2:
                mViewPager.setCurrentItem(0);//Monday First item 0
                break;

            case 3:
                mViewPager.setCurrentItem(1);//Tuesday
                break;

            case 4:
                mViewPager.setCurrentItem(2);//Wednesday
                break;

            case 5:
                mViewPager.setCurrentItem(3);//Thursday
                break;

            case 6:
                mViewPager.setCurrentItem(4);//Friday
                break;

            case 7:
                mViewPager.setCurrentItem(5);//Saturday
                break;
        }

        fab = rootView.findViewById(R.id.schedule_fab);
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialog = new AddClassDialog();
                dialog.show(getChildFragmentManager(), "AddClassDialog");
            }
        });
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static ClassScheduleFragment.PlaceholderFragment newInstance(int sectionNumber) {
            ClassScheduleFragment.PlaceholderFragment fragment = new ClassScheduleFragment.PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_class_schedule_tab, container, false);
           // TextView textView = (TextView) rootView.findViewById(R.id.section_label);
           // textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));


            return rootView;
        }
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
           // List<ScheduleItem> mScheduleItems = new ArrayList<>();

            if (position == 0){
                return new MondayFragment();
            }
            else if (position == 1){
                return new TuesdayFragment();
            }
            else if (position == 2){
                return new WednesdayFragment();
            }
            else if (position == 3){
                return new ThursdayFragment();
            }
            else if (position == 4){
                return new FridayFragment();
            }
            else if (position == 5){
                return new SaturdayFragment();
            }
            else {
                return new SundayFragment();
            }

            //return ClassScheduleFragment.PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 7 total pages.
            return 7;
        }
    }
}




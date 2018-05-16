package com.example.abhishek.rateyourclass;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class SubjectListAdapter extends RecyclerView.Adapter<SubjectListAdapter.ViewHolder> {

    List<String> values;
    String mDept;
    String mYear;
    String mSem;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView subjectView;
        TextView ratingView;
        TextView ratingText;
        TextView submitText;
        RatingBar ratingBar;
        //public View layout;
        ViewHolder(View v){
            super(v);
            //layout = v;
            subjectView = v.findViewById(R.id.subject_name);
            ratingView = v.findViewById(R.id.rating_value);
            ratingText = v.findViewById(R.id.rating_text);
            ratingBar = v.findViewById(R.id.rating_bar);
            submitText = v.findViewById(R.id.review_submit);
            //Change Text on rating change
            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                    ratingView.setText(String.valueOf(v));
                    if (v == 1.0){
                        ratingText.setText(R.string.oneStar);
                    }
                    else if (v == 2.0){
                        ratingText.setText(R.string.twoStar);
                    }
                    else if (v == 3.0){
                        ratingText.setText(R.string.threeStar);
                    }
                    else if (v == 4.0){
                        ratingText.setText(R.string.fourStar);
                    }
                    else if (v == 5.0){
                        ratingText.setText(R.string.fiveStar);
                    }
                    else{
                        ratingText.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }
    }

    SubjectListAdapter(List<String> myData){
        values = myData;
    }

    public void getUserData(String dept, String year, String sem){
        mDept = dept;
        mYear = year;
        mSem = sem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.class_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String subjectName = values.get(position);
        final Float rating = holder.ratingBar.getRating();
        if(!TextUtils.isEmpty(subjectName)){
            holder.subjectView.setText(subjectName);

            holder.submitText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseHelper firebaseHelper = new FirebaseHelper();
                    //TODO Add sections spinner to edit profile
                    firebaseHelper.submitReview(subjectName, "A", rating, ReviewForm.dept,
                            ReviewForm.studyYear, ReviewForm.studySem);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return values.size();
    }


}

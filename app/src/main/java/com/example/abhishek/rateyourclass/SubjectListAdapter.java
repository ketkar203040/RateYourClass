package com.example.abhishek.rateyourclass;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class SubjectListAdapter extends RecyclerView.Adapter<SubjectListAdapter.ViewHolder> {

    List<String> values;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView subjectView;
        TextView ratingView;
        TextView ratingText;
        TextView submitText;
        RatingBar ratingBar;
        EditText commentsText;

        Float rating;
        String comments;
        ViewHolder(View v){
            super(v);
            //layout = v;
            subjectView = v.findViewById(R.id.subject_name);
            ratingView = v.findViewById(R.id.rating_value);
            ratingText = v.findViewById(R.id.rating_text);
            ratingBar = v.findViewById(R.id.rating_bar);
            submitText = v.findViewById(R.id.review_submit);
            commentsText = v.findViewById(R.id.comments_box);
            //Change Text on rating change
            rating = ratingBar.getRating();
            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                    rating = v;
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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.class_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final String subjectName = values.get(position);


        if(!TextUtils.isEmpty(subjectName)){
            holder.subjectView.setText(subjectName);

            holder.submitText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!TextUtils.isEmpty(holder.commentsText.getText().toString())){
                        holder.comments = holder.commentsText.getText().toString();
                    }
                    else {
                        holder.comments = "No Comments";
                    }
                    FirebaseHelper firebaseHelper = new FirebaseHelper();
                    firebaseHelper.submitReview(subjectName, holder.rating, holder.comments);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return values.size();
    }


}

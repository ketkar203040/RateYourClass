package com.example.abhishek.rateyourclass;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.app.FragmentManager;


import java.util.List;

public class ScheduleListAdapter extends RecyclerView.Adapter<ScheduleListAdapter.ViewHolder> {
    List<ScheduleItem> itemList;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView classNameView;
        TextView classTimeView;
        ImageView btn_options;

        ViewHolder(View v){
            super(v);
            classNameView = v.findViewById(R.id.schedule_classname);
            classTimeView = v.findViewById(R.id.schedule_time);


            //button for more options
            btn_options = v.findViewById(R.id.btn_options);
            btn_options.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(v.getContext(), btn_options);
                    popupMenu.inflate(R.menu.menu_schedule_item);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            int menuId = item.getItemId();
                            switch (menuId){
                                case R.id.btn_edit:
                                    editDialog();
                                    break;

                                case R.id.btn_delete:
                                    deleteItem();
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });

        }


        private void editDialog(){
            //Get the required data
            int position = getAdapterPosition();
            ScheduleItem curData = itemList.get(position);
            android.support.v4.app.DialogFragment dialog = new AddClassDialog();
            Bundle args = new Bundle();
            args.putCharSequence("curClassName", curData.getClassName());
            args.putCharSequence("curDay", curData.getDay());
            Log.v("curDay", curData.getDay());
            args.putCharSequence("curClassTime", curData.getClassTime());
            args.putCharSequence("curKey", curData.getKey());
            dialog.setArguments(args);
            dialog.show(ClassScheduleFragment.mFragmentManager, "AddClassDialog");
        }

        private void deleteItem(){
            int position = getAdapterPosition();
            ScheduleItem curData = itemList.get(position);
            FirebaseHelper firebaseHelper = new FirebaseHelper();
            firebaseHelper.deleteScheduleItem(curData.getDay(), curData.getKey());
        }

    }

    public ScheduleListAdapter(List<ScheduleItem> scheduleItems){itemList = scheduleItems;}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.schedule_list_item, parent, false);
        return new ScheduleListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ScheduleItem item = itemList.get(position);
        String className = "";
        String classTime = "";
        if (item != null){
            className = item.getClassName();
            classTime = item.getClassTime();
        }

        if (!TextUtils.isEmpty(className)){
            holder.classNameView.setText(className);
        }
        if (!TextUtils.isEmpty(classTime)){
            holder.classTimeView.setText(classTime);
        }

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

}

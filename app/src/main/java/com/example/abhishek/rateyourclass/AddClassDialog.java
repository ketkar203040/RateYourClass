package com.example.abhishek.rateyourclass;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddClassDialog extends DialogFragment {
    Spinner classSpinner, daySpinner;
    TextView timeEdit, daySpinnerHeading;
    FirebaseHelper firebaseHelper;
    private String className, classDay, classTime;

    private String mKey, confirmText;
    boolean isEdit;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_class, null);

        confirmText = "Set";

        classSpinner = dialogView.findViewById(R.id.spinner_add_class);
        daySpinner = dialogView.findViewById(R.id.spinner_select_day);
        timeEdit = dialogView.findViewById(R.id.edit_select_time);
        daySpinnerHeading = dialogView.findViewById(R.id.spinner_select_day_heading);

        //Set class spinner
        ArrayAdapter<String> classAdapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, ReviewFragment.dataList);
        classAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        classSpinner.setAdapter(classAdapter);

        //Set Day spinner
        List<String> days = new ArrayList<>();
        days.add("MON");
        days.add("TUE");
        days.add("WED");
        days.add("THU");
        days.add("FRI");
        days.add("SAT");
        days.add("SUN");
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, days);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        daySpinner.setAdapter(dayAdapter);


        //Set existing value for editing items
        try {
            if (!getArguments().isEmpty()){
                classSpinner.setSelection(classAdapter.getPosition(getArguments().getCharSequence("curClassName").toString()));
                daySpinner.setSelection(dayAdapter.getPosition(getArguments().getCharSequence("curDay").toString()));
                Log.v("dDay", getArguments().getCharSequence("curDay").toString());
                timeEdit.setText(getArguments().getCharSequence("curClassTime").toString());
                mKey = getArguments().getCharSequence("curKey").toString();
                confirmText = "Update";
            }

        }catch (Exception e){
            e.printStackTrace();
        }



        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    className = selection;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    classDay = selection;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (confirmText.equals("Update")){
            daySpinner.setVisibility(View.INVISIBLE);
            daySpinnerHeading.setVisibility(View.GONE);
        }

        timeEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int mHour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int mMinute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String getTime = formatTime(hourOfDay) + ":" + formatTime(minute);
                        timeEdit.setText(getTime);

                    }
                }, mHour, mMinute, true);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

        builder.setView(dialogView)
                .setPositiveButton(confirmText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        classTime = timeEdit.getText().toString();
                        firebaseHelper = new FirebaseHelper();
                        // FIRE ZE MISSILES!
                        if (confirmText == "Update"){
                            Map<String, Object> map = new HashMap<>();
                            map.put("className", className);
                            map.put("classTime", classTime);
                            firebaseHelper.updateScheduleItem(map, classDay, mKey);
                        }
                        else {
                            firebaseHelper.setSchedule(className, classDay, classTime);
                        }

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    private String formatTime(int time){
        if (time < 10){
            return "0" + String.valueOf(time);
        }
        else
            return String.valueOf(time);
    }

}

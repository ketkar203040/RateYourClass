package com.example.abhishek.rateyourclass;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EditProfileActivity extends AppCompatActivity {
    private EditText editFirstName, editLastName, editRollNumber;
    private Spinner editDept, editStartYear, editEndYear, editSection;
    private Button btnSubmit;
    private ProgressBar progressBar;

    private String addstartYear, addendYear, adddept, addSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //Get the Views
        editFirstName = findViewById(R.id.first_name);
        editLastName = findViewById(R.id.last_name);
        editRollNumber = findViewById(R.id.rollno);
        editDept = findViewById(R.id.spinner_dept);
        editStartYear = findViewById(R.id.spinner_startYear);
        editEndYear = findViewById(R.id.spinner_endYear);
        editSection = findViewById(R.id.spinner_section);
        progressBar = findViewById(R.id.progressBar);

        setUpSpinners(); //Populate Spinners

        //Submit button OnClick
        btnSubmit = findViewById(R.id.submit_button);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //progressBar.setVisibility(View.VISIBLE);
                submitProfile();

            }
        });
    }

    //Populate the spinners
    private void setUpSpinners(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy", Locale.ENGLISH);
        Date curDate = Calendar.getInstance().getTime();
        int curYear = Integer.parseInt(dateFormat.format(curDate));

        List<String> startList = new ArrayList<>();
        List<String> endList = new ArrayList<>();
        List<String> deptList = new ArrayList<>();
        List<String> sectionList = new ArrayList<>();

        for(int i = 0; i < 5; i++){
            startList.add(Integer.toString(curYear - i));
            endList.add(Integer.toString(curYear + i));
        }

        //Add departments
        deptList.add("CSE");
        deptList.add("ECE");
        deptList.add("EEE");
        deptList.add("MECH");
        deptList.add("CIVIL");

        //Add Sections
        sectionList.add("A");
        sectionList.add("B");
        sectionList.add("C");
        sectionList.add("D");

        //Start List Adapter
        ArrayAdapter<String> startAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, startList);
        startAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editStartYear.setAdapter(startAdapter);

        //End List Adapter
        ArrayAdapter<String> endAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, endList);
        endAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        editEndYear.setAdapter(endAdapter);

        //Dept List Adapter
        ArrayAdapter<String> deptAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, deptList);
        deptAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        editDept.setAdapter(deptAdapter);

        //Section list Adapter
        ArrayAdapter<String> sectionAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, sectionList);
        sectionAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        editSection.setAdapter(sectionAdapter);

        //Action on item selected in spinner
        //Start List listener
        editStartYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selection = (String) adapterView.getItemAtPosition(i);
                if (!TextUtils.isEmpty(selection)) {
                    addstartYear = selection;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //End List listener
        editEndYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selection = (String) adapterView.getItemAtPosition(i);
                if (!TextUtils.isEmpty(selection)) {
                    addendYear = selection;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Department list listener
        editDept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selection = (String) adapterView.getItemAtPosition(i);
                if (!TextUtils.isEmpty(selection)) {
                    adddept = selection;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Section list listener
        editSection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selection = (String) adapterView.getItemAtPosition(i);
                if (!TextUtils.isEmpty(selection)) {
                    addSection = selection;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void submitProfile(){
        String addfirstName = editFirstName.getText().toString();
        String addlastName = editLastName.getText().toString();
        String addrollNumber = editRollNumber.getText().toString();

        //Check if the data is correct
        if (TextUtils.isEmpty(addfirstName)){
            Toast.makeText(this, "Enter First Name!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(addlastName)){
            Toast.makeText(this, "Enter Last Name!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(addrollNumber)){
            Toast.makeText(this, "Enter Roll Number!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (addrollNumber.length() < 8){
            Toast.makeText(this, "Roll Number should be 8 characters!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(adddept)){
            Toast.makeText(this, "Select your Department!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(addSection)){
            Toast.makeText(this, "Select your Section!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(addstartYear)){
            Toast.makeText(this, "Select Start Year!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(addendYear)){
            Toast.makeText(this, "Select End Year!", Toast.LENGTH_SHORT).show();
            return;
        }

        UserProfile userProfile = new UserProfile(addfirstName, addlastName, addrollNumber, adddept, addstartYear, addendYear, addSection);

        FirebaseHelper firebaseHelper = new FirebaseHelper();
        firebaseHelper.createUserProfile(userProfile);

        startActivity(new Intent(EditProfileActivity.this, ReviewForm.class));
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);

    }


}

package com.example.caleo_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ViewGroup;

import com.example.caleo_app.models.User;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class OnboardingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    public static final String TAG = "OnboardingActivity";
    private TextView tvWelcome;
    private EditText etName;
    private EditText etAge;
    private EditText etHeight;
    private EditText etWeight;
    private EditText etCalGoal;
    private Spinner spinner;
    private Button btnHint;
    private Button btnSave;
    private User user = new User();

    List<String> info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        loadItems();
        if (!info.isEmpty()){
            Toast.makeText(this, info.get(0), Toast.LENGTH_LONG).show();
            goMainActivity();
        }


//        if (current == 1){
//            goMainActivity();
//        }


        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        etHeight = findViewById(R.id.etHeight);
        etWeight = findViewById(R.id.etWeight);
        etCalGoal = findViewById(R.id.etCalGoal);
        btnSave = findViewById(R.id.btnSave);
        btnHint = findViewById(R.id.btnHint);

        String[] gender = {"Male", "Female"};
        spinner = findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(OnboardingActivity.this,
                android.R.layout.simple_spinner_item, gender);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);



        btnHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick hint button");
                String age = etAge.getText().toString();
                String height = etHeight.getText().toString();
                String weight = etWeight.getText().toString();
                String gender = user.getGender();



                if(age.isEmpty() || height.isEmpty() || weight.isEmpty() || gender.isEmpty()){
                    Toast.makeText(OnboardingActivity.this,"Please enter your age, gender, height, and weight", Toast.LENGTH_SHORT).show();
                }
                else {
                    int goal = (int)(10*Integer.parseInt(weight) + 6.25*Integer.parseInt(height)-5*Integer.parseInt(age));
                    if(gender.equals("male")){
                        goal += 5;
                    }
                    else{
                        goal -= 161;
                    }
                    Toast.makeText(OnboardingActivity.this, "Your recommended daily calorie goal is " + goal, Toast.LENGTH_LONG).show();
                }


            }

        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick save button");
                user.setName(etName.getText().toString());
                user.setAge(Integer.parseInt(etAge.getText().toString()));
                user.setHeight(Integer.parseInt(etHeight.getText().toString()));
                user.setWeight(Integer.parseInt(etWeight.getText().toString()));
                user.setCalGoal(Integer.parseInt(etCalGoal.getText().toString()));

                String name = etName.getText().toString();
                String age = etAge.getText().toString();
                String height = etHeight.getText().toString();
                String weight = etWeight.getText().toString();
                String gender = user.getGender();
                String calGoal = etCalGoal.getText().toString();

                info.add(name);
                info.add(age);
                info.add(height);
                info.add(weight);
                info.add(gender);
                info.add(calGoal);
                saveItems();
                goMainActivity();
            }

        });
    }




    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:
                ((TextView) v).setTextColor(ContextCompat.getColor(OnboardingActivity.this, R.color.cadet));
                ((TextView) v).setTextSize(18);
                user.setGender("male");
                break;
            case 1:
                ((TextView) v).setTextColor(ContextCompat.getColor(OnboardingActivity.this, R.color.cadet));
                ((TextView) v).setTextSize(18);
                user.setGender("female");
                break;
            default:
                ((TextView) v).setTextColor(ContextCompat.getColor(OnboardingActivity.this, R.color.cadet));
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }


    private File getDataFile(){
        return new File(getFilesDir(), "data.txt");
    }

    private void loadItems(){
        try{
            info = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        }
        catch (IOException e){
            Log.e("OnboardingActivity", "Error reading items", e);
            info = new ArrayList<>();
        }

    }

    private void saveItems(){
        try{
            FileUtils.writeLines(getDataFile(), info);
        }
        catch (IOException e){
            Log.e("OnboardingActivity", "Error writing items", e);
        }
    }

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
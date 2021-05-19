package com.example.caleo_app;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caleo_app.fragments.HomeFragment;
import com.example.caleo_app.fragments.ProfileFragment;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class EditActivity extends AppCompatActivity {
    List<String> info = new ArrayList<String>();

    private View ivSolidEdit;
    private TextView tvProfileEdit;
    private TextView tvNameEdit;
    private EditText etNameInputEdit;
    private TextView tvAgeEdit;
    private EditText etAgeInputEdit;
    private TextView tvHeightEdit;
    private EditText etHeightInputEdit;
    private TextView tvWeightEdit;
    private EditText etWeightInputEdit;
    private TextView tvCalGoalEdit;
    private EditText etCalGoalInputEdit;
    private Button btnSaveEdit;
    private Button btnHintEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                goMainActivity();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);


        setContentView(R.layout.activity_edit);
        loadItems();
        ivSolidEdit = findViewById(R.id.ivSolidEdit);
        tvProfileEdit = findViewById(R.id.tvProfileEdit);
        tvNameEdit = findViewById(R.id.tvNameEdit);
        etNameInputEdit = findViewById(R.id.etNameInputEdit);
        etNameInputEdit.setText(info.get(0));
        tvAgeEdit = findViewById(R.id.tvAgeEdit);
        etAgeInputEdit = findViewById(R.id.etAgeInputEdit);
        etAgeInputEdit.setText(info.get(1));
        tvHeightEdit = findViewById(R.id.tvHeightEdit);
        etHeightInputEdit = findViewById(R.id.etHeightInputEdit);
        etHeightInputEdit.setText(info.get(2));
        tvWeightEdit = findViewById(R.id.tvWeightEdit);
        etWeightInputEdit = findViewById(R.id.etWeightInputEdit);
        etWeightInputEdit.setText(info.get(3));
        tvCalGoalEdit = findViewById(R.id.tvCalGoalEdit);
        etCalGoalInputEdit = findViewById(R.id.etCalGoalInputEdit);
        etCalGoalInputEdit.setText(info.get(4));
        btnHintEdit = findViewById(R.id.btnHintEdit);
        btnHintEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("EditActivity", "onClick hint button");
                String age = etAgeInputEdit.getText().toString();
                String height = etHeightInputEdit.getText().toString();
                String weight = etWeightInputEdit.getText().toString();
                String gender = info.get(4);



                if(age.isEmpty() || height.isEmpty() || weight.isEmpty() || gender.isEmpty()){
                    Toast.makeText(EditActivity.this,"Please enter your age, gender, height, and weight", Toast.LENGTH_SHORT).show();
                }
                else {
                    int goal = (int)(10*Integer.parseInt(weight) + 6.25*Integer.parseInt(height)-5*Integer.parseInt(age));
                    if(gender.equals("male")){
                        goal += 5;
                    }
                    else{
                        goal -= 161;
                    }
                    Toast.makeText(EditActivity.this, "Your recommended daily calorie goal is " + goal, Toast.LENGTH_LONG).show();
                }


            }

        });
        btnSaveEdit = findViewById(R.id.btnSaveEdit);
        btnSaveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateInfo();
                saveItems();
                goMainActivity();
            }
        });
    }

    private void populateInfo() {
        String gender = info.get(4);
        String calCount = info.get(6);
        info.clear();
        info.add(etNameInputEdit.getText().toString());
        info.add(etAgeInputEdit.getText().toString());
        info.add(etHeightInputEdit.getText().toString());
        info.add(etWeightInputEdit.getText().toString());
        info.add(etCalGoalInputEdit.getText().toString());
        info.add(gender);
        info.add(calCount);
    }

    private File getDataFile(){
        return new File(getFilesDir(), "data.txt");
    }

    private void saveItems(){
        try{
            FileUtils.writeLines(getDataFile(), info);
        }
        catch (IOException e){
            Log.e("EditActivity", "Error writing items", e);
        }
    }

    private void loadItems() {
        try {
            info = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("ProfileFragment", "Error reading items", e);
            info = new ArrayList<>();
        }
    }

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
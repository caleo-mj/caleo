package com.example.caleo_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        btnSaveEdit = findViewById(R.id.btnSaveEdit);
        btnSaveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateInfo();
                saveItems();
                goProfileActivity();
            }
        });
    }

    private void populateInfo() {
        info.clear();
        info.add(etNameInputEdit.getText().toString());
        info.add(etAgeInputEdit.getText().toString());
        info.add(etHeightInputEdit.getText().toString());
        info.add(etWeightInputEdit.getText().toString());
        info.add(etCalGoalInputEdit.getText().toString());
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

    private void goProfileActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
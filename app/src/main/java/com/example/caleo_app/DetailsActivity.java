package com.example.caleo_app;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caleo_app.ml.Model;
import com.example.caleo_app.models.Food;
import com.opencsv.CSVReaderHeaderAware;

import org.apache.commons.io.FileUtils;
import org.tensorflow.lite.DataType;

import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    protected ImageView ivImage;
    protected TextView tvPrediction;
    protected TextView tvCal;
    protected Button btnLog;
    private Bitmap pic;
    private List<String> info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ivImage = findViewById(R.id.ivImage);
        tvPrediction = (TextView) findViewById(R.id.tvPrediction);
        tvCal = findViewById(R.id.tvCal);
        btnLog = findViewById(R.id.btnLog);
        handleInput();
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadItems();
                info.set(6, Integer.toString(Integer.parseInt(info.get(6).split(" ")[0]) + Integer.parseInt(tvCal.getText().toString().split(" ")[0])) + " cal.");
                saveItems();
                goMainActivity();
            }
        });




    }

    private File getDataFile(){
        return new File(getFilesDir(), "data.txt");
    }


    private void loadItems(){
        try{
            info = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        }
        catch (IOException e){
            Log.e("ProfileFragment", "Error reading items", e);
            info = new ArrayList<>();
        }

    }

    public int getMax(int[] intArray){
        int index = 0;
        int max = 0;
        for (int i = 0; i<intArray.length; i++){
            if(intArray[i]> max) {
                index = i;
                max = intArray[i];
            }
        }
        return index;
    }


    private String loadItems(int goal){
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("labels.txt"), "UTF-8"));

            // do reading, usually loop until end of file reading
            String mLine;
            int i = 0;
            while ((mLine = reader.readLine()) != null) {
                if (i == goal){
                    return mLine.split(" ")[1];
                }
                i++;
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }

        return "Index out of bound";

    }

    private int getIndex(String food){
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("labels.txt"), "UTF-8"));

            // do reading, usually loop until end of file reading
            String mLine;
            int i = 0;
            while ((mLine = reader.readLine()) != null) {
                if (mLine.split(" ")[1].equals(food)){
                    return Integer.parseInt(mLine.split(" ")[0]);
                }
                i++;
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }

        return -1;

    }

    private int getCal(String food) {
        int result = 0;
        try {
            ArrayList<String[]>	myEntries = new	ArrayList<String[]>();


            InputStreamReader is = new InputStreamReader(getAssets()
                    .open("food-calories.csv"));

            BufferedReader reader = new BufferedReader(is);
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                myEntries.add(line.split(","));
            }


            result =  Integer.parseInt(myEntries.get(getIndex(food))[1]);

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    private void saveItems(){
        try{
            FileUtils.writeLines(getDataFile(), info);
        }
        catch (IOException e){
            Log.e("DetailsActivity", "Error writing items", e);
        }
    }

    protected void handleInput() {
        pic = (Bitmap) getIntent().getParcelableExtra("pic");
        ivImage.setImageBitmap(pic);
        pic = Bitmap.createScaledBitmap(pic, 224, 224, true);
//
        try {
            Model model = Model.newInstance(getApplicationContext());

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.UINT8);

            TensorImage tensorImage = new TensorImage(DataType.UINT8);
            tensorImage.load(pic);
            ByteBuffer byteBuffer = tensorImage.getBuffer();

            inputFeature0.loadBuffer(byteBuffer);

            // Runs model inference and gets result.
            Model.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            // Releases model resources if no longer used.
            model.close();

            int max = getMax(outputFeature0.getIntArray());


//            tvPrediction.setText(outputFeature0.getIntArray()[max]+ "\n");
            tvPrediction.setText(loadItems(max));
            tvCal.setText(Integer.toString(getCal(loadItems(max))) + " cal.");
        } catch (IOException e) {
            Toast.makeText(this, "Redo ml", Toast.LENGTH_SHORT).show();
        }
    }
    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

}
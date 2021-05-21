package com.example.caleo_app;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caleo_app.ml.Model;

import org.apache.commons.io.FileUtils;
import org.tensorflow.lite.DataType;

import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    private ImageView ivImage;
    private TextView tvPrediction;
    private Bitmap pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ivImage = findViewById(R.id.ivImage);
        tvPrediction = (TextView) findViewById(R.id.tvPrediction);
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
        } catch (IOException e) {
            Toast.makeText(this, "a7a b2a yoooh", Toast.LENGTH_SHORT).show();
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
                    return mLine.substring(2);
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
}
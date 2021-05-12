package com.example.caleo_app;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

public class DetailsActivity extends AppCompatActivity {

    private ImageView ivImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ivImage = findViewById(R.id.ivImage);
//        Bitmap pic = (Bitmap) getIntent().getParcelableExtra("pic");
        Bitmap pic = (Bitmap) getIntent().getParcelableExtra("pic");
        ivImage.setImageBitmap(pic);
    }
}
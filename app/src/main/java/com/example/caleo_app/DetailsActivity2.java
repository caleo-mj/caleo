package com.example.caleo_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.example.caleo_app.models.Food;

import org.parceler.Parcels;

import java.io.InputStream;

public class DetailsActivity2 extends DetailsActivity {

    @Override
    protected void handleInput() {
        Food foodItem = (Food) Parcels.unwrap(getIntent().getParcelableExtra("food"));
        tvPrediction.setText(foodItem.getName());
        tvCal.setText(foodItem.getCalories());
        ivImage.setImageDrawable(loadDrawableFromAssets(this, foodItem.getImage() + ".jpg"));
    }

    public static Drawable loadDrawableFromAssets(Context context, String path)
    {
        InputStream stream = null;
        try
        {
            stream = context.getAssets().open(path);
            return Drawable.createFromStream(stream, null);
        }
        catch (Exception ignored) {} finally
        {
            try
            {
                if(stream != null)
                {
                    stream.close();
                }
            } catch (Exception ignored) {}
        }
        return null;
    }

}
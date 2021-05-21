package com.example.caleo_app.models;

import android.content.Context;
import android.graphics.drawable.Drawable;

import org.parceler.Parcel;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



@Parcel
public class Food {
     String name;
     Drawable image;
     int calories;

     public Food(String name, Drawable image, int calories) {
        this.name = name;
        this.image = image;
        this.calories = calories;
    }

    // empty constructor needed by the Parceler library
    public Food() {

    }

    public String getName() {
        return name;
    }

    public Drawable getImage() {
        return image;
    }

    public int getCalories() {
        return calories;
    }

    public static List<Food> getFood(Context context, ArrayList<String[]> foodData) {
        List<Food> food = new ArrayList<>();
        for (int i = 0; i < foodData.size(); i++) {
            // populating movies list
            food.add(new Food(foodData.get(i)[0], loadDrawableFromAssets(context, i + ".jpg"), Integer.parseInt(foodData.get(i)[1])));
        }
        return food;
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

package com.example.caleo_app.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.parceler.Parcels;

import com.example.caleo_app.DetailsActivity;
import com.example.caleo_app.DetailsActivity2;
import com.example.caleo_app.R;
import com.example.caleo_app.models.Food;

import java.io.InputStream;
import java.util.List;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class FoodAdapter extends
        RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    Context context;
    List<Food> food;

    public FoodAdapter(Context context, List<Food> food) {

        this.context = context;
        this.food = food;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // Get the movie based on position
        Food foodItem = food.get(position);
        // Bind the movie data into the View Holder (We created this method)
        holder.bind(foodItem);
    }

    @Override
    public int getItemCount() {
        return food.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rlContainer;
        ImageView ivImg;
        TextView tvFood;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rlContainer = itemView.findViewById(R.id.rlContainer);
            ivImg = itemView.findViewById(R.id.ivImg);
            tvFood = itemView.findViewById(R.id.tvFood);

        }

        public void bind(Food foodItem) {
            tvFood.setText(foodItem.getName());
            ivImg.setImageDrawable(loadDrawableFromAssets(context, foodItem.getImage()+".jpg"));
            // Setting up intents to navigate to detail activity
            rlContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // first parameter is the context, second is the class of the activity to launch
                    Intent i =  new Intent(context, DetailsActivity2.class);
                    i.putExtra("food", Parcels.wrap(foodItem));
                    context.startActivity(i);
                }
            });

        }


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
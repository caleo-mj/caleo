package com.example.caleo_app.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.caleo_app.MainActivity;
import com.example.caleo_app.R;
import com.example.caleo_app.adapters.FoodAdapter;
import com.example.caleo_app.models.Food;
import com.opencsv.CSVReaderHeaderAware;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FoodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FoodFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public List<Food> food;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FoodFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FoodFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FoodFragment newInstance(String param1, String param2) {
        FoodFragment fragment = new FoodFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        getActivity().setContentView(R.layout.activity_main);

        try {
            CSVReaderHeaderAware dataReadIn	= new CSVReaderHeaderAware(new FileReader("food-calories.csv"));
            ArrayList<String[]>	myEntries = new	ArrayList<String[]>(dataReadIn.readAll());



            food = Food.getFood(getContext(), myEntries);
            dataReadIn.close();

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Lookup the recyclerview in activity layout
        RecyclerView rvFood = (RecyclerView) getActivity().findViewById(R.id.rvFood);
        // Create adapter passing in the sample user data
        FoodAdapter adapter = new FoodAdapter(getActivity(), food);
        // Attach the adapter to the recyclerview to populate items
        rvFood.setAdapter(adapter);
        // Set layout manager to position the items
        // First param is number of columns and second param is orientation i.e Vertical or Horizontal
//        StaggeredGridLayoutManager gridLayoutManager =
//                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
// Attach the layout manager to the recycler view
        rvFood.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // That's all!

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.flContainer, new HomeFragment()).commit();
                MainActivity.bottomNavigationView.setSelectedItemId(R.id.action_home);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food, container, false);
    }

    private void goMainActivity() {
        Intent i = new Intent(getActivity(), MainActivity.class);
        startActivity(i);
        getActivity().finish();
    }
}
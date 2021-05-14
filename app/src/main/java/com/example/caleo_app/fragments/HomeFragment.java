package com.example.caleo_app.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caleo_app.R;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private TextView tvDate;
    private ProgressBar progress_bar;
    private TextView progressCalNum;
    private TextView progressCalRem;
    private TextView tvGoalNum;
    private TextView tvGoalRem;
    private TextView tvFoodNum;
    private TextView tvFoodRem;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    List<String> info;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tvDate = view.findViewById(R.id.tvDate);
        progress_bar = view.findViewById(R.id.progress_bar);
        progressCalNum = view.findViewById(R.id.progressCalNum);
        progressCalRem = view.findViewById(R.id.progressCalRem);
        tvGoalNum = view.findViewById(R.id.tvGoalNum);
        tvGoalRem = view.findViewById(R.id.tvGoalRem);
        tvFoodNum = view.findViewById(R.id.tvFoodNum);
        tvFoodRem = view.findViewById(R.id.tvFoodRem);
        setInfo();
    }

    private File getDataFile(){
        return new File(getActivity().getFilesDir(), "data.txt");
    }

    private void loadItems(){
        try{
            info = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        }
        catch (IOException e){
            Log.e("HomeFragment", "Error reading items", e);
            info = new ArrayList<>();
        }

    }

    private void setInfo() {
        loadItems();
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
        date = dateFormat.format(calendar.getTime());
        tvDate.setText(date);
        int progress = (int) ((Double.parseDouble(info.get(6)) / Double.parseDouble(info.get(4)) * 100));
        progress_bar.setProgress(progress);
        progressCalNum.setText(Integer.toString((Integer.parseInt(info.get(4)) - Integer.parseInt(info.get(6)))));
        tvGoalNum.setText(info.get(4));
        tvFoodNum.setText(info.get(6));
    }
}
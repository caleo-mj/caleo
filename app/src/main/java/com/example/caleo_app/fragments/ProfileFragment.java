package com.example.caleo_app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.caleo_app.EditActivity;
import com.example.caleo_app.MainActivity;
import com.example.caleo_app.R;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    List<String> info;


    private View ivSolid;
    private TextView tvProfile;
    private TextView tvName;
    private TextView tvNameInput;
    private TextView tvAge;
    private TextView tvAgeInput;
    private TextView tvHeight;
    private TextView tvHeightInput;
    private TextView tvWeight;
    private TextView tvWeightInput;
    private TextView tvCalGoal;
    private TextView tvCalGoalInput;
    private Button btnEdit;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivSolid = view.findViewById(R.id.ivSolid);
        tvProfile = view.findViewById(R.id.tvProfile);
        tvName = view.findViewById(R.id.tvName);
        tvNameInput = view.findViewById(R.id.tvNameInput);
        tvAge = view.findViewById(R.id.tvAge);
        tvAgeInput = view.findViewById(R.id.tvAgeInput);
        tvHeight = view.findViewById(R.id.tvHeight);
        tvHeightInput = view.findViewById(R.id.tvHeightInput);
        tvWeight = view.findViewById(R.id.tvWeight);
        tvWeightInput = view.findViewById(R.id.tvWeightInput);
        tvCalGoal = view.findViewById(R.id.tvCalGoal);
        tvCalGoalInput = view.findViewById(R.id.tvCalGoalInput);
        setInfo();
        btnEdit = view.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goEditActivity();
            }
        });

    }


    private void setInfo() {
        loadItems();
        tvNameInput.setText(info.get(0));
        tvAgeInput.setText(info.get(1));
        tvHeightInput.setText(info.get(2) + " cm");
        tvWeightInput.setText(info.get(3) + " kg");
        tvCalGoalInput.setText(info.get(4) + " cal.");
    }

    private File getDataFile(){
        return new File(getActivity().getFilesDir(), "data.txt");
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

    private void goEditActivity() {
        Intent i = new Intent(getActivity(), EditActivity.class);
        startActivity(i);
        getActivity().finish();
    }

    private void goMainActivity() {
        Intent i = new Intent(getActivity(), MainActivity.class);
        startActivity(i);
        getActivity().finish();
    }
}
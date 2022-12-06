package com.example.planner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class AddProblemActivity extends AppCompatActivity {

    private FragmentManager manager;
    private AddProblemFragment addProblemFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_problem);

        manager = getSupportFragmentManager();
        addProblemFragment = new AddProblemFragment();

        FragmentTransaction tf = manager.beginTransaction();
        tf.replace(R.id.add_problem_fragment_container, addProblemFragment);
        tf.commit();
    }
}
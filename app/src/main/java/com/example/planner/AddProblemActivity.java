package com.example.planner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);  //액티비티의 앱바 지정


        ActionBar actionBar = getSupportActionBar();
        //Title text 작성
        actionBar.setTitle("문제 추가");
        //뒤로가기 버튼 추가
        actionBar.setDisplayHomeAsUpEnabled(true);

        manager = getSupportFragmentManager();
        addProblemFragment = new AddProblemFragment();

        FragmentTransaction tf = manager.beginTransaction();
        tf.replace(R.id.add_problem_fragment_container, addProblemFragment);
        tf.commit();
    }
}
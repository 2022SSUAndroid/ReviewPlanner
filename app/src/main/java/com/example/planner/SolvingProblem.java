package com.example.planner;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Map;

public class SolvingProblem extends AppCompatActivity {

    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solving_problem);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);  //액티비티의 앱바 지정


        ActionBar actionBar = getSupportActionBar();
        //Title text 작성
        actionBar.setTitle("문제 풀기");
        //뒤로가기 버튼 추가
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        ProblemObj problemObj = (ProblemObj) intent.getSerializableExtra("problemObj");

        Bundle result = new Bundle();
        result.putSerializable("bundlepro", problemObj);


        manager = getSupportFragmentManager();
        SolvingProblemFragment solvingProblemFragment = new SolvingProblemFragment();
        solvingProblemFragment.setArguments(result);
        FragmentTransaction tf = manager.beginTransaction();
        tf.replace(R.id.new_fragment, solvingProblemFragment);
        tf.addToBackStack(null);
        tf.commit();

    }


}
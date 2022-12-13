package com.example.planner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SolvingProblem extends AppCompatActivity {
    ImageView correctBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solving_problem);

        ActionBar ac = getSupportActionBar();
        ac.setTitle("문제 풀기");


        correctBtn = findViewById(R.id.correct_button);
        correctBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });

    }

    void showAlertDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("남은 복습 일정을 계속 진행하시겠습니까?");
        builder.setPositiveButton("O", null);
        builder.setNegativeButton("X",null);
        builder.show();
    }
}
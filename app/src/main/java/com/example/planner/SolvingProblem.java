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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Map;

public class SolvingProblem extends AppCompatActivity {
    ImageView correctBtn;
    ImageView incorrectBtn;
    ProblemDto problemDto;
    ProblemObj problemObj;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solving_problem);

        ActionBar ac = getSupportActionBar();
//        ac.setTitle("문제 풀기");


        Intent intent = getIntent();
        problemObj = (ProblemObj) intent.getSerializableExtra("problemObj");
//        problemObj = new ProblemObj((Map<String, Object>) intent.getSerializableExtra("class"));


//        Log.d("problemObj", "category : " + problemObj.getCategory());
//        Log.d("problemObj", "name : " + problemObj.getProblemName());
//        Log.d("problemObj", "cycle : " + problemObj.getCycle().toString());
//        Log.d("problemObj", "tag : " + problemObj.getReviewTag().toString());
//        Log.d("problemObj", "problemImg : " + problemObj.getProblemImg());


        correctBtn = findViewById(R.id.correct_button);
        incorrectBtn = findViewById(R.id.incorrect_button);

        correctBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });

        incorrectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                // 프래그먼트매니저를 통해 사용
                ReviewAgainFragment reviewAgainFragment= new ReviewAgainFragment(); // 객체 생성
                transaction.replace(R.id.add_problem_fragment_container, reviewAgainFragment); //layout, 교체될 layout
                transaction.commit();
            }
        });


    }

    void showAlertDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("남은 복습 일정을 계속 진행하시겠습니까?");
        builder.setPositiveButton("O", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                problemObj.addOX(true);
                problemObj.getMySolving().add("");

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("X", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                problemObj.addOX(true);
                problemObj.getMySolving().add("");
                problemObj.getReviewDay().clear();

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);

            }
        });
        builder.show();
    }


}
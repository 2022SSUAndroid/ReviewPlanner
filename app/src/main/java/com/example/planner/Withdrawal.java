package com.example.planner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Withdrawal extends AppCompatActivity {
    TextView back;
    Button withdrawal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal);

        back = findViewById(R.id.back);
        back.setOnClickListener(v -> onBackPressed());

        withdrawal = findViewById(R.id.withdrawalbutton);
        withdrawal.setOnClickListener(v -> {
            Toast.makeText(Withdrawal.this, "탈퇴 완료", Toast.LENGTH_LONG).show();
        });

    }
}
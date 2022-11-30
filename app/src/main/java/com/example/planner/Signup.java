package com.example.planner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Signup extends AppCompatActivity {
    TextView back;
    EditText nickname, id, pw, pw2, email;
    Button pwCheck, submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        back = findViewById(R.id.back);
        back.setOnClickListener(v -> onBackPressed());

        nickname = findViewById(R.id.signNickname);
        id = findViewById(R.id.signID);
        pw = findViewById(R.id.signPW);
        pw2 = findViewById(R.id.signPW2);
        email = findViewById(R.id.signmail);
        pwCheck = findViewById(R.id.pwcheckbutton);
        pwCheck.setOnClickListener(v -> {
            if (pw.getText().toString().equals(pw2.getText().toString())) {
                pwCheck.setText("일치");
            } else {
                Toast.makeText(Signup.this, "비밀번호가 다릅니다.", Toast.LENGTH_LONG).show();
            }
        });

        submit = findViewById(R.id.signupbutton);
        submit.setOnClickListener(v -> {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            Toast.makeText(Signup.this, "회원가입 성공", Toast.LENGTH_LONG).show();
        });

    }
}
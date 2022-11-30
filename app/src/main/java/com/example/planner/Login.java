package com.example.planner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Login extends AppCompatActivity implements View.OnClickListener {

    Button backBtn;
    EditText loginIdView;
    EditText loginPasswordView;
    Button loginBtn;
    Button signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        backBtn = findViewById(R.id.back);
        loginIdView = findViewById(R.id.login_id);
        loginPasswordView = findViewById(R.id.login_password);
        loginBtn = findViewById(R.id.login);
        signupBtn = findViewById(R.id.signup);

        backBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        signupBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == loginBtn) {
            if (loginIdView.getText().toString().equals("")) {
                Toast.makeText(this, "아이디를 입력하세요.", Toast.LENGTH_LONG).show();
            } else if (loginPasswordView.getText().toString().equals("")){
                Toast.makeText(this, "비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
            } else {
                // 다음으로 가기 구현
            }
        }

        if (view == signupBtn) {
            // 회원가입 뷰로 이동
        }
    }
}
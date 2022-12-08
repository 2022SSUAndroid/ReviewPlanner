package com.example.planner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity implements View.OnClickListener {

    EditText loginIdView;
    EditText loginPasswordView;
    Button loginBtn;
    Button signupBtn;

    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginIdView = findViewById(R.id.login_id);
        loginPasswordView = findViewById(R.id.login_password);
        loginBtn = findViewById(R.id.login);
        signupBtn = findViewById(R.id.signup);

        loginBtn.setOnClickListener(this);
        signupBtn.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            updateUI(currentUser);
        }
    }

    @Override
    public void onClick(View view) {
        if (view == loginBtn) {
            if (loginIdView.getText().toString().equals("")) {
                Toast.makeText(this, "아이디를 입력하세요.", Toast.LENGTH_LONG).show();
            } else if (loginPasswordView.getText().toString().equals("")){
                Toast.makeText(this, "비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
            } else {
                String id = loginIdView.getText().toString().trim();
                String pwd = loginPasswordView.getText().toString().trim();
                loginIdView.setText("");
                loginPasswordView.setText("");
                signIn(id, pwd);
            }
        }

        if (view == signupBtn) {
            // 회원가입 뷰로 이동
        }
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Log.d(TAG, "signInWithEmail:failure");
                            Toast.makeText(getApplicationContext(), "다시 입력해보세요",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if (user == null) return;

        Log.d(TAG, "email: " + user.getEmail());
        Log.d(TAG, "uid: " + user.getUid());

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
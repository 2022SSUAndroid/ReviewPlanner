package com.example.planner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;


public class Signup extends AppCompatActivity {
    private static final String TAG = "EmailPassword";

    TextView back;
    EditText nickname, pw, pw2, emailEdit;
    Button pwCheck, submit;
    Boolean emailOK = false;
    Boolean pwdOK = false;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        back = findViewById(R.id.back);
        back.setOnClickListener(v -> onBackPressed());

        nickname = findViewById(R.id.signNickname);
        pw = findViewById(R.id.signPW);
        pw2 = findViewById(R.id.signPW2);
        emailEdit = findViewById(R.id.signmail);
        pwCheck = findViewById(R.id.pwcheckbutton);
        pwCheck.setOnClickListener(v -> {
            if (pw.getText().toString().length() < 6) {
                Toast.makeText(this, "비밀번호는 6자 이상이어야합니다.", Toast.LENGTH_SHORT).show();
            } else if (pw.getText().toString().equals(pw2.getText().toString())) {
                pwCheck.setText("일치");
                pwdOK = true;
            } else {
                Toast.makeText(Signup.this, "비밀번호가 다릅니다.", Toast.LENGTH_LONG).show();
            }
        });

        submit = findViewById(R.id.signupbutton);
        submit.setOnClickListener(v -> {
            if (!pw.getText().toString().equals(pw2.getText().toString())) {
                pwCheck.setText("확인");
                pwdOK = false;
            }
            if (emailEdit.getText().toString() == "") {
                Toast.makeText(this, "이메일을 입력하세요", Toast.LENGTH_SHORT).show();
                return;
            } else if (!pwdOK) {
                Toast.makeText(this, "비밀번호를 확인하세요", Toast.LENGTH_SHORT).show();
                return;
            }
            String email = emailEdit.getText().toString();
            String password = pw2.getText().toString();

            signUp(email, password);
        });

    }
    private void signUp(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            String email = user.getEmail();
                            String uid = user.getUid();
                            String name = nickname.getText().toString().trim();

                            //해쉬맵 테이블을 파이어베이스 데이터베이스에 저장
                            HashMap<Object,String> hashMap = new HashMap<>();

                            hashMap.put("id",email);
                            hashMap.put("name",name);

                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            db.collection("user").document(uid).set(hashMap);

                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Log.d(TAG, "createUserWithEmail:failure");
                            Toast.makeText(getApplicationContext(), "이미 존재하는 이메일입니다.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });

    }
    private void updateUI(FirebaseUser user) {
        if (user == null) return;

        Intent intent = new Intent(getApplicationContext(), Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        Toast.makeText(Signup.this, "회원가입 성공", Toast.LENGTH_SHORT).show();

        Log.d(TAG, "email : " + user.getEmail());
        Log.d(TAG, "uid : " + user.getUid());
    }
}
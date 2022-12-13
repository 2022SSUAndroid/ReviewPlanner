package com.example.planner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class Withdrawal extends AppCompatActivity {
    TextView back;
    Button withdrawal;
    private FirebaseAuth mAuth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal);

        mAuth = FirebaseAuth.getInstance();

        back = findViewById(R.id.back);
        back.setOnClickListener(v -> onBackPressed());

        withdrawal = findViewById(R.id.withdrawalbutton);
        withdrawal.setOnClickListener(v -> {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.document("user/" + mAuth.getCurrentUser().getUid()).delete();
            mAuth.getCurrentUser().delete();
            Toast.makeText(Withdrawal.this, "탈퇴 완료", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, Signup.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
}
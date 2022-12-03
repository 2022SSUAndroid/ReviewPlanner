package com.example.planner;

//import androidx.annotation.NonNull;
//import androidx.appcompat.app.ActionBar;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//
//import android.annotation.SuppressLint;
//import android.os.Bundle;
//import android.view.MenuItem;
//
//import com.google.android.material.navigation.NavigationBarView;
//
//
//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);  //액티비티의 앱바 지정
//
//
//        ActionBar actionBar = getSupportActionBar();
//        //Title text 작성
//        actionBar.setTitle("문제 풀기");
//        //뒤로가기 버튼 추가
//        actionBar.setDisplayHomeAsUpEnabled(true);
//
//        NavigationBarView navigationBarView = findViewById(R.id.bottom_navigation);
//
//        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.page_1:// Respond to navigation item 1 click
//                        return true;
//                    case R.id.page_2:// Respond to navigation item 2 click
//                        return true;
//                    case R.id.page_3:// Respond to navigation item 3 click
//                        return true;
//                    case R.id.page_4:// Respond to navigation item 4 click
//                        return true;
//                    default:
//                        return false;
//                }
//            }
//        });
//
//        navigationBarView.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
//            @Override
//            public void onNavigationItemReselected(@NonNull MenuItem item) {
//
//            }
//        });
//    }
//
//    @SuppressLint("NonConstantResourceId")
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId ()) {
//            case android.R.id.home: //툴바 뒤로가기버튼 눌렸을 때 동작
//                finish ();
//                return true;
//            default:
//                return super.onOptionsItemSelected (item);
//        }
//    }
//
//
//
//
//}


import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    Button btn1,btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_first_tap_bar);

        btn1 = (Button)findViewById(R.id.btn_1);
        btn2 = (Button)findViewById(R.id.btn_2);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Fragment1 fragment1 = new Fragment1();
                transaction.replace(R.id.frame, fragment1);
                transaction.commit();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Fragment2 fragment2 = new Fragment2();
                transaction.replace(R.id.frame, fragment2);
                transaction.commit();
            }
        });
    }
}


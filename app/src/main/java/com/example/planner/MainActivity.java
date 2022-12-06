package com.example.planner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);  //액티비티의 앱바 지정


        ActionBar actionBar = getSupportActionBar();
        //Title text 작성
        actionBar.setTitle("text 입력");
        //뒤로가기 버튼 추가
        actionBar.setDisplayHomeAsUpEnabled(true);

        NavigationBarView navigationBarView = findViewById(R.id.bottom_navigation);

        transferTo(TodayFragment.newInstance("param1", "param2"));


        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.page_1:// Respond to navigation item 1 click
                        transferTo(TodayFragment.newInstance("param1", "param2"));
                        return true;
                    case R.id.page_2:// Respond to navigation item 2 click
                        transferTo(CalenderFragment.newInstance("param1", "param2"));
                        return true;
                    case R.id.page_3:// Respond to navigation item 3 click
                        transferTo(HistoryFragment.newInstance("param1", "param2"));
                        return true;
                    case R.id.page_4:// Respond to navigation item 4 click
                        transferTo(MypageFragment.newInstance("param1", "param2"));
                        return true;
                    default:
                        return false;
                }
            }
        });

        navigationBarView.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {

            }
        });
    }

    private void transferTo(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }




}
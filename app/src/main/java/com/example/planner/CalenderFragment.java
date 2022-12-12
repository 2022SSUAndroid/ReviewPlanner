package com.example.planner;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import com.google.android.gms.gcm.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class CalenderFragment extends Fragment {

    public CalendarView calendarView;
    public TextView diaryTextView;

    ArrayList<Category> categories;
    ListView customListView;
    private static CustomAdapter customAdapter;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public CalenderFragment() {

    }

    public static CalenderFragment newInstance() {
        CalenderFragment fragment = new CalenderFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_calender, container, false);
        customListView = (ListView) rootView.findViewById(R.id.calendar_list);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        calendarView = view.findViewById(R.id.calendarView);
        diaryTextView = view.findViewById(R.id.diaryTextView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
        {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth)
            {
                diaryTextView.setVisibility(View.VISIBLE);
                diaryTextView.setText(String.format("%d / %d / %d", year, month + 1, dayOfMonth));
                LoadProblems(year, month, dayOfMonth);
            }
        });
    }

    public void LoadProblems(int year, int month, int dayOfMonth)
    {
        String day = "" + year + (month + 1) + "" + dayOfMonth;

//        categories = new ArrayList<>();
//
//
//        categories.add(new Category("영어", "2문제/4문제"));
//        categories.add(new Category("수학", "1문제/3문제"));
//        categories.add(new Category("정보", "1문제/5문제"));
//
//
//        customAdapter = new CustomAdapter(getContext(), categories);
//        customListView.setAdapter(customAdapter);

    }


}



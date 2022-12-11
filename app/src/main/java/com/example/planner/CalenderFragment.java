package com.example.planner;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

public class CalenderFragment extends Fragment {

    public String readDay = null;
    public String str = null;
    public CalendarView calendarView;
    public TextView diaryTextView;

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
        return inflater.inflate(R.layout.fragment_calender, container, false);
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
//                LoadProblems(year, month, dayOfMonth);
            }
        });
    }

//    public void LoadProblems(int cYear, int cMonth, int cDay)
//    {
//        readDay = "" + cYear + "-" + (cMonth + 1) + "" + "-" + cDay + ".txt";
//        FileInputStream fis;
//
//        try
//        {
//            fis = openFileInput(readDay);
//
//            byte[] fileData = new byte[fis.available()];
//            fis.read(fileData);
//            fis.close();
//
//            str = new String(fileData);
//
//            textView2.setVisibility(View.VISIBLE);
//            textView2.setText(str);
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//    }
}



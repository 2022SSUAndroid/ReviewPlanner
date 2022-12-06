package com.example.planner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import java.util.ArrayList;

public class Fragment1 extends Fragment {

    ArrayList<Category> categories;
    ListView customListView;
    private static CustomAdapter customAdapter;

    public Fragment1() {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment1, container, false);

        categories = new ArrayList<>();
        categories.add(new Category("영어", "2문제/4문제"));
        categories.add(new Category("수학", "1문제/3문제"));
        categories.add(new Category("정보", "1문제/5문제"));


        customListView = (ListView) rootView.findViewById(R.id.listView_custom);
        customAdapter = new CustomAdapter(getContext(), categories);
        customListView.setAdapter(customAdapter);

        return rootView;
    }
}

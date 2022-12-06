package com.example.planner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class Fragment2 extends Fragment {

    ArrayList<Category> categories;
    ListView customListView;
    private static CustomAdapter customAdapter;

    public Fragment2() {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment2, container, false);

        categories = new ArrayList<>();
        categories.add(new Category("영어", "3문제"));
        categories.add(new Category("수학", "1문제"));

        customListView = (ListView) rootView.findViewById(R.id.listView_custom);
        customAdapter = new CustomAdapter(getContext(), categories);
        customListView.setAdapter(customAdapter);

        return rootView;
    }
}

package com.example.planner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

public class ProblemListFragment extends Fragment {

    ArrayList<Category> categories = new ArrayList<>();
    ListView customListView;
    private static CustomAdapter customAdapter;
    ArrayList<String> problemNames;
    String categoryName;


    public ProblemListFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_problemlistfragment, container, false);
        customListView = (ListView) rootView.findViewById(R.id.listView_custom);

        if (getArguments() != null) {
            problemNames = getArguments().getStringArrayList("category");
            categoryName = getArguments().getString("categoryName");
        }

        for (String name : problemNames) {
            categories.add(new Category(name, "풀기 >"));
        }

        customAdapter = new CustomAdapter(getContext(), categories);
        customListView.setAdapter(customAdapter);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        customListView = (ListView) view.findViewById(R.id.listView_custom);

        customListView.setOnItemClickListener(listener);

    }

    AdapterView.OnItemClickListener listener= new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            String selectProblem = categories.get(position).getName();

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            db.document("user/" + uid + "/" + categoryName + "/" + selectProblem).get().addOnCompleteListener(task -> {
                if(task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    Map<String, Object> map = document.getData();

                    ProblemDto problemDto = new ProblemDto(map);

                    Intent intent = new Intent(getContext(), SolvingProblem.class);

                    intent.putExtra("problemDto", problemDto);
                    startActivity(intent);

                }
            });
        }

    };
}

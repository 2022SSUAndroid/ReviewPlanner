package com.example.planner;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Fragment2 extends Fragment {

    ArrayList<String> categoryNames;

    ArrayList<Category> categories;
    ListView customListView;
    private static CustomAdapter customAdapter;
    String currentDate = LocalDate.now().toString();

    public Fragment2() {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment2, container, false);
        customListView = (ListView) rootView.findViewById(R.id.listView_custom);

        if (getArguments() != null) {
            categoryNames = getArguments().getStringArrayList("categories");
        }


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String today = currentDate.substring(0, 4) + currentDate.substring(5, 7) + currentDate.substring(8, 10);

        for (String name : categoryNames) {
            AtomicInteger takeOffCount = new AtomicInteger();
            db.collection("user/" + "3rKDL4lMxSR7UnWB35GNoyEeI9s2" + "/" + name).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        ArrayList reviewDay = (ArrayList) document.getData().get("reviewDay");
                        ArrayList ox = (ArrayList) document.getData().get("ox");

                        if (reviewDay.get(ox.size()).toString().compareTo(today) > 0) {
                            takeOffCount.getAndIncrement();
                        }
                    }
                    categories.add(new Category(name, takeOffCount.toString()+"문제"));
                    customAdapter = new CustomAdapter(getContext(), categories);
                    customListView.setAdapter(customAdapter);
                }
            });
        }
        return rootView;
    }

}

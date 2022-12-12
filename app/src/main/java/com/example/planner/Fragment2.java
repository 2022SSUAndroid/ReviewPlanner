package com.example.planner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Fragment2 extends Fragment {

    ArrayList<Category> categories;
    ListView customListView;
    private static CustomAdapter customAdapter;
    DocumentReference docRef;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public Fragment2() {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment2, container, false);

//        DocumentReference docRef = db.collection("user").document(user.getUid());
        this.docRef = db.collection("user").document("3rKDL4lMxSR7UnWB35GNoyEeI9s2");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    ArrayList categoryReturnNames = (ArrayList) document.get("categories");
                    ArrayList<String> categoryNames = new ArrayList<>();

                    categories = new ArrayList<>();
                    for (Object category : categoryReturnNames) {
                        String categoryName = category.toString();
                        categories.add(new Category(categoryName, "1문제/1문제"));
                    }

                    customListView = (ListView) rootView.findViewById(R.id.listView_custom);
                    customAdapter = new CustomAdapter(getContext(), categories);
                    customListView.setAdapter(customAdapter);

                } else {
                    Toast.makeText(getActivity(), "해당하는 문제가 없습니다", Toast.LENGTH_SHORT).show();
                }
            }

        });

        return rootView;
    }
}

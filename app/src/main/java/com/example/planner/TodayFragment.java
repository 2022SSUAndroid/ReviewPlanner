package com.example.planner;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class TodayFragment extends Fragment implements View.OnClickListener {

    Button btn;
    Button btn1, btn2;
    DocumentReference docRef;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<String> categoryNames = new ArrayList<>();


    public TodayFragment() {

    }

    public static TodayFragment newInstance() {
        TodayFragment fragment = new TodayFragment();
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
        return inflater.inflate(R.layout.fragment_first_tap_bar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn = view.findViewById(R.id.add_problem);
        btn.setOnClickListener(this);

        btn1 = view.findViewById(R.id.btn_1);
        btn1.setOnClickListener(this);
        btn2 = view.findViewById(R.id.btn_2);
        btn2.setOnClickListener(this);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        this.docRef = db.collection("user").document(uid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    ArrayList categoryReturnNames = (ArrayList) document.get("categories");

                    try {
                        for (Object category : categoryReturnNames) {
                            String categoryName = category.toString();
                            categoryNames.add(categoryName);
                        }
                    } catch (NullPointerException e) {

                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {

        Bundle bundle = new Bundle();
        bundle.putStringArrayList("categories", categoryNames);

        if (view == btn) {
            getActivity().startActivity(new Intent(getActivity(), AddProblemActivity.class));
        }
        else if (view == btn1) {
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            Fragment1 fragment1 = new Fragment1();
            fragment1.setArguments(bundle);
            transaction.addToBackStack(null);
            transaction.replace(R.id.frame, fragment1);
            transaction.commit();
        }
        else if (view == btn2) {
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            Fragment2 fragment2 = new Fragment2();
            fragment2.setArguments(bundle);
            transaction.addToBackStack(null);
            transaction.replace(R.id.frame, fragment2);
            transaction.commit();
        }
    }
}
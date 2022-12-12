package com.example.planner;

import static android.content.ContentValues.TAG;

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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.core.FirestoreClient;

public class CalenderFragment extends Fragment {

    public CalendarView calendarView;
    public TextView diaryTextView;

    ListView customListView;
    private static CustomAdapter customAdapter;

//    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
//    DocumentReference docRef;

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

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

//        Firestore firestore = FirestoreClient.getFirestore();
//        Iterable<CollectionReference> collections =
//                firestore.collection("user").document("3rKDL4lMxSR7UnWB35GNoyEeI9s2").
//
//        for (CollectionReference collRef : collections) {
//            System.out.println("Found subcollection with id: " + collRef.getId());
//        }

        //DocumentReference docRef = db.collection("user").document(user.getUid());
//        this.docRef = db.collection("user").document("3rKDL4lMxSR7UnWB35GNoyEeI9s2");
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    Map data = document.getData();
//                    ArrayList categoryReturnNames = (ArrayList) document.get("categories");
//                    ArrayList<String> categoryNames = new ArrayList<>();
//
//                    for (Object category : categoryReturnNames) {
//                        String categoryName = category.toString();
//                        categoryNames.add(categoryName);
//                    }
//
//                    Log.i("CalenderFragment", document.toString());
//
//                } else {
//                    Toast.makeText(getActivity(), "해당하는 문제가 없습니다", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//        });
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



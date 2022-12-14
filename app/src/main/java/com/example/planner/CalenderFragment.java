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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.util.BackgroundQueue;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class CalenderFragment extends Fragment {

    public CalendarView calendarView;
    public TextView diaryTextView;

    ListView customListView;
    private static CustomAdapter customAdapter;
    ArrayList<String> categoryNames = new ArrayList<>();
    ArrayList<Category> categories;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    DocumentReference docRef;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();

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

        this.docRef = db.collection("user").document(user.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    ArrayList categoryReturnNames = (ArrayList) document.get("categories");

                    for (Object category : categoryReturnNames) {
                        String categoryName = category.toString();
                        categoryNames.add(categoryName);
                    }

                } else {
                    Toast.makeText(getActivity(), "등록 된 문제가 없습니다", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void LoadProblems(int year, int month, int dayOfMonth)
    {
        String selectDay = "" + year + (month + 1) + "" + dayOfMonth;
        categories = new ArrayList<>();

        for (String name : categoryNames) {
            db.collection("user/" + uid + "/" + name).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        ArrayList<String> reviewDay = (ArrayList<String>) document.getData().get("reviewDay");
                        ArrayList<Boolean> ox = (ArrayList<Boolean>) document.getData().get("ox");

                        Log.i("Calender", reviewDay.toString());
                        Log.i("Calender", ox.toString());

                        for (int i = 0; i < reviewDay.size(); i++) {
                            if (reviewDay.get(i).equals(selectDay)) {
                                try {
                                    if (ox.get(i+1)) categories.add(new Category(document.getId(), "O"));
                                    else categories.add(new Category(document.getId(), "X"));
                                } catch (IndexOutOfBoundsException e) {
                                    categories.add(new Category(document.getId(), "X"));
                                }

                            }
                        }
                        customAdapter = new CustomAdapter(getContext(), categories);
                        customListView.setAdapter(customAdapter);

                    }
                }
            });
        }


    }


}



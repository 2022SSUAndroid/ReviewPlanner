package com.example.planner;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Fragment1 extends Fragment {

    ArrayList<String> categoryNames;

    ArrayList<Category> categories = new ArrayList<>();
    ListView customListView;
    private static CustomAdapter customAdapter;
    private HashMap<String, ArrayList<String>> todayProblemsList;

    String currentDate = LocalDate.now().toString();

    public Fragment1() {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment1, container, false);
        customListView = (ListView) rootView.findViewById(R.id.listView_custom);
        todayProblemsList = new HashMap<>();

        if (getArguments() != null) {
            categoryNames = getArguments().getStringArrayList("categories");
        }


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String today = currentDate.substring(0, 4) + currentDate.substring(5, 7) + currentDate.substring(8, 10);

        for (String name : categoryNames) {
            ArrayList<String> names = new ArrayList<>();
            AtomicInteger todayCount = new AtomicInteger();
            db.collection("user/" + uid + "/" + name).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        ArrayList reviewDay = (ArrayList) document.getData().get("reviewDay");
                        ArrayList ox = (ArrayList) document.getData().get("ox");

                        if (reviewDay.get(ox.size() - 1).equals(today)) {
                            todayCount.getAndIncrement();
                            names.add(document.getId());
                        }
                    }
                    categories.add(new Category(name, todayCount.toString()+"??????"));
                    customAdapter = new CustomAdapter(getContext(), categories);
                    customListView.setAdapter(customAdapter);
                }
            });
            todayProblemsList.put(name, names);
        }
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

            ArrayList<String> selectCategory = todayProblemsList.get(categories.get(position).getName());

            Bundle bundle = new Bundle();
            bundle.putStringArrayList("category", selectCategory);
            bundle.putString("categoryName", categories.get(position).getName());
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            ProblemListFragment problemListFragment = new ProblemListFragment();
            problemListFragment.setArguments(bundle);
            transaction.addToBackStack(null);
            transaction.replace(R.id.frame, problemListFragment);
            transaction.commit();
        }

    };

}


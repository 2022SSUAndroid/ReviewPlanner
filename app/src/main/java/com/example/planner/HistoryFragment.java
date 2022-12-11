package com.example.planner;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    LinearLayout historyCategoryLinear;
    ArrayList<String> categories = new ArrayList<>();
    List<Button> buttons = new ArrayList<>();
    String selectedCategory;

    public HistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        historyCategoryLinear = view.findViewById(R.id.history_category_linear);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.document("user/" + uid).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                ArrayList arr = (ArrayList) document.get("categories");
                for (int i = 0; i < arr.size(); i++) {
                    Log.i("rerere", arr.get(i).toString());
                    categories.add(arr.get(i).toString());
                }
                for (int i = 0; i < categories.size(); i++) {
                    Button btn = new Button(getActivity());
                    btn.setText(categories.get(i));
                    btn.setOnClickListener(this);
                    buttons.add(btn);
                }

                for (int i = 0; i < buttons.size(); i++) {
                    historyCategoryLinear.addView(buttons.get(i));
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        for (int i = 0; i < buttons.size(); i++) {
            if (view == buttons.get(i)) {
                selectedCategory = buttons.get(i).getText().toString();
                break;
            }
        }
        Toast.makeText(getActivity(), selectedCategory, Toast.LENGTH_SHORT).show();

        Bundle bundle = new Bundle();
        bundle.putString("category", selectedCategory);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        HistoryProblemsFragment historyProblemsFragment = new HistoryProblemsFragment();
        historyProblemsFragment.setArguments(bundle);
        transaction.addToBackStack(null);
        transaction.replace(R.id.fragment_container, historyProblemsFragment);
        transaction.commit();
    }
}
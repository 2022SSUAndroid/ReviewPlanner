package com.example.planner;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryProblemsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryProblemsFragment extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    String selectedCategory = "";
    List<String> problemNameList;
    List<Button> buttons;
    HashMap<String, Map> problems;
    LinearLayout historyProblemsLinear;
    Map selectedProblem = new HashMap<>();

    public HistoryProblemsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryProblemsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryProblemsFragment newInstance(String param1, String param2) {
        HistoryProblemsFragment fragment = new HistoryProblemsFragment();
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
        return inflater.inflate(R.layout.fragment_history_problems, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        problemNameList = new ArrayList<>();
        buttons = new ArrayList<>();
        problems = new HashMap<>();

        if (getArguments() != null) {
            selectedCategory = getArguments().getString("category");
        }

        // 전달받은 카테고리 확인용 토스트
        Toast.makeText(getActivity(), "선택된 카테고리는" + selectedCategory, Toast.LENGTH_SHORT).show();

        historyProblemsLinear = view.findViewById(R.id.history_problems_linear);

        // 파이어베이스에서 카테고리가 selectedCategory인 컬렉션에 들어가서 문제 해시맵<이름, 객체>로 가져오기
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("user/" + uid + "/" + selectedCategory).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    problemNameList.add(document.getId());
                    problems.put(document.getId(), document.getData());
                }
                for (int i = 0; i < problemNameList.size(); i++) {
                    Button btn = new Button(getActivity());
                    btn.setText(problemNameList.get(i));
                    btn.setOnClickListener(this);
                    historyProblemsLinear.addView(btn);
                    buttons.add(btn);
                }
            }
        });


        // 문제 리스트에서 문제 이름으로 버튼 뷰 주르륵 만들기

        // 각 버튼별로 클릭리스너 달고 클릭되면 클릭된 문제 객체를 다음 페이지로 넘김

    }

    @Override
    public void onClick(View view) {
        for (int i = 0; i < buttons.size(); i++) {
            if (view == buttons.get(i)) {
                String selectedProblemName = buttons.get(i).getText().toString();
                selectedProblem = problems.get(selectedProblemName);
                break;
            }
        }


        Bundle bundle = new Bundle();
        bundle.putSerializable("problem", (Serializable) selectedProblem);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        HistoryRecordFragment historyOXFragment = new HistoryRecordFragment();
        historyOXFragment.setArguments(bundle);
        transaction.addToBackStack(null);
        transaction.replace(R.id.fragment_container, historyOXFragment);
        transaction.commit();
    }
}
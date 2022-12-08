package com.example.planner;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryRecordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryRecordFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    HistoryRecyclerViewAdapter adapter;

    ProblemObj selectedProblem = new ProblemObj();

    public HistoryRecordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryRecordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryRecordFragment newInstance(String param1, String param2) {
        HistoryRecordFragment fragment = new HistoryRecordFragment();
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
        return inflater.inflate(R.layout.fragment_history_record, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            selectedProblem = (ProblemObj) getArguments().getSerializable("problem");

            recyclerView = view.findViewById(R.id.recycler);
            adapter = new HistoryRecyclerViewAdapter(getActivity(), selectedProblem.getOX(), selectedProblem.getReviewTag());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false));

        }



        Toast.makeText(getActivity(), selectedProblem.getProblemName() + selectedProblem.getReviewCnt(), Toast.LENGTH_SHORT).show();
        Log.d("선택된 문제 정보 :", "이름" + selectedProblem.getProblemName());
        Log.d("선택된 문제 정보 :", "복습횟수" + selectedProblem.getReviewCnt());
        Log.d("선택된 문제 정보 :", "오답여부" + selectedProblem.getOX());
    }
}
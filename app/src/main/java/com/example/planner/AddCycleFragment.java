package com.example.planner;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddCycleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddCycleFragment extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button selectedView;
    Button cycle1View;
    Button cycle2View;
    Button cycle3View;
    Button selectMyselfView;
    Button nextView;
    ProblemObj problemObj = new ProblemObj();

    List<Integer> myCycle = new ArrayList<>();

    public AddCycleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddCycleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddCycleFragment newInstance(String param1, String param2) {
        AddCycleFragment fragment = new AddCycleFragment();
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
        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                problemObj = (ProblemObj) result.getSerializable("bundleKey");
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_cycle, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        selectedView = view.findViewById(R.id.selected);
        cycle1View = view.findViewById(R.id.cycle1);
        cycle2View = view.findViewById(R.id.cycle2);
        cycle3View = view.findViewById(R.id.cycle3);
        selectMyselfView = view.findViewById(R.id.select_myself);
        nextView = view.findViewById(R.id.btn_next);

        selectedView.setText("복습주기를 선택하세요");

        cycle1View.setOnClickListener(this);
        cycle2View.setOnClickListener(this);
        cycle3View.setOnClickListener(this);
        selectMyselfView.setOnClickListener(this);
        nextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == nextView) {
            if (selectedView.getText().equals("복습주기를 선택하세요")) {
                Toast.makeText(getActivity(), "복습주기를 선택하세요", Toast.LENGTH_SHORT).show();
                return;
            }
            List<String> reviewDay = problemObj.calculateReviewDay();
            problemObj.setReviewDay(reviewDay);

            Bundle result = new Bundle();
            result.putSerializable("bundleKey2", problemObj);

            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            ReviewFragment reviewFragment = new ReviewFragment();
            reviewFragment.setArguments(result);
            transaction.replace(R.id.add_problem_fragment_container, reviewFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (view == selectMyselfView) {
            DialogFragmentMyCycle dialog = new DialogFragmentMyCycle();
            dialog.setFragmentInterface(new DialogFragmentMyCycle.MyFragmentInterface() {
                @Override
                public void onButtonClick(List<Integer> input) {
                    if (input.size() != 0) {
                        myCycle = input;
                        StringBuilder cycleStr = new StringBuilder();
                        for (int i = 0; i < myCycle.size(); i++) {
                            if (i != myCycle.size() - 1)
                                cycleStr.append(myCycle.get(i) + "일뒤, ");
                            else
                                cycleStr.append(myCycle.get(i) + "일뒤");
                        }
                        problemObj.setCycle(myCycle);
                        selectedView.setText(cycleStr);
                    }
                }
            });
            dialog.show(getParentFragmentManager(), null);

        } else if (view == cycle1View) {
            selectedView.setText(cycle1View.getText());
            List<Integer> cycle1 = Arrays.asList(0, 1, 3, 7);
            problemObj.setCycle(cycle1);
        } else if (view == cycle2View) {
            selectedView.setText(cycle2View.getText());
            List<Integer> cycle1 = Arrays.asList(0, 1, 3, 7, 14);
            problemObj.setCycle(cycle1);
        } else if (view == cycle3View) {
            selectedView.setText(cycle3View.getText());
            List<Integer> cycle1 = Arrays.asList(0, 1, 3, 7, 14, 30);
            problemObj.setCycle(cycle1);
        }
    }
}
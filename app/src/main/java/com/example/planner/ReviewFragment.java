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
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReviewFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ProblemObj problemObj = new ProblemObj();
    TextView btn_next;
    EditText reviewTag;

    public ReviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReviewFragment newInstance(String param1, String param2) {
        ReviewFragment fragment = new ReviewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("dfdfdf", "onCreate");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            problemObj = (ProblemObj) getArguments().getSerializable("bundleKey2");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_review, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d("problemObj", "category : " + problemObj.getCategory());
        Log.d("problemObj", "name : " + problemObj.getProblemName());
        Log.d("problemObj", "cycle : " + problemObj.getCycle().toString());
        Log.d("problemObj", "tag : " + problemObj.getReviewTag().toString());
        Log.d("problemObj", "problemImg : " + problemObj.getProblemImg());


        btn_next = (TextView) view.findViewById(R.id.btn_next);
        reviewTag = (EditText) view.findViewById(R.id.reviewTag);

        btn_next.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        if (view == btn_next) {
            List<String> reviewTagList = Arrays.asList(reviewTag.getText().toString());
            problemObj.setReviewTag(reviewTagList);

            //Log 확인
            Log.d("problemObj", "reviewTag : " + problemObj.getReviewTag());

            Bundle result = new Bundle();
            result.putSerializable("bundleKey3", problemObj);
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            ProblemRegisterFragment problemRegisterFragment = new ProblemRegisterFragment();//프래그먼트2 선언
            problemRegisterFragment.setArguments(result);//번들을 프래그먼트2로 보낼 준비
            transaction.replace(R.id.add_problem_fragment_container, problemRegisterFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
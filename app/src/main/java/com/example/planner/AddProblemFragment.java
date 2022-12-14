package com.example.planner;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddProblemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddProblemFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    LinearLayout category_list;
    EditText problem;
    TextView categoryView;
    Button nextView;
    Button addCategoryBtn;
    ProblemObj problemObj = new ProblemObj();

    List<String> category;
    List<Button> categoryBtn;

    String newCategory = "";

    public AddProblemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddProblemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddProblemFragment newInstance(String param1, String param2) {
        AddProblemFragment fragment = new AddProblemFragment();
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
        getChildFragmentManager().setFragmentResultListener("requestKeyForPlaces", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                // We use a String here, but any type that can be put in a Bundle is supported
                String newCategory = result.getString("bundleKey");
                // Do something with the result...
                Toast.makeText(getActivity(), "newCategory", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_problem, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        category = new ArrayList<>();      // ???????????????????????? ???????????? ???
        categoryBtn = new ArrayList<>();       // ???????????? ???????????? ?????????

        category_list = view.findViewById(R.id.category_list);
        problem = view.findViewById(R.id.problem);
        categoryView = view.findViewById(R.id.category);
        nextView = view.findViewById(R.id.next);
        addCategoryBtn = view.findViewById(R.id.add_category_btn);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.document("user/" + uid).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                category = (List) document.get("categories");
                if (category != null) {
                    for (int i = 0; i < category.size(); i++) {
                        Button btn = new Button(getActivity());
                        btn.setText(category.get(i));
                        btn.setOnClickListener(this);
                        categoryBtn.add(btn);
                        category_list.addView(btn);
                    }
                }
            }
        });

        addCategoryBtn.setOnClickListener(this);
        nextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == addCategoryBtn) {
            DialogFragmentNewCategory dialog = new DialogFragmentNewCategory();
            dialog.setFragmentInterfacer(new DialogFragmentNewCategory.MyFragmentInterfacer() {
                @Override
                public void onButtonClick(String input) {
                    if (!input.equals("")) {
                        if (category.contains(input)) {
                            Toast.makeText(getActivity(), "?????? ???????????? ?????????????????????.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        newCategory = input;
                        problemObj.setCategory(newCategory);
                        categoryView.setText("???????????? ?????? : " + problemObj.getCategory());
                        Button btn = new Button(getActivity());
                        btn.setText(newCategory);
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                problemObj.setCategory(newCategory);
                            }
                        });
                        categoryBtn.add(btn);
                        category_list.addView(btn);
                    }
                }
            });
            dialog.show(getParentFragmentManager(), null);

        } else if (categoryBtn.contains(view)) {  // ???????????? ?????? ??? ?????? ?????? ?????? ??????
            int idx = categoryBtn.indexOf(view);
            problemObj.setCategory(String.valueOf(categoryBtn.get(idx).getText()));
            categoryView.setText("???????????? ?????? : " + problemObj.getCategory());
        } else if (view == nextView) {
            if (problem.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "?????? ????????? ???????????????.", Toast.LENGTH_SHORT).show();
            } else if (problemObj.getCategory().equals("")) {
                Toast.makeText(getActivity(), "??????????????? ???????????????.", Toast.LENGTH_SHORT).show();
            }
            else{
                problemObj.setProblemName(problem.getText().toString());
                Bundle result = new Bundle();
                result.putSerializable("bundleKey", problemObj);
                getParentFragmentManager().setFragmentResult("requestKey", result);

                getParentFragmentManager().beginTransaction().replace(R.id.add_problem_fragment_container, AddCycleFragment.newInstance("param1", "param2")).addToBackStack(null).commit();
            }
        }
    }
}
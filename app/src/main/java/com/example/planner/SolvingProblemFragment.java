package com.example.planner;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SolvingProblemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SolvingProblemFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ImageView correctBtn;
    ImageView incorrectBtn;
    Button convertBtn;
    ImageView problemImg;
    ImageView solutionImg;

    ProblemObj problemObj;
    String pi = "";
    String si = "";

    FirebaseAuth mAuth;

    public SolvingProblemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SolvingProblemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SolvingProblemFragment newInstance(String param1, String param2) {
        SolvingProblemFragment fragment = new SolvingProblemFragment();
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
            problemObj = (ProblemObj) getArguments().getSerializable("bundlepro");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_solving_problem, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        correctBtn = view.findViewById(R.id.correct_button);
        incorrectBtn = view.findViewById(R.id.incorrect_button);
        convertBtn = view.findViewById(R.id.convert_btn);
        problemImg = view.findViewById(R.id.problem_image);
        solutionImg = view.findViewById(R.id.solution_image);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.document("user/" + uid + "/" + problemObj.getCategory() + "/" + problemObj.getProblemName()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                pi = (String) document.get("problemImg");
                si = (String) document.get("solutionImg");
            }
            if (pi.equals("")) {
                pi = "NoImg.png";
            }
            if (si.equals("")) {
                si = "NoImg.png";
            }
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReference();

            StorageReference pathReference = storageReference.child(pi);
            pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(getActivity()).load(uri).into(problemImg);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

            StorageReference pathReference2 = storageReference.child(si);
            pathReference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(getActivity()).load(uri).into(solutionImg);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        });



        convertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button btn = (Button) view;
                if (btn.getText().equals("정답")) {
                    problemImg.setVisibility(View.GONE);
                    solutionImg.setVisibility(View.VISIBLE);
                    btn.setText("풀이");
                } else if (btn.getText().equals("풀이")) {
                    problemImg.setVisibility(View.VISIBLE);
                    solutionImg.setVisibility(View.GONE);
                    btn.setText("정답");
                }
            }
        });


        correctBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });

        incorrectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                problemObj.addOX(false);

                Bundle result = new Bundle();
                result.putSerializable("bundlepro2", problemObj);

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                // 프래그먼트매니저를 통해 사용
                ReviewAgainFragment reviewAgainFragment= new ReviewAgainFragment(); // 객체 생성
                reviewAgainFragment.setArguments(result);
                transaction.replace(R.id.new_fragment, reviewAgainFragment); //layout, 교체될 layout
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


    }

    void showAlertDialog()
    {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("남은 복습 일정을 계속 진행하시겠습니까?");
        builder.setPositiveButton("O", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 그대로 저장
                problemObj.addOX(true);
                problemObj.getMySolving().add("");
                HashMap<Object,Object> hashMap = new HashMap<>();

                hashMap.put("category", problemObj.getCategory());
                hashMap.put("cycle", problemObj.getCycle());
                hashMap.put("mySolving", problemObj.getMySolving());
                hashMap.put("ox", problemObj.getOX());
                hashMap.put("problemImg", problemObj.getProblemImg());
                hashMap.put("problemName", problemObj.getProblemName());
                hashMap.put("reviewCnt", problemObj.getReviewCnt());
                hashMap.put("reviewDay", problemObj.getReviewDay());
                hashMap.put("reviewTag", problemObj.getReviewTag());
                hashMap.put("solutionImg", problemObj.getSolutionImg());

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.document("user/" + uid + "/" + problemObj.getCategory() + "/" + problemObj.getProblemName()).set(hashMap);

                Intent intent = new Intent(getContext(),MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("X", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                problemObj.addOX(true);
                problemObj.getMySolving().add("");

                List<String> tmp = new ArrayList<>();
                for (int i = 0; i < problemObj.getReviewCnt(); i++) {
                    tmp.add(problemObj.getReviewDay().get(i));
                }
                problemObj.setReviewDay(tmp);

                HashMap<Object,Object> hashMap = new HashMap<>();

                hashMap.put("category", problemObj.getCategory());
                hashMap.put("cycle", problemObj.getCycle());
                hashMap.put("mySolving", problemObj.getMySolving());
                hashMap.put("ox", problemObj.getOX());
                hashMap.put("problemImg", problemObj.getProblemImg());
                hashMap.put("problemName", problemObj.getProblemName());
                hashMap.put("reviewCnt", problemObj.getReviewCnt());
                hashMap.put("reviewDay", problemObj.getReviewDay());
                hashMap.put("reviewTag", problemObj.getReviewTag());
                hashMap.put("solutionImg", problemObj.getSolutionImg());

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.document("user/" + uid + "/" + problemObj.getCategory() + "/" + problemObj.getProblemName()).set(hashMap);

                Intent intent = new Intent(getContext(),MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });
        builder.show();
    }
}
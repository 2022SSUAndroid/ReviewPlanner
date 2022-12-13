package com.example.planner;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryProblemRecyclerAdapter extends RecyclerView.Adapter {

    HashMap<String, Map> problems;
    List<String> problemNameList;
    Context context;

    public HistoryProblemRecyclerAdapter(Context context, List<String> problemNameList, HashMap<String, Map> problems){
        this.problemNameList = problemNameList;
        this.problems = problems;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //자신이 만든 itemview를 inflate한 다음 뷰홀더 생성
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_history_problems,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);

        //생선된 뷰홀더를 리턴하여 onBindViewHolder에 전달한다.
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;

        myViewHolder.button.setText(problemNameList.get(position));
    }

    @Override
    public int getItemCount() {
        return problemNameList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        Button button;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.problem_btn);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Button btn = (Button) view;
                    String selectedProblemName = btn.getText().toString();

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("problem", (Serializable) problems.get(selectedProblemName));
                    FragmentTransaction transaction = ((AppCompatActivity) view.getContext()).getSupportFragmentManager().beginTransaction();
                    HistoryRecordFragment historyRecordFragment = new HistoryRecordFragment();
                    historyRecordFragment.setArguments(bundle);
                    transaction.addToBackStack(null);
                    transaction.replace(R.id.fragment_container, historyRecordFragment);
                    transaction.commit();
                }
            });
        }
    }
}

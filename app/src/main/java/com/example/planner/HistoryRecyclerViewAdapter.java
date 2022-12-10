package com.example.planner;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter {
    String TAG = "RecyclerViewAdapter";

    //리사이클러뷰에 넣을 데이터 리스트

    private List<Boolean> ox;
    private List<String> reviewTag;
    Context context;

    //생성자를 통하여 데이터 리스트 context를 받음
    public HistoryRecyclerViewAdapter(Context context, List<Boolean> ox, List<String> reviewTag){
        this.ox = ox;
        this.reviewTag = reviewTag;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        //데이터 리스트의 크기를 전달해주어야 함
        return ox.size();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG,"onCreateViewHolder");

        //자신이 만든 itemview를 inflate한 다음 뷰홀더 생성
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_history_info,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);


        //생선된 뷰홀더를 리턴하여 onBindViewHolder에 전달한다.
        return viewHolder;
    }




    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG,"onBindViewHolder");

        MyViewHolder myViewHolder = (MyViewHolder)holder;

        if (position == 0) {
            myViewHolder.textViewCnt.setText("처음 ");
        } else {
            myViewHolder.textViewCnt.setText(position + "회차");
        }

        myViewHolder.textViewTag.setText(reviewTag.get(position));

        if (ox.get(position) == true) {
            myViewHolder.imageView.setImageResource(R.drawable.history_o);
        } else {
            myViewHolder.imageView.setImageResource(R.drawable.history_x);
        }
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTag;
        TextView textViewCnt;
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCnt = itemView.findViewById(R.id.reviewCnt);
            textViewTag =  itemView.findViewById(R.id.reviewTag);
            imageView = itemView.findViewById(R.id.imageview);
        }
    }
}

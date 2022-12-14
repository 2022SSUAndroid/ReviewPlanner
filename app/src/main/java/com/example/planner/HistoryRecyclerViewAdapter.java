package com.example.planner;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;
import java.util.Map;

public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter {
    String TAG = "RecyclerViewAdapter";

    //리사이클러뷰에 넣을 데이터 리스트

    private List<Boolean> ox;
    private List<String> reviewTag;
    private List<String> mySolving;
    Context context;

    //생성자를 통하여 데이터 리스트 context를 받음
    public HistoryRecyclerViewAdapter(Context context, Map selectedProblem){
        this.ox = (List<Boolean>) selectedProblem.get("ox");
        this.reviewTag = (List<String>) selectedProblem.get("reviewTag");
        this.mySolving = (List<String>) selectedProblem.get("mySolving");
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
        //자신이 만든 itemview를 inflate한 다음 뷰홀더 생성
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_history_info,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);


        //생선된 뷰홀더를 리턴하여 onBindViewHolder에 전달한다.
        return viewHolder;
    }




    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyViewHolder myViewHolder = (MyViewHolder)holder;

        if (position == 0) {
            myViewHolder.textViewCnt.setText("처음 ");
        } else {
            myViewHolder.textViewCnt.setText(position + "회차");
        }

        try{
            myViewHolder.textViewTag.setText(reviewTag.get(position));
        } catch (Exception e) {
            return;
        }


        if (ox.get(position) == true) {
            myViewHolder.imageView.setImageResource(R.drawable.history_o);
        } else {
            myViewHolder.imageView.setImageResource(R.drawable.history_x);
        }
        /**
         * 임시 이미지
         */
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        String solving = mySolving.get(position);
        if (solving.equals("")) {
            solving = "NoImg2.png";
        }
        StorageReference pathReference = storageReference.child(solving);
        pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context).load(uri).into(myViewHolder.mysolution);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTag;
        TextView textViewCnt;
        ImageView imageView;
        ImageView mysolution;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCnt = itemView.findViewById(R.id.reviewCnt);
            textViewTag =  itemView.findViewById(R.id.reviewTag);
            imageView = itemView.findViewById(R.id.imageview);
            mysolution = itemView.findViewById(R.id.show_mysolution);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mysolution.getVisibility() == View.GONE) {
                        mysolution.setVisibility(View.VISIBLE);
                    } else {
                        mysolution.setVisibility(View.GONE);
                    }
                }
            });
        }
    }
}

package com.example.planner;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button backView;
    LinearLayout select_category;
    EditText problem;
    Button nextView;
    Button addCategoryView;
    List<Button> categoryBtn = new ArrayList<>();       // 카테고리 버튼들의 리스트
    ProblemObj problemObj = new ProblemObj();
    /**
     * 파이어 베이스에 있는 카테고리 목록이 담긴 리스트 이름 : category
     */
    List<String> category = new ArrayList<>();      // 파이어베이스에서 가져와야 함

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_problem);

        backView = findViewById(R.id.back);
        select_category = findViewById(R.id.select_category);
        problem = findViewById(R.id.problem);
        nextView = findViewById(R.id.next);
        addCategoryView = findViewById(R.id.add_category);


        category.add("국어");                        // 예시
        category.add("수학");                        // 예시
        for (int i = 0; i < category.size(); i++) {
            Button btn = new Button(this);
            btn.setText(category.get(i));
            btn.setOnClickListener(this);
            categoryBtn.add(btn);
            select_category.addView(btn);
        }

        backView.setOnClickListener(this);
        nextView.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if(view == backView) {
            // 뒤로가기 구현해야 함
        } else if (view == addCategoryView) {
            // 카테고리 추가 기능 구현해야 함
        } else if (categoryBtn.contains(view)) {  // 카테고리 선택 시 내부 동작 구현 필요
            int idx = categoryBtn.indexOf(view);
            problemObj.setCategory(String.valueOf(categoryBtn.get(idx).getText()));
            /**
             * 아래 토스트는 선택이 제대로 됐는지 확인을 위한 것임.
             * 선택한 버튼의 텍스트(카테고리 이름)에 해당하는 카테고리에 문제가 저장되도록 하면 됨.
             */
            Toast.makeText(this, "선택한 카테고리는 " + categoryBtn.get(idx).getText(), Toast.LENGTH_LONG).show();
        } else if (view == nextView) {
            if (problem.getText().toString().equals("")) {
                Toast.makeText(this, "문제 이름을 입력하세요.", Toast.LENGTH_LONG).show();
            } else{
                problemObj.setProblemName(String.valueOf(problem.getText()));
                // 파이어베이스에 수집한 데이터 객체 (problemObj 보내기)
                // 아래 토스트는 확인용 (나중에 삭제해야 함)
                Toast.makeText(this, "문제 이름 : " + problemObj.getProblemName() + "\n카테고리 : " + problemObj.getCategory(), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(), AddCycle.class);
                intent.putExtra("PROBLEM_OBJ", problemObj);
                startActivity(intent);
            }
        }
    }
}
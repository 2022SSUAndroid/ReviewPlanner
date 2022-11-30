package com.example.planner;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class AddCycle extends AppCompatActivity implements View.OnClickListener {

    Button backView;
    Button selectedView;
    Button cycle1View;
    Button cycle2View;
    Button cycle3View;
    Button selectMyselfView;
    Button nextView;
    ProblemObj problemObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cycle);

        Intent intent = getIntent();
        problemObj = (ProblemObj)intent.getSerializableExtra("PROBLEM_OBJ");

        /* 인텐트 잘 전달받았는지 확인 */ /* 나중에 삭제해야 함 */
        Toast.makeText(this, problemObj.getProblemName() +"\n" + problemObj.getCategory(), Toast.LENGTH_LONG).show();

        backView = findViewById(R.id.back);
        selectedView = findViewById(R.id.selected);
        cycle1View = findViewById(R.id.cycle1);
        cycle2View = findViewById(R.id.cycle2);
        cycle3View = findViewById(R.id.cycle3);
        selectMyselfView = findViewById(R.id.select_myself);
        nextView = findViewById(R.id.next);

        selectedView.setText(cycle1View.getText());
        List<Integer> cycle1 = Arrays.asList(0, 1, 3, 7);
        problemObj.setCycle(cycle1);
        // 파이어베이스에 복습 주기 기본 설정은 cycle1으로

        backView.setOnClickListener(this);
        cycle1View.setOnClickListener(this);
        cycle2View.setOnClickListener(this);
        cycle3View.setOnClickListener(this);
        selectMyselfView.setOnClickListener(this);
        nextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == backView) {
            // 뒤로가기 구현해야 함
        } else if(view == nextView) {
            /* 선택한게 제대로 들어갔는지 확인 */ /* 나중에 지울거임 */
            Toast.makeText(this, problemObj.getCycle().toString(), Toast.LENGTH_LONG).show();
            // 다음으로 가기 구현해야 함 (지우가 만든 문제,사진 올리는 액티비티로 problemObj 전달하면서 화면 전환)
        } else if (view == selectMyselfView) {
            // 직접 선택 부분 구현해야 함
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
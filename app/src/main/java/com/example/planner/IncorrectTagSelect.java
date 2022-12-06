package com.example.planner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class IncorrectTagSelect extends AppCompatActivity implements AdapterView.OnItemClickListener{

    String[] arrayDates;
    private TextView btn_move;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incorrect_tag_select);

        ListView listView = findViewById(R.id.main_listview_array);
        listView.setOnItemClickListener(this);

        btn_move = findViewById(R.id.btn_next);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IncorrectTagSelect.this, Register.class);
                startActivity(intent);
            }
        });
        //arrayDates = getResources().getStringArray(R.array.incorrectTAG);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_multiple_choice, arrayDates);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(arrayAdapter);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast t= Toast.makeText(this, arrayDates[position], Toast.LENGTH_SHORT);
        t.show();
    }
}
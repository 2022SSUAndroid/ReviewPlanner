package com.example.planner;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogFragmentNewCategory extends DialogFragment {

    public interface MyFragmentInterfacer {
        void onButtonClick(String input);
    }

    private MyFragmentInterfacer fragmentInterfacer;


    public void setFragmentInterfacer(MyFragmentInterfacer fragmentInterfacer){
        this.fragmentInterfacer = fragmentInterfacer;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        EditText editText = new EditText(getActivity());
        editText.setHint("카테고리 이름");
        editText.setText("");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("카테고리 추가");
        builder.setMessage("추가할 카테고리 이름을 입력하세요");
        builder.setView(editText);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!editText.getText().toString().equals("")) {
                    String newCategory = editText.getText().toString();
                    fragmentInterfacer.onButtonClick(newCategory);
                    getDialog().dismiss();
                }
            }
        });
        AlertDialog dialog = builder.create();
        return dialog;
    }
}

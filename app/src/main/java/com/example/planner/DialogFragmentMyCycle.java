package com.example.planner;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DialogFragmentMyCycle extends DialogFragment {

    public interface MyFragmentInterface {
        void onButtonClick(List<Integer> input);
    }

    private DialogFragmentMyCycle.MyFragmentInterface fragmentInterface;


    public void setFragmentInterface(DialogFragmentMyCycle.MyFragmentInterface fragmentInterface){
        this.fragmentInterface = fragmentInterface;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        EditText editText = new EditText(getActivity());
        editText.setHint("1,3,7,14,30");
        editText.setText("");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("복습주기 직접 설정");
        builder.setMessage("빈칸 없이 콤마로 구분한 숫자를 입력해주세요");
        builder.setView(editText);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (editText.getText().toString() != "") {
                    List<Integer> myCycle = new ArrayList<>();
                    String cycleStr = editText.getText().toString();
                    String[] cycleArr = cycleStr.split(",");
                    for (int k = 0; k < cycleArr.length; k++) {
                        myCycle.add(Integer.parseInt(cycleArr[k]));
                    }
                    fragmentInterface.onButtonClick(myCycle);
                    getDialog().dismiss();
                }
            }
        });
        AlertDialog dialog = builder.create();
        return dialog;
    }
}

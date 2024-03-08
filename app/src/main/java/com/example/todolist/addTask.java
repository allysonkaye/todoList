package com.example.todolist;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todolist.Util.databaseHelper;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class addTask extends BottomSheetDialogFragment {
    private static final String TAG = "AdddNewTask";

    private EditText add_txt;
    private Button btn_add;
    private databaseHelper db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater(R.layout.add_task , container , false);
        return v;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        add_txt = view.findViewById(R.id.add_txt);
        btn_add = view.findViewById(R.id.btn_add);

        db = new databaseHelper(getActivity());

        boolean isUpdate = false;
        Bundle bundle = getArguments();
        if (bundle != null) {
            isUpdate = true;
            String task = bundle.getString("task");
            add_txt.getText(task);

            if (task.length() > 0){
                btn_add.setEnabled(false);
            }
        }
        add_txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}

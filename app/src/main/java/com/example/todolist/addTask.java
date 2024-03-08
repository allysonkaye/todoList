package com.example.todolist;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
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

import com.example.todolist.Model.todoModel;
import com.example.todolist.Util.databaseHelper;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class addTask extends BottomSheetDialogFragment {
    public static final String TAG = "addTask";

    private EditText add_txt;
    private Button btn_add;
    private databaseHelper db;

    public static addTask newInstances() {
        return new addTask();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_task , container , false);
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
            add_txt.setText(task);
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

                if ( charSequence.toString().equals("")) {
                    btn_add.setEnabled(false);
                    btn_add.setBackgroundColor(Color.GRAY);
                } else {
                    btn_add.setEnabled(true);
                    btn_add.setBackgroundColor(getResources().getColor(R.color.violet));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        final boolean finalIsUpdate = isUpdate;
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = add_txt.getText().toString();

                if(finalIsUpdate) {
                    db.updateTask(bundle.getInt("id") , text);

                }else {
                    todoModel item = new todoModel();
                    item.setTask(text);
                    item.setStatus(0);
                    db.insertTask(item);
                }
                dismiss();
            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if (activity instanceof onDialogCloseListener) {
            ((onDialogCloseListener) activity).onDialogClose(dialog);
        }
    }
}

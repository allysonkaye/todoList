package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.todolist.Model.todoModel;
import com.example.todolist.Util.databaseHelper;
import com.example.todolist.adapter.todoAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements onDialogCloseListener {
    private RecyclerView recyclerView;
    private Button fab;
    private databaseHelper db;
    private List<todoModel> list;
    private todoAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.todolist);
        fab = findViewById(R.id.floatingActionButton);
        db = new databaseHelper(MainActivity.this);
        list = new ArrayList<>();
        adapter = new todoAdapter(db , MainActivity.this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        list = db.getAllTasks();
        Collections.reverse(list);
        adapter.setTask(list);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask.newInstances().show(getSupportFragmentManager() , addTask.TAG);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new recyclerViewTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
        list = db.getAllTasks();
        Collections.reverse(list);
        adapter.setTask(list);
        adapter.notifyDataSetChanged();
    }
}
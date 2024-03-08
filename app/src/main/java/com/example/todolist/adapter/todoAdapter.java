package com.example.todolist.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.MainActivity;
import com.example.todolist.Model.todoModel;
import com.example.todolist.R;
import com.example.todolist.Util.databaseHelper;
import com.example.todolist.addTask;

import java.util.List;

public class todoAdapter extends RecyclerView.Adapter<todoAdapter.myViewHolder> {

    private List<todoModel> list;
    private MainActivity activity;
    private databaseHelper db;

    public todoAdapter(databaseHelper db , MainActivity activity){
        this.activity = activity;
        this.db = db;
    }
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_list , parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        final todoModel item = list.get(position);
        holder.txt_todo.setText(item.getTask());
        holder.txt_todo.setChecked(toBoolean(item.getStatus()));
        holder.txt_todo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked) {
                    db.updateStatus(item.getId() , 1);
                } else {
                    db.updateStatus(item.getId(), 0);
                }
            }
        });
    }

    public boolean toBoolean(int num) {
        return num!=0;
    }
    public Context getContext() {
        return activity;
    }

    public void setTask(List<todoModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void deleteTask(int position) {
        todoModel item = list.get(position);
        db.deleteTask(item.getId());
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void editItem( int position){
        todoModel item = list.get(position);

        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("task" , item.getTask());

        addTask task = new addTask();
        task.show(activity.getSupportFragmentManager(), task.getTag());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{

        CheckBox  txt_todo;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_todo= itemView.findViewById(R.id.txt_todo);
        }
    }
}
